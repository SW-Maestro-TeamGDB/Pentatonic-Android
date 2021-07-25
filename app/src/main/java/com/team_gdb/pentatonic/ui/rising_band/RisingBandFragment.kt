package com.team_gdb.pentatonic.ui.rising_band

import org.koin.androidx.viewmodel.ext.android.viewModel
import com.newidea.mcpestore.libs.base.BaseFragment
import com.team_gdb.pentatonic.R
import com.team_gdb.pentatonic.databinding.FragmentRisingBandBinding

class RisingBandFragment : BaseFragment<FragmentRisingBandBinding, RisingBandViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.fragment_rising_band
    override val viewModel: RisingBandViewModel by viewModel()

    override fun initStartView() {
    }

    override fun initDataBinding() {
    }

    override fun initAfterBinding() {
    }
}