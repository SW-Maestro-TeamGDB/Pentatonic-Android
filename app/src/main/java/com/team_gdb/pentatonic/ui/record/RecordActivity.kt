package com.team_gdb.pentatonic.ui.record

import android.content.Intent
import android.media.MediaRecorder
import android.media.audiofx.AudioEffect
import com.newidea.mcpestore.libs.base.BaseActivity
import com.team_gdb.pentatonic.R
import org.koin.androidx.viewmodel.ext.android.viewModel

import com.team_gdb.pentatonic.databinding.ActivityRecordBinding
import com.team_gdb.pentatonic.ui.record_processing.RecordProcessingActivity

/**
 *  커버 녹음을 위한 페이지
 *  - MediaRecorder 를 통해 녹음 동작을 수행함
 *  - MediaRecorder, MediaPlayer 를 사용하지 않을 때는 메모리 해제를 꼭 해줘야함
 */
class RecordActivity : BaseActivity<ActivityRecordBinding, RecordViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.activity_record
    override val viewModel: RecordViewModel by viewModel()

    private val recordingFilePath: String by lazy {  // 녹음본이 저장될 위치
        "${externalCacheDir?.absolutePath}/recording.m4a"
    }

    private var recorder: MediaRecorder? = null // MediaRecorder 사용하지 않을 때는 메모리 해제

    /**
     * TODO : state 를 viewModel 로 옮겨야 함
    옵저브 하는 부분에서 updateIconWithState() 호출해야 할듯
     */
    private var state = ButtonState.BEFORE_RECORDING
        set(value) {
            field = value
            binding.resetButton.isEnabled = (value == ButtonState.ON_RECORDING)
            binding.recordButton.updateIconWithState(value)
        }

    override fun initStartView() {
        binding.recordButton.updateIconWithState(state)
    }

    override fun initDataBinding() {
    }

    override fun initAfterBinding() {
        // 현재 진폭을 알려줌으로써 진폭 크기에 비례하여 뷰를 그릴 수 있도록 함
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
            }
        }

        // 임시로 사용, 이상적인 로직은 녹음 후 자동으로 인텐트
        // - 페이지 이동할 때, 해당 녹음본의 진폭 정보를 같이 넘겨줌
        binding.recordCompleteButton.setOnClickListener {
            startActivity(Intent(this, RecordProcessingActivity::class.java))
        }

    }


    /**
     * 녹음 버튼이 눌렸을 때 동작
     */
    private fun startRecoding() {
        // 녹음 시작 시 초기화
        recorder = MediaRecorder()
            .apply {
                setAudioSource(MediaRecorder.AudioSource.MIC)
                setOutputFormat(MediaRecorder.OutputFormat.MPEG_4) // 포멧
                setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB) // 인코더
                setOutputFile(recordingFilePath) // 저장 경로 (캐싱 방식으로 구현)
                prepare()
            }
        recorder?.start()
        // 이전 상태 삭제
        binding.soundVisualizerView.clearVisualization()
        binding.recordTimeTextView.clearCountTime()

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


        state = ButtonState.BEFORE_RECORDING
    }

    companion object {
        val RECORD_AMPLITUDE = "RECORD_AMPLITUDE"
    }

}