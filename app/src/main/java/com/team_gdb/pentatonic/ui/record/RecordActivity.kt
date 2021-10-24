package com.team_gdb.pentatonic.ui.record

import android.content.Intent
import android.media.MediaRecorder
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import androidx.annotation.RequiresApi
import com.team_gdb.pentatonic.R
import com.team_gdb.pentatonic.base.BaseActivity
import com.team_gdb.pentatonic.custom_view.ButtonState
import com.team_gdb.pentatonic.data.model.CreatedRecordEntity
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.team_gdb.pentatonic.databinding.ActivityRecordBinding
import com.team_gdb.pentatonic.media.ExoPlayerHelper.initPlayer
import com.team_gdb.pentatonic.media.ExoPlayerHelper.pausePlaying
import com.team_gdb.pentatonic.media.ExoPlayerHelper.startPlaying
import com.team_gdb.pentatonic.media.ExoPlayerHelper.stopPlaying
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
        "${externalCacheDir?.absolutePath}/recording.mp3"
    }
    private var recorder: MediaRecorder? = null  // MediaRecorder 사용하지 않을 때는 메모리 해제

    private val createdCoverEntity: CreatedRecordEntity by lazy {
        intent.getSerializableExtra(CREATED_COVER_ENTITY) as CreatedRecordEntity
    }

    private val mrFilePath: String by lazy {  // MR 스트리밍 URL
        createdCoverEntity.recordSong.songUrl
    }

    // 카운트 후 녹음 시작을 위한 CountDownTimer (3초)
    private val countDownTimer = object : CountDownTimer(3000, 1000) {
        override fun onTick(millisUntilFinished: Long) {
            binding.startCountDownTextView.text =
                "${(millisUntilFinished.toFloat() / 1000.0f).roundToInt()}초"
        }

        @RequiresApi(Build.VERSION_CODES.Q)
        override fun onFinish() {
            binding.startCountDownTextView.visibility = View.GONE
            binding.totalTimeTextView.visibility = View.VISIBLE
            binding.recordTimeTextView.visibility = View.VISIBLE
            // 지정곡이라면 MR 재생, 만약 자유곡이면 MR 재생 X
            if (!createdCoverEntity.recordSong.isFreeSong) {
                startPlaying()
            }
            startRecoding()
        }
    }

    override fun initStartView() {
        val bottomSheetDialog = RecordGuideBottomSheetDialog()
        bottomSheetDialog.show(supportFragmentManager, bottomSheetDialog.tag)

        binding.titleBar.titleTextView.text = "Recording"

        if (!createdCoverEntity.recordSong.isFreeSong) {
            binding.totalTimeTextView.visibility = View.GONE
        } else {
            binding.totalTimeTextView.text =
                "${(createdCoverEntity.recordSong.duration / 60).toInt()}:${(createdCoverEntity.recordSong.duration % 60).toInt()}"
        }

        Timber.d(createdCoverEntity.toString())
    }

    override fun initDataBinding() {
        viewModel.buttonState.observe(this) {
            binding.recordButton.updateIconWithState(it)
        }
    }

    override fun initAfterBinding() {
        initPlayer(mrFilePath) {
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
                    binding.recordTimeTextView.visibility = View.GONE
                    binding.totalTimeTextView.visibility = View.GONE
                    binding.startCountDownTextView.visibility = View.VISIBLE
                    countDownTimer.start()
                }
                ButtonState.ON_RECORDING -> {
                    viewModel.buttonState.postValue(ButtonState.STOP_RECORDING)
                    binding.recordCompleteButton.visibility =
                        View.VISIBLE  // 녹음 완료 페이지로 이동하는 버튼 VISIBLE
                    initPlayer(mrFilePath) {
                        stopPlaying()
                        stopRecording()
                    }  // 다시 누를 시 녹음 & 재생 처음부터 다시 될 수 있도록 초기화

                    binding.soundVisualizerView.stopVisualizing()
                    binding.recordTimeTextView.stopCountUp()
                }
                ButtonState.STOP_RECORDING -> {
                    binding.recordTimeTextView.visibility = View.GONE
                    binding.totalTimeTextView.visibility = View.GONE
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
            finish()
            startActivity(intent)
        }

    }

    /**
     * 녹음 버튼이 눌렸을 때 동작
     */
    @RequiresApi(Build.VERSION_CODES.Q)
    private fun startRecoding() {
        // 녹음 시작 시 초기화
        recorder = MediaRecorder()
            .apply {
                setAudioSource(MediaRecorder.AudioSource.VOICE_PERFORMANCE)
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

    override fun onDestroy() {
        super.onDestroy()

        stopPlaying()
    }

    companion object {
        const val AMPLITUDE_DATA = "AMPLITUDE_DATA"
    }

}