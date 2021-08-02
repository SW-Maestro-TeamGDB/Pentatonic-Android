package com.team_gdb.pentatonic.ui.create_cover

import com.team_gdb.pentatonic.R
import com.team_gdb.pentatonic.base.BaseFragment
import com.team_gdb.pentatonic.databinding.FragmentBasicInfoFormBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class BasicInfoFormFragment : BaseFragment<FragmentBasicInfoFormBinding, CreateCoverViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.fragment_basic_info_form
    override val viewModel: CreateCoverViewModel by sharedViewModel()

    override fun initStartView() {
        binding.viewModel = this.viewModel
    }

    override fun initDataBinding() {
    }

    override fun initAfterBinding() {
    }
}