package com.team_gdb.pentatonic.ui.record_processing

import com.newidea.mcpestore.libs.base.BaseActivity
import com.team_gdb.pentatonic.R
import com.team_gdb.pentatonic.databinding.ActivityRecordProcessingBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class RecordProcessingActivity : BaseActivity<ActivityRecordProcessingBinding, RecordProcessingViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.activity_record_processing
    override val viewModel: RecordProcessingViewModel by viewModel()
    override fun initStartView() {
    }

    override fun initDataBinding() {
    }

    override fun initAfterBinding() {
    }
}