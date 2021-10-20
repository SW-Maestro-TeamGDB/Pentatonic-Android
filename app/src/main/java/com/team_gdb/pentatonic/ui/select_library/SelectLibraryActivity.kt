package com.team_gdb.pentatonic.ui.select_library

import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.team_gdb.pentatonic.R
import com.team_gdb.pentatonic.adapter.library.LibrarySelectListAdapter
import com.team_gdb.pentatonic.base.BaseActivity
import com.team_gdb.pentatonic.base.BaseApplication
import com.team_gdb.pentatonic.data.model.CreatedCoverEntity
import com.team_gdb.pentatonic.databinding.ActivitySelectLibraryBinding
import com.team_gdb.pentatonic.ui.cover_view.band_cover.BandCoverActivity
import com.team_gdb.pentatonic.ui.create_cover.CreateCoverActivity.Companion.CREATED_COVER_ENTITY
import com.team_gdb.pentatonic.ui.create_record.CreateRecordActivity
import com.team_gdb.pentatonic.ui.lounge.LoungeFragment.Companion.COVER_ID
import com.team_gdb.pentatonic.ui.record_processing.RecordProcessingActivity.Companion.CREATE_COVER
import com.team_gdb.pentatonic.ui.studio.StudioFragment
import com.team_gdb.pentatonic.ui.studio.StudioFragment.Companion.SONG_ENTITY
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class SelectLibraryActivity : BaseActivity<ActivitySelectLibraryBinding, SelectLibraryViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.activity_select_library
    override val viewModel: SelectLibraryViewModel by viewModel()

    private lateinit var librarySelectListAdapter: LibrarySelectListAdapter

    // 이전 액티비티 (CreateCoverActivity) 에서 넘어온 정보
    private val createdCoverEntity: CreatedCoverEntity by lazy {
        intent.getSerializableExtra(CREATED_COVER_ENTITY) as CreatedCoverEntity
    }

    override fun initStartView() {
        binding.titleBar.titleTextView.text = "라이브러리 선택"

        // 유저 ID 를 기반으로 해당 곡에 맞는 라이브러리 쿼리
        // - userId, songId (고유값)
        viewModel.getUserLibrary(
            BaseApplication.prefs.userId!!,
            createdCoverEntity.coverSong.songId
        )

        // 라이브러리 선택변경될 때마다 selectedUserCoverID 에 라이브러리 ID 담김
        librarySelectListAdapter = LibrarySelectListAdapter {
            viewModel.selectedUserCoverID.postValue(it)
        }

        binding.libraryList.apply {
            this.layoutManager = LinearLayoutManager(context)
            this.adapter = librarySelectListAdapter
            this.setHasFixedSize(true)
        }

    }

    override fun initDataBinding() {
        // 유효한 라이브러리 필터링하여 리스트로 보여줌
        viewModel.libraryList.observe(this) {
            Timber.e(it.toString())
            val data =
                it.filter { createdCoverEntity.coverSessionConfig[0].sessionSetting.name == it.position.rawValue }
            if (data.isEmpty()) {
                // 해당 곡에 대해 커버 녹음하는 페이지로 이동할 수 있는 버튼 보여주기
                binding.libraryList.visibility = View.GONE
                binding.noLibraryImageView.visibility = View.VISIBLE
            } else {
                librarySelectListAdapter.setItem(data)
            }
        }

        viewModel.selectedUserCoverID.observe(this) {
            binding.librarySelectCompleteButton.isEnabled =
                !it.isNullOrEmpty()  // 선택된 라이브러리가 있다면 버튼 활성화
        }

        viewModel.createBandComplete.observe(this) {
            if (!it.getContentIfNotHandled().isNullOrBlank()) {
                Timber.d("createBand() Complete!")
                viewModel.joinBand(
                    sessionName = createdCoverEntity.coverSessionConfig[0].sessionSetting.name,
                    bandId = it.peekContent(),
                    coverId = viewModel.selectedUserCoverID.value!!
                )
            }
        }

        viewModel.joinBandComplete.observe(this) {
            if (it.getContentIfNotHandled() == true) {
                // 밴드 생성 및 참여 완료 시 Alert 애니메이션 실행
                val intent = Intent(this, BandCoverActivity::class.java)
                intent.putExtra(CREATE_COVER, "CREATE_COVER")
                intent.putExtra(COVER_ID, viewModel.createBandComplete.value!!.peekContent())
                finish()
                startActivity(intent)
            }
        }
    }

    override fun initAfterBinding() {
        binding.titleBar.backButton.setOnClickListener {
            finish()
        }

        binding.librarySelectCompleteButton.setOnClickListener {
            viewModel.createBand(
                sessionConfig = createdCoverEntity.coverSessionConfig,
                bandName = createdCoverEntity.coverName,
                bandIntroduction = createdCoverEntity.coverIntroduction ?: "",
                backgroundUrl = createdCoverEntity.backgroundImg,
                songId = createdCoverEntity.coverSong.songId
            )
        }

        binding.recordButton.setOnClickListener {
            val intent = Intent(this, CreateRecordActivity::class.java)
            intent.putExtra(SONG_ENTITY, createdCoverEntity.coverSong)
            startActivity(intent)
            finish()
        }
    }
}