package com.team_gdb.pentatonic.ui.rising_band

import org.koin.androidx.viewmodel.ext.android.viewModel
import com.team_gdb.pentatonic.R
import com.team_gdb.pentatonic.base.BaseFragment
import com.team_gdb.pentatonic.databinding.FragmentRisingBandBinding
import com.team_gdb.pentatonic.ui.lounge.LoungeViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class RisingBandFragment : BaseFragment<FragmentRisingBandBinding, LoungeViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.fragment_rising_band
    override val viewModel: LoungeViewModel by sharedViewModel()

    override fun initStartView() {
    }

    override fun initDataBinding() {
    }

    override fun initAfterBinding() {
    }
}