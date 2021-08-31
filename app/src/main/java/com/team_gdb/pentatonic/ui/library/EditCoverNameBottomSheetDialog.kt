package com.team_gdb.pentatonic.ui.library

import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.team_gdb.pentatonic.R
import com.team_gdb.pentatonic.base.BaseBottomSheetDialogFragment
import com.team_gdb.pentatonic.databinding.DialogEditCoverNameBinding
import com.team_gdb.pentatonic.databinding.DialogInputCoverNameBinding
import com.team_gdb.pentatonic.databinding.DialogRecordGuideBinding
import com.team_gdb.pentatonic.ui.my_page.MyPageViewModel
import com.team_gdb.pentatonic.util.Event
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class EditCoverNameBottomSheetDialog :
    BaseBottomSheetDialogFragment<DialogEditCoverNameBinding, MyPageViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.dialog_input_cover_name
    override val viewModel: MyPageViewModel by sharedViewModel()

    override fun initStartView() {
        binding.viewModel = this.viewModel
    }

    override fun initDataBinding() {
    }

    override fun initAfterBinding() {
        binding.confirmCoverNameButton.setOnClickListener {
            dismiss()
        }
    }
}