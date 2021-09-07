package com.team_gdb.pentatonic.ui.record

import android.content.Intent
import android.media.MediaRecorder
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import com.team_gdb.pentatonic.R
import com.team_gdb.pentatonic.base.BaseActivity
import com.team_gdb.pentatonic.data.model.CreatedCoverEntity
import org.koin.androidx.viewmodel.ext.android.viewModel

import com.team_gdb.pentatonic.databinding.ActivityRecordBinding
import com.team_gdb.pentatonic.media.PlayerHelper.initPlayer
import com.team_gdb.pentatonic.media.PlayerHelper.startPlaying
import com.team_gdb.pentatonic.media.PlayerHelper.stopPlaying
import com.team_gdb.pentatonic.ui.create_cover.CreateCoverActivity.Companion.CREATED_COVER_ENTITY
import com.team_gdb.pentatonic.ui.record_processing.RecordProcessingActivity
import timber.log.Timber
import kotlin.math.roundToInt

/**
 *  커버 녹음을 위한 페이지
 *  - MediaRecorder 를 통해 녹음 동작을 수행함
 *  - MediaRecorder, MediaPlayer 를 사용하지 않을 때는 메모리 해제를 꼭 해줘야 함
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

    private val createdCoverEntity: CreatedCoverEntity by lazy {
        intent.getSerializableExtra(CREATED_COVER_ENTITY) as CreatedCoverEntity
    }

    // 카운트 후 녹음 시작을 위한 CountDownTimer (3초)
    private val countDownTimer = object : CountDownTimer(3000, 1000) {
        override fun onTick(millisUntilFinished: Long) {
            binding.startCountDownTextView.text =
                "${(millisUntilFinished.toFloat() / 1000.0f).roundToInt()}초"
        }

        override fun onFinish() {
            binding.startCountDownTextView.visibility = View.GONE
            binding.totalTimeTextView.visibility = View.VISIBLE
            startPlaying()
            startRecoding()
        }
    }

    override fun initStartView() {
        val bottomSheetDialog = RecordGuideBottomSheetDialog()
        bottomSheetDialog.show(supportFragmentManager, bottomSheetDialog.tag)

        binding.titleBar.titleTextView.text = "Recording"

        Timber.d(createdCoverEntity.toString())
    }

    override fun initDataBinding() {
        viewModel.buttonState.observe(this) {
            binding.recordButton.updateIconWithState(it)
        }
    }

    override fun initAfterBinding() {
        initPlayer(mrFilePath) {
            binding.recordCompleteButton.visibility = View.VISIBLE  // 녹음 완료 페이지로 이동하는 버튼 VISIBLE
            stopPlaying()
            stopRecording()
        }

        binding.titleBar.backButton.setOnClickListener {
            finish()
        }

        // 현재 진폭을 알려줌으로써 진폭 크기에 비례하여 뷰를 그릴 수 있도록 함
        binding.soundVisualizerView.onRequestCurrentAmplitude = {
            recorder?.maxAmplitude ?: 0
        }

        binding.recordButton.setOnClickListener {
            when (viewModel.buttonState.value) {
                ButtonState.BEFORE_RECORDING -> {
                    binding.startCountDownTextView.visibility = View.VISIBLE
                    countDownTimer.start()
                }
                ButtonState.ON_RECORDING -> {
                    viewModel.buttonState.postValue(ButtonState.STOP_RECORDING)
                    stopPlaying()
                    stopRecording()
                }
                ButtonState.STOP_RECORDING -> {
                    binding.startCountDownTextView.visibility = View.VISIBLE
                    countDownTimer.start()
                }
            }
        }

        // 임시로 사용, 이상적인 로직은 녹음 후 자동으로 인텐트
        binding.recordCompleteButton.setOnClickListener {
            var byteArray = byteArrayOf()
            binding.soundVisualizerView.drawingAmplitudes.forEach {
                byteArray += it.toByte()
            }
            val intent = Intent(this, RecordProcessingActivity::class.java)
            val bundle = Bundle()
            bundle.putByteArray(AMPLITUDE_DATA, byteArray)
            bundle.putSerializable(CREATED_COVER_ENTITY, createdCoverEntity)
            intent.putExtras(bundle)
            startActivity(intent)
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

        viewModel.buttonState.postValue(ButtonState.ON_RECORDING)
    }

    private fun stopRecording() {
        recorder?.run {
            stop()
            release()
        }
        recorder = null

        binding.recordCompleteButton.visibility = View.VISIBLE  // 녹음 완료 페이지로 이동하는 버튼 VISIBLE
        binding.soundVisualizerView.stopVisualizing()
        binding.recordTimeTextView.stopCountUp()

        viewModel.buttonState.postValue(ButtonState.BEFORE_RECORDING)
    }


    companion object {
        const val AMPLITUDE_DATA = "AMPLITUDE_DATA"
    }

}