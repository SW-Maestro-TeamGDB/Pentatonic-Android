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


class RecordProcessingActivity : 
    BaseActivity<ActivityRecordProcessingBinding, RecordProcessingViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.activity_record_processing
    override val viewModel: RecordProcessingViewModel by viewModel()

    private var player: MediaPlayer? = null

    private var indicatorWidth: Int = 0

    private val recordingFilePath: String by lazy {  // 녹음본이 저장된 위치
        "${externalCacheDir?.absolutePath}/recording.m4a"
    }

    private val amplitudeData: ByteArray by lazy {  // 녹음본 진폭 정보 (ByteArray)
        intent.extras?.getByteArray(AMPLITUDE_DATA)!!
    }

    private var state = ButtonState.BEFORE_PLAYING
        set(value) { // setter 설정
            field = value // 실제 프로퍼티에 대입
            binding.playButton.updateIconWithState(value)
        }

    override fun initStartView() {
        binding.playButton.updateIconWithState(state)

    }

    override fun initDataBinding() {
    }

    override fun initAfterBinding() {
        // ViewPager 어댑터 지정 및 탭 이름 설정
        binding.viewPager.adapter = TabFragmentAdapter(this)
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when(position){
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

        // Indicator 이동을 위한 PageChangeCallback 리스너 등록
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
        binding.audioSeekBar.onProgressListener = object: OnProgressListener{
            override fun onProgressChanged(progress: Float, byUser: Boolean) {
                Timber.d("SeekBar : $progress")
            }

            override fun onStartTracking(progress: Float) {
            }

            override fun onStopTracking(progress: Float) {
            }
        }

        binding.playButton.setOnClickListener {
            when (state) {
                ButtonState.BEFORE_PLAYING -> {
                    startPlaying()
                }
                ButtonState.ON_PLAYING -> {
                    stopPlaying()
                }
                else -> { /* no-op */ }
            }
        }
    }


    /**
     * 녹음본을 재생 (리버브 이펙트 테스트)
     */
    private fun startPlaying() {
        player = MediaPlayer()
        player?.apply {
            setDataSource(recordingFilePath)

//                val reverbEffect = PresetReverb(0, 0)
//                reverbEffect.preset = PresetReverb.PRESET_LARGEHALL
//                reverbEffect.enabled = true
//                attachAuxEffect(reverbEffect.id)
//                val environmentalReverb = EnvironmentalReverb(0, 0)
//                environmentalReverb.decayTime = 2000
//                environmentalReverb.reflectionsDelay = 250
//                environmentalReverb.reflectionsLevel = -8500
//                environmentalReverb.roomLevel = -8500
//                attachAuxEffect(reverbEffect.id)
//
//            } catch (e: IllegalArgumentException) {
//                Timber.i("IllegalArgumentException 잼 ㅋㅋ :  ${player?.audioSessionId}")
//                Timber.e(e)
//            } catch (e: UnsupportedOperationException) {
//                Timber.i("UnsupportedOperationException 잼 ㅋㅋ :  ${player?.audioSessionId}")
//                Timber.e(e)
//            } catch (e: RuntimeException) {
//                Timber.i("RuntimeException 잼 ㅋㅋ :  ${player?.audioSessionId}")
//                Timber.e(e)
//            } finally {
//                Timber.i("오잉 또잉 ㅋㅋ")
//            }
            setAuxEffectSendLevel(1.0f)
            prepare()  // 재생 할 수 있는 상태 (큰 파일 또는 네트워크로 가져올 때는 prepareAsync() )
            start()
        }
        binding.recordTimeTextView.startCountUp()

        // 끝까지 재생이 끝났을 때
        player?.setOnCompletionListener {
            stopPlaying()
            state = ButtonState.BEFORE_PLAYING
        }

        state = ButtonState.ON_PLAYING
    }

    /**
     * 음원 재생 중지
     */
    private fun stopPlaying() {
        player?.release()
        player = null
        binding.recordTimeTextView.stopCountUp()

        state = ButtonState.BEFORE_PLAYING
    }

    companion object {
        const val NUM_PAGES = 2
    }

}