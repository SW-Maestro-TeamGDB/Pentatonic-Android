package com.team_gdb.pentatonic.ui.band_ranking

import android.content.Intent
import androidx.recyclerview.widget.LinearLayoutManager
import com.team_gdb.pentatonic.R
import com.team_gdb.pentatonic.TestData
import com.team_gdb.pentatonic.adapter.ranking.BandRankingListAdapter
import com.team_gdb.pentatonic.base.BaseFragment
import com.team_gdb.pentatonic.databinding.FragmentBandRankingBinding
import com.team_gdb.pentatonic.ui.artist.ArtistViewModel
import com.team_gdb.pentatonic.ui.cover_view.band_cover.BandCoverActivity
import com.team_gdb.pentatonic.ui.lounge.LoungeFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class BandRankingFragment : BaseFragment<FragmentBandRankingBinding, ArtistViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.fragment_band_ranking
    override val viewModel: ArtistViewModel by viewModel()

    private lateinit var bandRankingListAdapter: BandRankingListAdapter

    override fun initStartView() {
        binding.viewModel = this.viewModel
        binding.lifecycleOwner = this

        // 밴드 랭킹 리스트 어댑터
        bandRankingListAdapter = BandRankingListAdapter {
            val intent = Intent(requireContext(), BandCoverActivity::class.java)
            intent.putExtra(LoungeFragment.COVER_ENTITY, it)
            startActivity(intent)
        }

        binding.bandRankingList.apply {
            this.layoutManager = LinearLayoutManager(context)
            this.adapter = bandRankingListAdapter
            this.setHasFixedSize(true)
        }
    }

    override fun initDataBinding() {
    }

    override fun initAfterBinding() {
        bandRankingListAdapter.setItem(TestData.TEST_BAND_COVER_LIST)
    }
}