package com.team_gdb.pentatonic.ui.record

import android.content.Intent
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.media.audiofx.PresetReverb
import android.os.CountDownTimer
import android.view.View
import com.newidea.mcpestore.libs.base.BaseActivity
import com.team_gdb.pentatonic.R
import org.koin.androidx.viewmodel.ext.android.viewModel

import com.team_gdb.pentatonic.databinding.ActivityRecordBinding
import com.team_gdb.pentatonic.ui.record_processing.RecordProcessingActivity
import kotlin.math.roundToInt

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

    private val mrFilePath: String by lazy {  // 서버에서 받아온 MR이 저장될 위치
        "${externalCacheDir?.absolutePath}/mr.mp3"
    }

    private var recorder: MediaRecorder? = null  // MediaRecorder 사용하지 않을 때는 메모리 해제
    private var player: MediaPlayer? = null  // MediaPlayer 사용하지 않을 때는 메모리 해제

    /**
     * TODO : state 를 viewModel 로 옮겨야 함
    옵저브 하는 부분에서 updateIconWithState() 호출해야 할듯
     */
    private var state = ButtonState.BEFORE_RECORDING
        set(value) {
            field = value
            binding.recordButton.updateIconWithState(value)
        }

    // 3초 카운트 후 녹음 시작
    private val countDownTimer = object : CountDownTimer(3000, 1000) {
        override fun onTick(millisUntilFinished: Long) {
            binding.startCountDownTextView.text =
                "${(millisUntilFinished.toFloat() / 1000.0f).roundToInt()}초"
        }

        override fun onFinish() {
            binding.startCountDownTextView.visibility = View.GONE
            startPlaying()
            startRecoding()
        }
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
                    binding.startCountDownTextView.visibility = View.VISIBLE
                    countDownTimer.start()
                }
                ButtonState.ON_RECORDING -> {
                    stopPlaying()
                    stopRecording()
                }
            }
        }

        // 임시로 사용, 이상적인 로직은 녹음 후 자동으로 인텐트
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

    /**
     * 녹음본을 재생 (리버브 이펙트 테스트)
     */
    private fun startPlaying() {
        player = MediaPlayer()
            .apply {
                setDataSource(mrFilePath)
                prepare() // 재생 할 수 있는 상태 (큰 파일 또는 네트워크로 가져올 때는 prepareAsync() )
            }

        player?.start()  // 재생

        // 끝까지 재생이 끝났을 때
        player?.setOnCompletionListener {
            stopPlaying()
            stopRecording()
        }
    }

    /**
     * 음원 재생 중지
     */
    private fun stopPlaying() {
        player?.release()
        player = null
    }

}