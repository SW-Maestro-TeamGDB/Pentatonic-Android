package com.team_gdb.pentatonic.ui.band_ranking

import com.team_gdb.pentatonic.R
import com.team_gdb.pentatonic.base.BaseFragment
import com.team_gdb.pentatonic.databinding.FragmentBandRankingBinding
import com.team_gdb.pentatonic.ui.artist.ArtistViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class BandRankingFragment : BaseFragment<FragmentBandRankingBinding, ArtistViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.fragment_band_ranking
    override val viewModel: ArtistViewModel by viewModel()

    override fun initStartView() {
        binding.viewModel = this.viewModel
        binding.lifecycleOwner = this
    }

    override fun initDataBinding() {
    }

    override fun initAfterBinding() {
    }
}