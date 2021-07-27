package com.team_gdb.pentatonic.ui.record_processing

import android.media.MediaPlayer
import com.newidea.mcpestore.libs.base.BaseActivity
import com.team_gdb.pentatonic.R
import com.team_gdb.pentatonic.databinding.ActivityRecordProcessingBinding
import com.team_gdb.pentatonic.ui.record.ButtonState
import com.team_gdb.pentatonic.ui.record.RecordActivity.Companion.RECORD_AMPLITUDE
import org.koin.android.ext.android.bind
import org.koin.androidx.viewmodel.ext.android.viewModel

class RecordProcessingActivity :
    BaseActivity<ActivityRecordProcessingBinding, RecordProcessingViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.activity_record_processing
    override val viewModel: RecordProcessingViewModel by viewModel()

    private var player: MediaPlayer? = null
    lateinit var drawingAmplitudes: List<Int>

    private val recordingFilePath: String by lazy {  // 녹음본이 저장된 위치
        "${externalCacheDir?.absolutePath}/recording.m4a"
    }

    private var state = ButtonState.BEFORE_RECORDING
        set(value) { // setter 설정
            field = value // 실제 프로퍼티에 대입
            binding.playButton.updateIconWithState(value)
        }

    override fun initStartView() {
        drawingAmplitudes = intent.getIntegerArrayListExtra(RECORD_AMPLITUDE) as List<Int>
    }

    override fun initDataBinding() {
    }

    override fun initAfterBinding() {

        binding.playButton.setOnClickListener {
            when (state) {
                ButtonState.BEFORE_RECORDING -> {
                    startPlaying()
                }
                ButtonState.ON_RECORDING -> {
                    stopPlaying()
                }
            }
        }

    }


    /**
     * 녹음본을 재생
     */
    private fun startPlaying() {
        player = MediaPlayer()
            .apply {
                setDataSource(recordingFilePath)
                prepare() // 재생 할 수 있는 상태 (큰 파일 또는 네트워크로 가져올 때는 prepareAsync() )
            }
        binding.soundVisualizerView.drawingAmplitudes = this.drawingAmplitudes

        player?.start() // 재생
        binding.recordTimeTextView.startCountUp()
        binding.soundVisualizerView.startVisualizing(true)

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
        binding.soundVisualizerView.stopVisualizing()
        binding.recordTimeTextView.stopCountUp()

        state = ButtonState.BEFORE_PLAYING
    }

}