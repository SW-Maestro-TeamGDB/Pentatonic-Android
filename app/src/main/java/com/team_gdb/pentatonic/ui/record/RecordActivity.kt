package com.team_gdb.pentatonic.ui.record

import android.media.MediaPlayer
import android.media.MediaRecorder
import com.newidea.mcpestore.libs.base.BaseActivity
import com.team_gdb.pentatonic.R
import org.koin.androidx.viewmodel.ext.android.viewModel

import com.team_gdb.pentatonic.databinding.ActivityRecordBinding

class RecordActivity : BaseActivity<ActivityRecordBinding, RecordViewModel>() {
    private var recorder: MediaRecorder? = null // MediaRecorder 사용하지 않을 때는 메모리 해제
    private var player: MediaPlayer? = null

    override val layoutResourceId: Int
        get() = R.layout.activity_record
    override val viewModel: RecordViewModel by viewModel()

    private val recordingFilePath: String by lazy {
        "${externalCacheDir?.absolutePath}/recording.3gp"
    }

    override fun initStartView() {
        binding.recordButton.updateIconWithState(state)
    }

    override fun initDataBinding() {
    }

    override fun initAfterBinding() {
        binding.soundVisualizerView.onRequestCurrentAmplitude = {
            recorder?.maxAmplitude ?: 0
        }

        binding.recordButton.setOnClickListener {
            when (state) {
                ButtonState.BEFORE_RECORDING -> {
                    startRecoding()
                }
                ButtonState.ON_RECORDING -> {
                    stopRecording()
                }
                ButtonState.AFTER_RECORDING -> {
                    startPlaying()
                }
                ButtonState.ON_PLAYING -> {
                    stopPlaying()
                }
            }
        }

        binding.resetButton.setOnClickListener {
            stopPlaying()
            // clear
            binding.soundVisualizerView.clearVisualization()
            binding.recordTimeTextView.clearCountTime()
            state = ButtonState.BEFORE_RECORDING
        }

        state = ButtonState.BEFORE_RECORDING

    }

    private var state = ButtonState.BEFORE_RECORDING
        set(value) { // setter 설정
            field = value // 실제 프로퍼티에 대입
            binding.resetButton.isEnabled =
                (value == ButtonState.AFTER_RECORDING || value == ButtonState.ON_PLAYING)
            binding.recordButton.updateIconWithState(value)
        }

    private fun startRecoding() {
        // 녹음 시작 시 초기화
        recorder = MediaRecorder()
            .apply {
                setAudioSource(MediaRecorder.AudioSource.MIC)
                setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP) // 포멧
                setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB) // 엔코더
                setOutputFile(recordingFilePath) // 우리는 저장 x 캐시에
                prepare()
            }
        recorder?.start()
        binding.recordTimeTextView.startCountUp()
        binding.soundVisualizerView.startVisualizing(false)
        state = ButtonState.ON_RECORDING
    }

    private fun stopRecording() {
        recorder?.run {
            stop()
            release()
        }
        recorder = null
        binding.soundVisualizerView.stopVisualizing()
        binding.recordTimeTextView.stopCountUp()
        state = ButtonState.AFTER_RECORDING
    }

    private fun startPlaying() {
        // MediaPlayer
        player = MediaPlayer()
            .apply {
                setDataSource(recordingFilePath)
                prepare() // 재생 할 수 있는 상태 (큰 파일 또는 네트워크로 가져올 때는 prepareAsync() )
            }

        // 전부 재생 했을 때
        player?.setOnCompletionListener {
            stopPlaying()
            state = ButtonState.AFTER_RECORDING
        }

        player?.start() // 재생
        binding.recordTimeTextView.startCountUp()
        binding.soundVisualizerView.startVisualizing(true)

        state = ButtonState.ON_PLAYING
    }

    private fun stopPlaying() {
        player?.release()
        player = null
        binding.soundVisualizerView.stopVisualizing()
        binding.recordTimeTextView.stopCountUp()

        state = ButtonState.AFTER_RECORDING
    }

}