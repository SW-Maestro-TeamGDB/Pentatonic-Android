package com.team_gdb.pentatonic.ui.create_cover.session_setting

import com.team_gdb.pentatonic.R
import com.team_gdb.pentatonic.base.BaseFragment
import com.team_gdb.pentatonic.databinding.FragmentSoloCoverSessionSelectBinding
import com.team_gdb.pentatonic.ui.create_cover.CreateCoverViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class SoloCoverSessionSettingFragment :
    BaseFragment<FragmentSoloCoverSessionSelectBinding, CreateCoverViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.fragment_solo_cover_session_setting

    override val viewModel: CreateCoverViewModel by sharedViewModel()

    override fun initStartView() {
        binding.viewModel = this.viewModel
    }

    override fun initDataBinding() {
    }

    override fun initAfterBinding() {
    }
}