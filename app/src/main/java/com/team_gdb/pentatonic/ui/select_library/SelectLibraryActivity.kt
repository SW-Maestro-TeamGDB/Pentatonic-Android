package com.team_gdb.pentatonic.ui.select_library

import androidx.recyclerview.widget.LinearLayoutManager
import com.team_gdb.pentatonic.R
import com.team_gdb.pentatonic.adapter.library.LibrarySelectListAdapter
import com.team_gdb.pentatonic.base.BaseActivity
import com.team_gdb.pentatonic.base.BaseApplication
import com.team_gdb.pentatonic.data.model.CreatedCoverEntity
import com.team_gdb.pentatonic.databinding.ActivitySelectLibraryBinding
import com.team_gdb.pentatonic.ui.create_cover.CreateCoverActivity.Companion.CREATED_COVER_ENTITY
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
            librarySelectListAdapter.setItem(data)
        }

        viewModel.selectedUserCoverID.observe(this) {
            binding.librarySelectCompleteButton.isEnabled = !it.isNullOrEmpty()  // 선택된 라이브러리가 있다면 버튼 활성화
        }
    }

    override fun initAfterBinding() {
        binding.titleBar.backButton.setOnClickListener {
            finish()
        }

        binding.librarySelectCompleteButton.setOnClickListener {
            // 라이브러리 선택 완료 시
        }

    }
}