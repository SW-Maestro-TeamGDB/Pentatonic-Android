package com.team_gdb.pentatonic.ui.record

import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.team_gdb.pentatonic.R
import com.team_gdb.pentatonic.base.BaseBottomSheetDialogFragment
import com.team_gdb.pentatonic.databinding.DialogRecordGuideBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class RecordGuideBottomSheetDialog: BaseBottomSheetDialogFragment<DialogRecordGuideBinding, RecordViewModel>()  {
    override val layoutResourceId: Int
        get() = R.layout.dialog_record_guide
    override val viewModel: RecordViewModel by sharedViewModel()

    override fun initStartView() {
        binding.viewModel = this.viewModel
    }

    override fun initDataBinding() {
    }

    override fun initAfterBinding() {
        binding.confirmGuideButton.setOnClickListener {
            dismiss()
        }
    }
}