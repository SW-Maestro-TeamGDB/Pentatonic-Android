package com.team_gdb.pentatonic.ui.record

import android.media.MediaPlayer
import android.media.MediaRecorder
import com.newidea.mcpestore.libs.base.BaseActivity
import com.team_gdb.pentatonic.R
import org.koin.androidx.viewmodel.ext.android.viewModel

import com.team_gdb.pentatonic.databinding.ActivityRecordBinding

/**
 *  커버 녹음을 위한 페이지
 *  - MediaRecorder 를 통해 녹음 동작을 수행함
 *  - MediaRecorder, MediaPlayer 를 사용하지 않을 때는 메모리 해제를 꼭 해줘야함
 */
class RecordActivity : BaseActivity<ActivityRecordBinding, RecordViewModel>() {
    private var recorder: MediaRecorder? = null // MediaRecorder 사용하지 않을 때는 메모리 해제
    private var player: MediaPlayer? = null

    override val layoutResourceId: Int
        get() = R.layout.activity_record
    override val viewModel: RecordViewModel by viewModel()

    private val recordingFilePath: String by lazy {  // 녹음본이 저장될 위치
        "${externalCacheDir?.absolutePath}/recording.3gp"
    }

    /**
     * TODO : state 를 viewModel 로 옮겨야 함
              옵저브 하는 부분에서 updateIconWithState() 호출해야 할듯
     */
    private var state = ButtonState.BEFORE_RECORDING
        set(value) { // setter 설정
            field = value // 실제 프로퍼티에 대입
            binding.resetButton.isEnabled =
                (value == ButtonState.AFTER_RECORDING || value == ButtonState.ON_PLAYING)
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


    }


    /**
     * 녹음 버튼이 눌렸을 때 동작
     */
    private fun startRecoding() {
        // 녹음 시작 시 초기화
        recorder = MediaRecorder()
            .apply {
                setAudioSource(MediaRecorder.AudioSource.MIC)
                setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP) // 포멧
                setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB) // 인코더
                setOutputFile(recordingFilePath) // 저장 경로 (캐싱 방식으로 구현)
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

    /**
     * 녹음 완료한 파일을 재생함
     */
    private fun startPlaying() {
        player = MediaPlayer()
            .apply {
                setDataSource(recordingFilePath)
                prepare() // 재생 할 수 있는 상태 (큰 파일 또는 네트워크로 가져올 때는 prepareAsync() )
            }

        player?.start() // 재생
        binding.recordTimeTextView.startCountUp()
        binding.soundVisualizerView.startVisualizing(true)


        // 끝까지 재생이 끝났을 때
        player?.setOnCompletionListener {
            stopPlaying()
            state = ButtonState.AFTER_RECORDING
        }

        state = ButtonState.ON_PLAYING
    }

    /**
     * 음원 재생 중지
     */
    private fun stopPlaying() {
        player?.release()
        player = null
        binding.soundVisualizerView.stopVisualizing()
        binding.recordTimeTextView.stopCountUp()

        state = ButtonState.AFTER_RECORDING
    }

}