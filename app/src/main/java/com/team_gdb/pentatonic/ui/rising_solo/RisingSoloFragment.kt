package com.team_gdb.pentatonic.ui.rising_solo

import com.team_gdb.pentatonic.R
import com.team_gdb.pentatonic.base.BaseFragment
import com.team_gdb.pentatonic.databinding.FragmentRisingSoloBinding
import com.team_gdb.pentatonic.ui.lounge.LoungeViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class RisingSoloFragment : BaseFragment<FragmentRisingSoloBinding, LoungeViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.fragment_rising_solo
    override val viewModel: LoungeViewModel by sharedViewModel()

    override fun initStartView() {
    }

    override fun initDataBinding() {
    }

    override fun initAfterBinding() {
    }
}