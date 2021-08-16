package com.team_gdb.pentatonic.ui.band_cover

import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.team_gdb.pentatonic.R
import com.team_gdb.pentatonic.TestLibraryData
import com.team_gdb.pentatonic.adapter.library.LibraryListAdapter
import com.team_gdb.pentatonic.adapter.library.LibrarySelectListAdapter
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
        librarySelectListAdapter.setItem(TestLibraryData.TEST_LIBRARY_DATA)
    }
}