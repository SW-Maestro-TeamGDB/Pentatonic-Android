package com.team_gdb.pentatonic.ui.rising_solo

import com.team_gdb.pentatonic.R
import com.team_gdb.pentatonic.base.BaseFragment
import com.team_gdb.pentatonic.databinding.FragmentRisingSoloBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class RisingSoloFragment : BaseFragment<FragmentRisingSoloBinding, RisingSoloViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.fragment_rising_solo
    override val viewModel: RisingSoloViewModel by viewModel()

    override fun initStartView() {
    }

    override fun initDataBinding() {
    }

    override fun initAfterBinding() {
    }
}