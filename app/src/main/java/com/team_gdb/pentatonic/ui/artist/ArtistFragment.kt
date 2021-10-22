package com.team_gdb.pentatonic.ui.artist

import android.content.Intent
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenResumed
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.team_gdb.pentatonic.R
import com.team_gdb.pentatonic.adapter.cover_list.RecommendCoverViewPagerAdapter
import com.team_gdb.pentatonic.adapter.ranking.ArtistRankingListAdapter
import com.team_gdb.pentatonic.adapter.ranking.CoverRankingListAdapter
import com.team_gdb.pentatonic.base.BaseFragment
import com.team_gdb.pentatonic.databinding.FragmentArtistBinding
import com.team_gdb.pentatonic.ui.cover_view.band_cover.BandCoverActivity
import com.team_gdb.pentatonic.ui.lounge.LoungeFragment
import com.team_gdb.pentatonic.ui.lounge.LoungeFragment.Companion.COVER_ID
import com.team_gdb.pentatonic.ui.profile.ProfileActivity
import com.team_gdb.pentatonic.ui.profile.ProfileActivity.Companion.USER_ID
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class ArtistFragment : BaseFragment<FragmentArtistBinding, ArtistViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.fragment_artist
    override val viewModel: ArtistViewModel by sharedViewModel()

    private lateinit var recommendBandCoverViewPagerAdapter: RecommendCoverViewPagerAdapter  // 라이징 밴드 커버 뷰페이저
    private lateinit var coverRankingListAdapter: CoverRankingListAdapter  // 밴드 랭킹 리스트
    private lateinit var userRankingListAdapter: ArtistRankingListAdapter  // 아티스트 랭킹 리스트

    override fun initStartView() {
        binding.viewModel = this.viewModel
        binding.lifecycleOwner = this

        // 라이징 커버 뷰 페이저 어댑터 생성
        recommendBandCoverViewPagerAdapter = RecommendCoverViewPagerAdapter { isSoloBand, id ->
            val intent = Intent(requireContext(), BandCoverActivity::class.java)
            intent.putExtra(COVER_ID, id)
            startActivity(intent)
        }

        binding.risingCoverViewPager.apply {
            orientation = ViewPager2.ORIENTATION_HORIZONTAL
            adapter = recommendBandCoverViewPagerAdapter
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)

                    //직접 유저가 스크롤했을 때 현재 위치 변경 처리
                    viewModel.setCurrentPosition(position)
                }
            })
        }

        binding.viewpagerIndicator.setViewPager2(binding.risingCoverViewPager)


        // 밴드 랭킹 리스트 어댑터
        coverRankingListAdapter = CoverRankingListAdapter(isDetailView = false) {
            val intent = Intent(requireContext(), BandCoverActivity::class.java)
            intent.putExtra(COVER_ID, it)
            startActivity(intent)
        }

        binding.bandRankingList.apply {
            this.layoutManager = LinearLayoutManager(context)
            this.adapter = coverRankingListAdapter
            this.setHasFixedSize(true)
        }

        // 아티스트 랭킹 리스트 어댑터
        userRankingListAdapter = ArtistRankingListAdapter(isDetailView = false) {
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
        viewModel.currentPosition.observe(this) {
            binding.risingCoverViewPager.currentItem = it
        }

        viewModel.rankedCoverList.observe(this) {
            coverRankingListAdapter.setItem(it)
        }

        viewModel.rankedUserList.observe(this) {
            userRankingListAdapter.setItem(it)
        }
    }

    override fun initAfterBinding() {
//        recommendBandCoverViewPagerAdapter.setItem(TestRisingCoverData.TEST_BAND_COVER_LIST)

        viewModel.getRankedCoverList()
        viewModel.getRankedUserList()

        autoScrollViewPager()

        binding.bandCoverRankingDetailButton.setOnClickListener {
            findNavController().navigate(
                R.id.action_navigation_artist_to_navigation_band_ranking
            )
        }

        binding.artistRankingDetailButton.setOnClickListener {
            findNavController().navigate(
                R.id.action_navigation_artist_to_navigation_artist_ranking
            )
        }

        binding.wholeArtistButton.setOnClickListener {
            findNavController().navigate(
                R.id.action_navigation_artist_to_navigation_whole_artist
            )
        }
    }

    private fun autoScrollViewPager() {
        lifecycleScope.launch {
            while (true) {
                whenResumed {
                    delay(3000)
                    viewModel.getCurrentPosition()?.let {
                        viewModel.setCurrentPosition((it.plus(1)) % 3)
                    }
                }
            }
        }
    }

}