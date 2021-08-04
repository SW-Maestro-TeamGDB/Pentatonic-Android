package com.team_gdb.pentatonic.ui.record_processing

import android.media.MediaPlayer
import android.widget.FrameLayout
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.team_gdb.pentatonic.R
import com.team_gdb.pentatonic.base.BaseActivity
import com.team_gdb.pentatonic.databinding.ActivityRecordProcessingBinding
import com.team_gdb.pentatonic.ui.record.ButtonState
import com.team_gdb.pentatonic.ui.record.RecordActivity.Companion.AMPLITUDE_DATA
import org.koin.androidx.viewmodel.ext.android.viewModel
import rm.com.audiowave.OnProgressListener
import timber.log.Timber
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*


class RecordProcessingActivity :
    BaseActivity<ActivityRecordProcessingBinding, RecordProcessingViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.activity_record_processing
    override val viewModel: RecordProcessingViewModel by viewModel()

    private var player: MediaPlayer? = null
    private var indicatorWidth: Int = 0

    private var totalDuration: Float = 0.0F  // 음악 총 재생 길이
    private var interval: Float =
        0.0F  // 음악 총 재생 길이를 100으로 나눈 값 (AudioWave 라이브러리의 SeekBar 가 0 ~ 100 만 지원하기 때문)

    private var seekBarThread: Thread? = null

    private val recordingFilePath: String by lazy {  // 녹음본이 저장된 위치
        "${externalCacheDir?.absolutePath}/recording.m4a"
    }

    private val amplitudeData: ByteArray by lazy {  // 녹음본 진폭 정보 (ByteArray)
        intent.extras?.getByteArray(AMPLITUDE_DATA)!!
    }

    override fun initStartView() {
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        initPlayer()
    }

    override fun initDataBinding() {
        viewModel.buttonState.observe(this) {
            binding.playButton.updateIconWithState(it)
        }
    }

    override fun initAfterBinding() {
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
            indicatorWidth = binding.tabLayout.width / NUM_PAGES
            val params = binding.indicator.layoutParams as FrameLayout.LayoutParams
            params.width = indicatorWidth
            binding.indicator.layoutParams = params
        }

        // TabLayout Indicator 위치 이동을 위한 PageChangeCallback 리스너 등록
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
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
        })

        // AudioWave SeekBar 데이터 입력 (ByteArray)
        binding.audioSeekBar.setRawData(amplitudeData)
        binding.audioSeekBar.onProgressListener = object : OnProgressListener {
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

        binding.playButton.setOnClickListener {
            when (viewModel.buttonState.value) {
                ButtonState.BEFORE_PLAYING -> {
                    startPlaying()
                }
                ButtonState.ON_PLAYING -> {
                    pausePlaying()
                }
                else -> { /* no-op */
                }
            }
        }
    }

    private fun initPlayer() {
        player = MediaPlayer().apply {
            setDataSource(recordingFilePath)
            setAuxEffectSendLevel(1.0f)
            prepare()  // 재생 할 수 있는 상태 (큰 파일 또는 네트워크로 가져올 때는 prepareAsync() )
            setOnCompletionListener {  // 끝까지 재생이 끝났을 때
                pausePlaying()
            }
            totalDuration = this.duration.toFloat()
            interval = this.duration.toFloat().div(100)
        }
    }

    /**
     * 녹음본을 재생
     */
    private fun startPlaying() {
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
                    binding.audioSeekBar.progress = player?.currentPosition?.div(interval)!!
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
    }
}