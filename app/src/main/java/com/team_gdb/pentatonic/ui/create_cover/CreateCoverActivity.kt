package com.team_gdb.pentatonic.ui.create_cover

import com.team_gdb.pentatonic.R
import com.team_gdb.pentatonic.base.BaseActivity
import com.team_gdb.pentatonic.databinding.ActivityCreateCoverBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class CreateCoverActivity : BaseActivity<ActivityCreateCoverBinding, CreateCoverViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.activity_create_cover
    override val viewModel: CreateCoverViewModel by viewModel()

    override fun initStartView() {
        binding.viewModel = this.viewModel
    }

    override fun initDataBinding() {
    }

    override fun initAfterBinding() {
    }
}