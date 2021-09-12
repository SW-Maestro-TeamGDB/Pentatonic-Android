package com.team_gdb.pentatonic.ui.solo_cover


import org.koin.androidx.viewmodel.ext.android.viewModel
import com.team_gdb.pentatonic.R
import com.team_gdb.pentatonic.base.BaseActivity
import com.team_gdb.pentatonic.databinding.ActivitySoloCoverBinding

class SoloCoverActivity : BaseActivity<ActivitySoloCoverBinding, SoloCoverViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.activity_solo_cover
    override val viewModel: SoloCoverViewModel by viewModel()

    override fun initStartView() {
    }

    override fun initDataBinding() {
    }

    override fun initAfterBinding() {
    }
}