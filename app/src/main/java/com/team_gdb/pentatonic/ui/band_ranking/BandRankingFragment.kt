package com.team_gdb.pentatonic.ui.band_ranking

import android.content.Intent
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.team_gdb.pentatonic.R
import com.team_gdb.pentatonic.adapter.ranking.CoverRankingListAdapter
import com.team_gdb.pentatonic.base.BaseFragment
import com.team_gdb.pentatonic.databinding.FragmentBandRankingBinding
import com.team_gdb.pentatonic.ui.artist.ArtistViewModel
import com.team_gdb.pentatonic.ui.cover_view.band_cover.BandCoverActivity
import com.team_gdb.pentatonic.ui.cover_view.solo_cover.SoloCoverActivity
import com.team_gdb.pentatonic.ui.lounge.LoungeFragment
import com.team_gdb.pentatonic.ui.lounge.LoungeFragment.Companion.COVER_ID
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class BandRankingFragment : BaseFragment<FragmentBandRankingBinding, ArtistViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.fragment_band_ranking
    override val viewModel: ArtistViewModel by sharedViewModel()

    private lateinit var coverRankingListAdapter: CoverRankingListAdapter

    override fun initStartView() {
        binding.viewModel = this.viewModel
        binding.lifecycleOwner = this

        binding.titleBar.titleTextView.text = "밴드 랭킹"

        // 밴드 랭킹 리스트 어댑터
        coverRankingListAdapter =
            CoverRankingListAdapter(isDetailView = false) { coverId, isSoloBand ->
                val intent = if (isSoloBand) {
                    Intent(requireContext(), SoloCoverActivity::class.java)
                } else {
                    Intent(requireContext(), BandCoverActivity::class.java)
                }
                intent.putExtra(COVER_ID, coverId)
                startActivity(intent)
            }

        binding.bandRankingList.apply {
            this.layoutManager = LinearLayoutManager(context)
            this.adapter = coverRankingListAdapter
            this.setHasFixedSize(true)
        }
    }

    override fun initDataBinding() {
        viewModel.rankedCoverList.observe(this) {
            coverRankingListAdapter.setItem(it)
        }
    }

    override fun initAfterBinding() {
        binding.titleBar.backButton.setOnClickListener {
            findNavController().popBackStack(R.id.navigation_band_ranking, true)
        }
    }
}