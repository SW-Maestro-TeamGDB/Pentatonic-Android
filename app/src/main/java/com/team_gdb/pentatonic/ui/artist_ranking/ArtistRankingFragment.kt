package com.team_gdb.pentatonic.ui.artist_ranking

import android.content.Intent
import androidx.recyclerview.widget.LinearLayoutManager
import com.team_gdb.pentatonic.R
import com.team_gdb.pentatonic.adapter.ranking.ArtistRankingListAdapter
import com.team_gdb.pentatonic.base.BaseFragment
import com.team_gdb.pentatonic.databinding.FragmentArtistRankingBinding
import com.team_gdb.pentatonic.ui.artist.ArtistViewModel
import com.team_gdb.pentatonic.ui.profile.ProfileActivity
import com.team_gdb.pentatonic.ui.profile.ProfileActivity.Companion.USER_ID
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class ArtistRankingFragment : BaseFragment<FragmentArtistRankingBinding, ArtistViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.fragment_artist_ranking
    override val viewModel: ArtistViewModel by sharedViewModel()

    private lateinit var userRankingListAdapter: ArtistRankingListAdapter

    override fun initStartView() {
        binding.viewModel = this.viewModel
        binding.lifecycleOwner = this

        // 아티스트 랭킹 리스트 어댑터
        userRankingListAdapter = ArtistRankingListAdapter(isDetailView = true){
            val intent = Intent(requireContext(), ProfileActivity::class.java)
            intent.putExtra(USER_ID, it)
            startActivity(intent)
        }

        binding.artistRankingList.apply {
            this.layoutManager = LinearLayoutManager(context)
            this.adapter = userRankingListAdapter
            this.setHasFixedSize(true)
        }
    }

    override fun initDataBinding() {
        viewModel.rankedUserList.observe(this) {
            userRankingListAdapter.setItem(it)
        }
    }

    override fun initAfterBinding() {
        binding.titleBar.titleTextView.text = "아티스트 랭킹"
    }
}