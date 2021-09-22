package com.team_gdb.pentatonic.ui.band_cover

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.team_gdb.pentatonic.R
import com.team_gdb.pentatonic.adapter.library.LibrarySelectListAdapter
import com.team_gdb.pentatonic.base.BaseApplication
import com.team_gdb.pentatonic.base.BaseBottomSheetDialogFragment
import com.team_gdb.pentatonic.databinding.DialogLibrarySelectBinding
import com.team_gdb.pentatonic.ui.band_cover.BandCoverActivity.Companion.SESSION_TYPE
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class LibrarySelectBottomSheetDialog() :
    BaseBottomSheetDialogFragment<DialogLibrarySelectBinding, BandCoverViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.dialog_library_select
    override val viewModel: BandCoverViewModel by sharedViewModel()

    private val selectedSession: String by lazy {
        arguments?.getString(SESSION_TYPE) as String
    }

    private lateinit var librarySelectListAdapter: LibrarySelectListAdapter

    override fun initStartView() {
        val offsetFromTop = 400
        (dialog as? BottomSheetDialog)?.behavior?.apply {
            isFitToContents = true
            expandedOffset = offsetFromTop
        }

        binding.viewModel = this.viewModel
        librarySelectListAdapter = LibrarySelectListAdapter {
            viewModel.selectedCoverURL.postValue(it)
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
            val data = it.filter { selectedSession == it.position.rawValue }
            if (data.isNullOrEmpty()){
                binding.buttonLayout.visibility = View.GONE
                binding.libraryList.visibility = View.GONE
            } else {
                binding.noDataImageView.visibility = View.GONE
                librarySelectListAdapter.setItem(data)
            }
        }

        // 사용자가 라이브러리를 선택했다면 참여하기 버튼 활성화
        // - 선택 해제 시 비어있는 String 을 전달하기 때문
        viewModel.selectedCoverURL.observe(this) {
            binding.joinBandButton.isEnabled = it.isNotBlank()
        }
    }

    override fun initAfterBinding() {
        binding.closeButton.setOnClickListener {
            dismiss()
        }

        binding.joinBandButton.setOnClickListener {
            // TODO : 밴드 커버 참여 동작
        }

        // 사용자의 라이브러리 정보 쿼리
        BaseApplication.prefs.userId?.let { viewModel.getUserLibrary(it) }
    }
}