package com.team_gdb.pentatonic.ui.record_processing

import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.team_gdb.pentatonic.R
import com.team_gdb.pentatonic.base.BaseBottomSheetDialogFragment
import com.team_gdb.pentatonic.databinding.DialogInputCoverNameBinding
import com.team_gdb.pentatonic.databinding.DialogRecordGuideBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class InputCoverNameBottomSheetDialog: BaseBottomSheetDialogFragment<DialogInputCoverNameBinding, RecordProcessingViewModel>()  {
    override val layoutResourceId: Int
        get() = R.layout.dialog_record_guide
    override val viewModel: RecordProcessingViewModel by sharedViewModel()

    override fun initStartView() {
        binding.viewModel = this.viewModel
    }

    override fun initDataBinding() {
    }

    override fun initAfterBinding() {

    }
}