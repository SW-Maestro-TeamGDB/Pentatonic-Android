package com.team_gdb.pentatonic.ui.artist_ranking

import com.team_gdb.pentatonic.R
import com.team_gdb.pentatonic.base.BaseFragment
import com.team_gdb.pentatonic.databinding.FragmentArtistRankingBinding
import com.team_gdb.pentatonic.ui.artist.ArtistViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ArtistRankingFragment : BaseFragment<FragmentArtistRankingBinding, ArtistViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.fragment_artist_ranking
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