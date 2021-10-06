package com.team_gdb.pentatonic.ui.record_processing

import android.content.Intent
import android.media.MediaPlayer
import android.media.audiofx.EnvironmentalReverb
import android.media.audiofx.LoudnessEnhancer
import android.widget.FrameLayout
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.team_gdb.pentatonic.R
import com.team_gdb.pentatonic.base.BaseActivity
import com.team_gdb.pentatonic.data.model.CreatedCoverEntity
import com.team_gdb.pentatonic.databinding.ActivityRecordProcessingBinding
import com.team_gdb.pentatonic.ui.create_cover.CreateCoverActivity.Companion.CREATED_COVER_ENTITY
import com.team_gdb.pentatonic.custom_view.ButtonState
import com.team_gdb.pentatonic.ui.cover_view.band_cover.BandCoverActivity
import com.team_gdb.pentatonic.ui.lounge.LoungeFragment.Companion.COVER_ID
import com.team_gdb.pentatonic.ui.record.RecordActivity.Companion.AMPLITUDE_DATA
import com.team_gdb.pentatonic.util.Event
import org.koin.androidx.viewmodel.ext.android.viewModel
import rm.com.audiowave.OnProgressListener
import timber.log.Timber
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import zeroonezero.android.audio_mixer.AudioMixer
import zeroonezero.android.audio_mixer.input.AudioInput
import zeroonezero.android.audio_mixer.input.GeneralAudioInput
import java.lang.NullPointerException


class RecordProcessingActivity :
    BaseActivity<ActivityRecordProcessingBinding, RecordProcessingViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.activity_record_processing
    override val viewModel: RecordProcessingViewModel by viewModel()

    private var player: MediaPlayer? = null
    private var indicatorWidth: Int = 0

    private var totalDuration: Float = 0.0F  // 음악 총 재생 길이
    // 음악 총 재생 길이를 100으로 나눈 값 (AudioWave 라이브러리의 SeekBar 가 0 ~ 100 만 지원하기 때문)
    private var interval: Float = 0.0F

    private var seekBarThread: Thread? = null

    // ReverbEffect
    private val reverbEffect = EnvironmentalReverb(1, 0)

    // 녹음본이 저장된 위치
    private val recordingFilePath: String by lazy { "${externalCacheDir?.absolutePath}/recording.mp3" }
    private val processedAudioFilePath: String by lazy { "${externalCacheDir?.absolutePath}/result.mp3" }


    private val amplitudeData: ByteArray by lazy {  // 녹음본 진폭 정보 (ByteArray)
        intent.extras?.getByteArray(AMPLITUDE_DATA)!!
    }

    private val createdCoverEntity: CreatedCoverEntity by lazy {  // 사용자가 이전 페이지에서 입력한 커버 정보
        intent.extras?.getSerializable(CREATED_COVER_ENTITY)!! as CreatedCoverEntity
    }

    override fun initStartView() {
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        this.progressView = binding.progressBar

        binding.playButton.updateIconWithState(ButtonState.BEFORE_PLAYING)

        Timber.d(createdCoverEntity.toString())

        binding.titleBar.titleTextView.text = "다듬기"

        // ViewPager 어댑터 지정 및 탭 이름 설정
        binding.viewPager.adapter = TabFragmentAdapter(this)
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "컨트롤"
                1 -> tab.text = "이펙터"
            }
        }.attach()

        // 동적으로 TabLayout Indicator Width 계산
        binding.tabLayout.post {
            setViewPagerIndicatorWidth()
        }
    }

    override fun initDataBinding() {
        viewModel.buttonState.observe(this) {
            binding.playButton.updateIconWithState(it)
        }

        // 커버 파일 업로드가 완료되면, 커버를 라이브러리에 업로드하는 뮤테이션 실행
        viewModel.coverFileURL.observe(this) {
            if (it.isNotBlank()) {
                viewModel.getInstMergedCover(createdCoverEntity.coverSong.songUrl, it)
            }
        }

        // MR 과 합쳐진 녹음 결과 준비 완료
        viewModel.instMergedCover.observe(this) {
            Timber.d("MR 합본 : $it")
            initPlayer(it)
        }

        // 사용자가 싱크 조절 값을 변경했을 때, 해당 수치에 따른 오디오 가공 실행
        viewModel.syncLevel.observe(this) {
            pausePlaying()
            setProgressVisible(true)
            doAudioProcessing(syncLevel = it.toLong())
        }

        viewModel.audioProcessingComplete.observe(this) {
            if (it.getContentIfNotHandled() == true) {
                // 커버 파일 업로드
                viewModel.uploadCoverFile(processedAudioFilePath)
            }
        }

        viewModel.reverbEffectLevel.observe(this) {
            reverbEffect.reverbLevel = (-8000 + (it * 100)).toShort()
        }

        // 커버 제목 입력이 완료되면, 커버 파일 업로드 뮤테이션 실행
        viewModel.coverNameInputComplete.observe(this) {
            if (it.getContentIfNotHandled() == true) {
                // 커버 정보 라이브러리에 업로드
                viewModel.uploadCoverToLibrary(
                    viewModel.coverNameField.value.toString(),
                    viewModel.coverFileURL.value!!,
                    createdCoverEntity.coverSong.songId,
                    createdCoverEntity.coverSessionConfig[0].sessionSetting.name
                )
            }
        }

        // 라이브러리 커버 업로드가 완료됐을 때
        viewModel.coverUploadComplete.observe(this) {
            if (!it.getContentIfNotHandled().isNullOrBlank()) {
                Timber.d("라이브러리 커버 업로드가 완료됐단다!")
                viewModel.createBand(
                    sessionConfig = createdCoverEntity.coverSessionConfig,
                    bandName = createdCoverEntity.coverName,
                    bandIntroduction = createdCoverEntity.coverIntroduction ?: "",
                    backgroundUrl = createdCoverEntity.backgroundImg,
                    songId = createdCoverEntity.coverSong.songId
                )
            }
        }

        viewModel.createBandComplete.observe(this) {
            if (!it.getContentIfNotHandled().isNullOrBlank()) {
                Timber.d("createBand() Complete!")
                viewModel.joinBand(
                    sessionName = createdCoverEntity.coverSessionConfig[0].sessionSetting.name,
                    bandId = it.peekContent(),
                    coverId = viewModel.coverUploadComplete.value!!.peekContent()
                )
            }
        }

        viewModel.joinBandComplete.observe(this) {
            if (it.getContentIfNotHandled() == true) {
                // 밴드 생성 및 참여 완료 시 Alert 애니메이션 실행
                val intent = Intent(this, BandCoverActivity::class.java)
                intent.putExtra(CREATE_COVER, "CREATE_COVER")
                intent.putExtra(COVER_ID, viewModel.createBandComplete.value!!.peekContent())
                finish()
                startActivity(intent)
            }
        }
    }

    override fun initAfterBinding() {
        binding.titleBar.backButton.setOnClickListener {
            finish()
        }

        // TabLayout Indicator 위치 이동을 위한 PageChangeCallback 리스너 등록
        binding.viewPager.registerOnPageChangeCallback(pageChangeListener)

        // AudioWave SeekBar 데이터 입력 (ByteArray)
        binding.audioSeekBar.setRawData(amplitudeData)
        binding.audioSeekBar.onProgressListener = audioProgressListener

        // 재생 버튼 눌렀을 때
        binding.playButton.setOnClickListener {
            when (viewModel.buttonState.value) {
                ButtonState.BEFORE_PLAYING -> {
                    startPlaying()
                }
                ButtonState.ON_PLAYING -> {
                    pausePlaying()
                }
                else -> {
                    /* no-op */
                }
            }
        }

        // 완료 버튼 눌렀을 때, 커버 제목 정보를 입력받도록 함
        binding.completeButton.setOnClickListener {
            val bottomSheetDialog = InputCoverNameBottomSheetDialog()
            bottomSheetDialog.show(supportFragmentManager, bottomSheetDialog.tag)
        }
    }

    private fun initPlayer(url: String) {
        player = MediaPlayer().apply {
            setDataSource(url)
            prepare()  // 재생 할 수 있는 상태 (큰 파일 또는 네트워크로 가져올 때는 prepareAsync() )
            setOnCompletionListener {  // 끝까지 재생이 끝났을 때
                pausePlaying()
            }
            setOnPreparedListener {
                viewModel.buttonState.postValue(ButtonState.BEFORE_PLAYING)
                setProgressVisible(false)
            }
            totalDuration = this.duration.toFloat()
            interval = this.duration.toFloat().div(100)
        }

        totalDuration = player!!.duration.toFloat()
        interval = player!!.duration.toFloat().div(100)

    }

    /**
     * 오디오 프로그레스 값 변경될 때마다 처리해줘야 할 동작
     * - 사용자가 SeekBar 조정을 통해 값이 변경된 것이라면, MedialPlayer 재생 위치를 옮김
     * - 기본적으로, ViewModel 에 재생 시간과 남은 시간 postValue() 동작을 함
     */
    private val audioProgressListener = object : OnProgressListener {
        override fun onProgressChanged(progress: Float, byUser: Boolean) {
            if (byUser) {  // 사용자가 SeekBar 움직였을 경우
                // 재생 위치를 해당 위치로 바꿔줌 (움직인 곳에서부터 음악 재생)
                player?.seekTo((progress * interval).toInt())
            }
            viewModel.playTime.postValue(SimpleDateFormat("mm:ss").format(Date(player?.currentPosition!!.toLong())))
            viewModel.remainTime.postValue("-${SimpleDateFormat("mm:ss").format(Date(player?.duration!! - player?.currentPosition!!.toLong()))}")
        }

        override fun onStartTracking(progress: Float) {
        }

        override fun onStopTracking(progress: Float) {
        }
    }

    /**
     * ViewPager 의 페이지 (컨트롤, 이펙트) 변경 시 인디케이터 UI 업데이트
     */
    private val pageChangeListener = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {
            super.onPageScrolled(position, positionOffset, positionOffsetPixels)
            val params = binding.indicator.layoutParams as FrameLayout.LayoutParams

            val translationOffset: Float = (positionOffset + position) * indicatorWidth
            params.leftMargin = translationOffset.toInt()
            binding.indicator.layoutParams = params
        }
    }

    private fun setViewPagerIndicatorWidth() {
        indicatorWidth = binding.tabLayout.width / NUM_PAGES
        val params = binding.indicator.layoutParams as FrameLayout.LayoutParams
        params.width = indicatorWidth
        binding.indicator.layoutParams = params
    }

    /**
     * Environment Reverb 이펙트 적용
     */
    private fun setEnvironmentReverb() {
        player?.attachAuxEffect(reverbEffect.id)
        reverbEffect.reverbLevel = -8000
        reverbEffect.enabled = true
        player?.setAuxEffectSendLevel(1.0f)
    }

    /**
     * LoudnessEnhancer 이펙트 적용
     * - 타겟 게인 값을 입력받아 게인을 늘림
     */
    private fun setLoudnessEnhancer() {
        val gain = LoudnessEnhancer(0)
        player?.attachAuxEffect(gain.id)
        gain.setTargetGain(100)
        gain.enabled = true
        player?.setAuxEffectSendLevel(1.0f)
    }

    /**
     * 입력받은 싱크 조절 레벨에 따라, 음원을 가공하는 함수
     *
     * @param syncLevel : 싱크를 얼마나 조절할지에 대한 수치
     */
    private fun doAudioProcessing(syncLevel: Long) {
        val input: AudioInput =
            GeneralAudioInput(recordingFilePath)

        input.volume = 1.0f //Optional
        input.startTimeUs = syncLevel * 1000  // 함수의 입력값은 ms 기준이기 때문

        // It will produce a blank portion of 3 seconds between input1 and input2 if mixing type is sequential.
        // But it will does nothing in parallel mixing.
//        val blankInput: AudioInput = BlankAudioInput(3000000)
//
//        val input2: AudioInput =
//            GeneralAudioInput("${externalCacheDir?.absolutePath}/fixyou_mr.mp3")

//        input2.startTimeUs = 3000000 //Optional
//        input2.endTimeUs = 9000000 //Optional
//        input2.setStartOffsetUs(5000000) //Optional. It is needed to start mixing the input at a certain time.

        val audioMixer = AudioMixer(processedAudioFilePath)

        audioMixer.addDataSource(input)
//        audioMixer.addDataSource(blankInput)
//        audioMixer.addDataSource(input2)

        audioMixer.setSampleRate(44100)  // Optional
        audioMixer.setBitRate(128000)  // Optional
        audioMixer.setChannelCount(2)  // Optional //1(mono) or 2(stereo)

        // Smaller audio inputs will be encoded from start-time again if it reaches end-time
        // It is only valid for parallel mixing
        // audioMixer.setLoopingEnabled(true);

        audioMixer.mixingType =
            AudioMixer.MixingType.PARALLEL // or AudioMixer.MixingType.SEQUENTIAL

        audioMixer.setProcessingListener(object : AudioMixer.ProcessingListener {
            override fun onProgress(progress: Double) {
                runOnUiThread {
//                    progressDialog.setProgress((progress * 100).toInt())
//                    Toast.makeText(applicationContext, "$progress", Toast.LENGTH_SHORT).show()
                }
            }

            // 오디오 가공이 끝났을 때의 작업 정의
            override fun onEnd() {
                runOnUiThread {
                    audioMixer.release()
                }
                viewModel.audioProcessingComplete.postValue(Event(true))
            }
        })

        //it is for setting up the all the things

        //it is for setting up the all the things
        audioMixer.start()

        /* These getter methods must be called after calling 'start()'*/
        // audioMixer.getOutputSampleRate();
        // audioMixer.getOutputBitRate();
        // audioMixer.getOutputChannelCount();
        // audioMixer.getOutputDurationUs();

        // starting real processing

        try {
            audioMixer.processAsync()
        } catch (e: Exception) {
            Timber.e(e)
            viewModel.audioProcessingComplete.postValue(Event(false))
        }
    }

    /**
     * 녹음본을 재생
     */
    private fun startPlaying() {
        setEnvironmentReverb()
        player?.start()
        viewModel.buttonState.postValue(ButtonState.ON_PLAYING)
        seekBarThread = Thread {
            while (player?.isPlaying == true) {
                try {
                    Thread.sleep(1000)
                } catch (e: Exception) {
                    Timber.i(e)
                }
                runOnUiThread {
                    try {
                        binding.audioSeekBar.progress = player?.currentPosition?.div(interval)!!
                    } catch (e: NullPointerException) {
                        seekBarThread?.interrupt()
                        Timber.e(e)
                    }
                }
            }
        }
        seekBarThread!!.start()
    }

    /**
     * 음원 재생 일시 정지
     */
    private fun pausePlaying() {
        player?.pause()
        viewModel.buttonState.postValue(ButtonState.BEFORE_PLAYING)
    }

    /**
     * 음원 재생 중지
     */
    private fun stopPlaying() {
        player?.release()
        player = null
    }

    /**
     * SeekBar Thread 종료 및 메모리 해제
     */
    private fun stopSeekBarThread() {
        seekBarThread?.interrupt()
        seekBarThread = null
    }

    /**
     * 뷰가 사라질 시점에 player 메모리 해제 안 되어있다면 해제
     */
    override fun onDestroy() {
        super.onDestroy()
        stopPlaying()
        stopSeekBarThread()
    }

    companion object {
        const val NUM_PAGES = 2
        const val CREATE_COVER = "create_cover"
    }
}