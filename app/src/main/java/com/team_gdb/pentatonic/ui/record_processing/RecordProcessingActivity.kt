package com.team_gdb.pentatonic.ui.record_processing

import android.media.MediaPlayer
import android.media.audiofx.AudioEffect
import android.media.audiofx.EnvironmentalReverb
import android.media.audiofx.PresetReverb
import android.provider.MediaStore
import com.newidea.mcpestore.libs.base.BaseActivity
import com.team_gdb.pentatonic.R
import com.team_gdb.pentatonic.databinding.ActivityRecordProcessingBinding
import com.team_gdb.pentatonic.ui.record.ButtonState
import org.koin.android.ext.android.bind
import org.koin.androidx.viewmodel.ext.android.viewModel

class RecordProcessingActivity :
    BaseActivity<ActivityRecordProcessingBinding, RecordProcessingViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.activity_record_processing
    override val viewModel: RecordProcessingViewModel by viewModel()

    private var player: MediaPlayer? = null

    private val recordingFilePath: String by lazy {  // 녹음본이 저장된 위치
        "${externalCacheDir?.absolutePath}/recording.m4a"
    }

    private val audioEffectDescriptor: AudioEffect.Descriptor by lazy {  // AudioEffect 모듈 Descriptor
        AudioEffect.Descriptor()
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
        binding.applyReverbButton.setOnClickListener {
//            audioEffectDescriptor.apply{
//                type = AudioEffect.EFFECT_TYPE_ENV_REVERB
//                connectMode = AudioEffect.EFFECT_INSERT
//            }
        }

        binding.playButton.setOnClickListener {
            when (state) {
                ButtonState.BEFORE_PLAYING -> {
                    startPlaying()
                }
                ButtonState.ON_PLAYING -> {
                    stopPlaying()
                }
            }
        }
    }


    /**
     * 녹음본을 재생 (리버브 이펙트 테스트)
     */
    private fun startPlaying() {
        player = MediaPlayer()
            .apply {
                setDataSource(recordingFilePath)
                prepare() // 재생 할 수 있는 상태 (큰 파일 또는 네트워크로 가져올 때는 prepareAsync() )
            }
//        val reverbEffect = EnvironmentalReverb(1, player!!.audioSessionId)
        val reverbEffect = PresetReverb(1, player!!.audioSessionId)
        reverbEffect.preset = PresetReverb.PRESET_LARGEHALL
        reverbEffect.enabled = true

        player?.apply {
            attachAuxEffect(reverbEffect.id)
            setAuxEffectSendLevel(1.0f)
        }

        player?.start() // 재생
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

}