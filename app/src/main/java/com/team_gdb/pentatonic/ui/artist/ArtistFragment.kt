package com.team_gdb.pentatonic.ui.artist

import android.content.Intent
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.team_gdb.pentatonic.R
import com.team_gdb.pentatonic.TestData
import com.team_gdb.pentatonic.adapter.cover_list.CoverHorizontalListAdapter
import com.team_gdb.pentatonic.adapter.ranking.ArtistRankingListAdapter
import com.team_gdb.pentatonic.adapter.ranking.BandRankingListAdapter
import com.team_gdb.pentatonic.base.BaseFragment
import com.team_gdb.pentatonic.databinding.FragmentArtistBinding
import com.team_gdb.pentatonic.ui.band_cover.BandCoverActivity
import com.team_gdb.pentatonic.ui.lounge.LoungeFragment
import com.team_gdb.pentatonic.ui.profile.ProfileActivity
import com.team_gdb.pentatonic.ui.upload_test.UploadTestActivity

class ArtistFragment : BaseFragment<FragmentArtistBinding, ArtistViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.fragment_artist
    override val viewModel: ArtistViewModel by viewModel()

    private lateinit var bandRankingListAdapter: BandRankingListAdapter
    private lateinit var artistRankingListAdapter: ArtistRankingListAdapter

    override fun initStartView() {
        binding.viewModel = this.viewModel

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
        bandRankingListAdapter.setItem(TestData.TEST_BAND_COVER_LIST)
    }

}