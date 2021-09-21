package com.team_gdb.pentatonic.ui.band_cover

import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.team_gdb.pentatonic.R
import com.team_gdb.pentatonic.adapter.library.LibrarySelectListAdapter
import com.team_gdb.pentatonic.base.BaseApplication
import com.team_gdb.pentatonic.base.BaseBottomSheetDialogFragment
import com.team_gdb.pentatonic.databinding.DialogLibrarySelectBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class LibrarySelectBottomSheetDialog() :
    BaseBottomSheetDialogFragment<DialogLibrarySelectBinding, BandCoverViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.dialog_library_select
    override val viewModel: BandCoverViewModel by sharedViewModel()

    private lateinit var librarySelectListAdapter: LibrarySelectListAdapter

    override fun initStartView() {
        val offsetFromTop = 400
        (dialog as? BottomSheetDialog)?.behavior?.apply {
            isFitToContents = true
            expandedOffset = offsetFromTop
        }

        binding.viewModel = this.viewModel
        librarySelectListAdapter = LibrarySelectListAdapter {
            // TODO : 라이브러리 내의 커버를 선택했을 때 동작 정의 (밴드 참여)
        }
        binding.libraryList.apply {
            this.layoutManager = LinearLayoutManager(context)
            this.adapter = librarySelectListAdapter
            this.setHasFixedSize(true)
        }
    }

    override fun initDataBinding() {
    }

    override fun initAfterBinding() {
        binding.closeButton.setOnClickListener {
            dismiss()
        }

        // 사용자의 라이브러리 정보 쿼리
        BaseApplication.prefs.userId?.let { viewModel.getUserLibrary(it) }
    }
}