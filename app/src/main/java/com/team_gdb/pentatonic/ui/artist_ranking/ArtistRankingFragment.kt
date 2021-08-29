package com.team_gdb.pentatonic.ui.artist_ranking

import android.content.Intent
import androidx.recyclerview.widget.LinearLayoutManager
import com.team_gdb.pentatonic.R
import com.team_gdb.pentatonic.TestData
import com.team_gdb.pentatonic.adapter.ranking.ArtistRankingListAdapter
import com.team_gdb.pentatonic.base.BaseFragment
import com.team_gdb.pentatonic.databinding.FragmentArtistRankingBinding
import com.team_gdb.pentatonic.ui.artist.ArtistViewModel
import com.team_gdb.pentatonic.ui.band_cover.BandCoverActivity
import com.team_gdb.pentatonic.ui.profile.ProfileActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class ArtistRankingFragment : BaseFragment<FragmentArtistRankingBinding, ArtistViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.fragment_artist_ranking
    override val viewModel: ArtistViewModel by viewModel()

    private lateinit var artistRankingListAdapter: ArtistRankingListAdapter

    override fun initStartView() {
        binding.viewModel = this.viewModel
        binding.lifecycleOwner = this

        // 아티스트 랭킹 리스트 어댑터
        artistRankingListAdapter = ArtistRankingListAdapter {
            val intent = Intent(requireContext(), ProfileActivity::class.java)
            intent.putExtra(BandCoverActivity.USER_ENTITY, it)
            startActivity(intent)
        }

        binding.artistRankingList.apply {
            this.layoutManager = LinearLayoutManager(context)
            this.adapter = artistRankingListAdapter
            this.setHasFixedSize(true)
        }
    }

    override fun initDataBinding() {
    }

    override fun initAfterBinding() {
        artistRankingListAdapter.setItem(TestData.TEST_USER_LIST)
    }
}