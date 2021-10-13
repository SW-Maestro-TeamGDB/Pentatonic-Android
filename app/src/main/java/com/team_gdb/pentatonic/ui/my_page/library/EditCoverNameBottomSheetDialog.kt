package com.team_gdb.pentatonic.ui.my_page.library

import com.team_gdb.pentatonic.R
import com.team_gdb.pentatonic.base.BaseBottomSheetDialogFragment
import com.team_gdb.pentatonic.databinding.DialogEditCoverNameBinding
import com.team_gdb.pentatonic.ui.my_page.MyPageViewModel
import com.team_gdb.pentatonic.util.Event
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class EditCoverNameBottomSheetDialog :
    BaseBottomSheetDialogFragment<DialogEditCoverNameBinding, MyPageViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.dialog_edit_cover_name
    override val viewModel: MyPageViewModel by sharedViewModel()

    override fun initStartView() {
        binding.viewModel = this.viewModel
    }

    override fun initDataBinding() {
    }

    override fun initAfterBinding() {
        binding.confirmCoverNameButton.setOnClickListener {
            viewModel.completeEditCoverName.postValue(Event(true))
            dismiss()
        }
    }
}