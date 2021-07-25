package com.team_gdb.pentatonic.ui.record

import android.media.MediaRecorder
import com.newidea.mcpestore.libs.base.BaseActivity
import com.team_gdb.pentatonic.R
import org.koin.androidx.viewmodel.ext.android.viewModel

import com.team_gdb.pentatonic.databinding.ActivityRecordBinding

class RecordActivity : BaseActivity<ActivityRecordBinding, RecordViewModel>() {
    private var recorder: MediaRecorder? = null // MediaRecorder 사용하지 않을 때는 메모리 해제

    override val layoutResourceId: Int
        get() = R.layout.activity_record
    override val viewModel: RecordViewModel by viewModel()

    override fun initStartView() {
    }

    override fun initDataBinding() {
    }

    override fun initAfterBinding() {
    }

    private var state = ButtonState.BEFORE_RECORDING
        set(value) { // setter 설정
            field = value // 실제 프로퍼티에 대입
            binding.resetButton.isEnabled = (value == ButtonState.AFTER_RECORDING || value == ButtonState.ON_PLAYING)
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
//        binding.recordTimeTextView.startCountup()
//        soundVisualizerView.startVisualizing(false)
        state = ButtonState.ON_RECORDING
    }

    private val recordingFilePath: String by lazy {
        "${externalCacheDir?.absolutePath}/recording.3gp"
    }

    private fun stopRecording() {
        recorder?.run {
            stop()
            release()
        }
        recorder = null
//        soundVisualizerView.stopVisualizing()
//        binding.recordTimeTextView.stopCountup()
        state = ButtonState.AFTER_RECORDING
    }
}