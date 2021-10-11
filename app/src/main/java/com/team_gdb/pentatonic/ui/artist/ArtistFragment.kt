package com.team_gdb.pentatonic.ui.artist

import android.content.Intent
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenResumed
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.team_gdb.pentatonic.R
import com.team_gdb.pentatonic.TestData
import com.team_gdb.pentatonic.TestRisingCoverData
import com.team_gdb.pentatonic.adapter.cover_list.RisingCoverViewPagerAdapter
import com.team_gdb.pentatonic.adapter.ranking.ArtistRankingListAdapter
import com.team_gdb.pentatonic.adapter.ranking.BandRankingListAdapter
import com.team_gdb.pentatonic.base.BaseFragment
import com.team_gdb.pentatonic.databinding.FragmentArtistBinding
import com.team_gdb.pentatonic.ui.cover_view.band_cover.BandCoverActivity
import com.team_gdb.pentatonic.ui.lounge.LoungeFragment
import com.team_gdb.pentatonic.ui.profile.ProfileActivity
import com.team_gdb.pentatonic.ui.profile.ProfileActivity.Companion.USER_ID
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ArtistFragment : BaseFragment<FragmentArtistBinding, ArtistViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.fragment_artist
    override val viewModel: ArtistViewModel by viewModel()

    private lateinit var risingBandCoverViewPagerAdapter: RisingCoverViewPagerAdapter  // 라이징 밴드 커버 뷰페이저
    private lateinit var bandRankingListAdapter: BandRankingListAdapter  // 밴드 랭킹 리스트
    private lateinit var artistRankingListAdapter: ArtistRankingListAdapter  // 아티스트 랭킹 리스트

    override fun initStartView() {
        binding.viewModel = this.viewModel
        binding.lifecycleOwner = this

        // 라이징 커버 뷰 페이저 어댑터 생성
        risingBandCoverViewPagerAdapter = RisingCoverViewPagerAdapter {
            val intent = Intent(requireContext(), BandCoverActivity::class.java)
            intent.putExtra(LoungeFragment.COVER_ENTITY, it)
            startActivity(intent)
        }

        binding.risingCoverViewPager.apply {
            orientation = ViewPager2.ORIENTATION_HORIZONTAL
            adapter = risingBandCoverViewPagerAdapter
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
            intent.putExtra(USER_ID, it)
            startActivity(intent)
        }

        binding.artistRankingList.apply {
            this.layoutManager = LinearLayoutManager(context)
            this.adapter = artistRankingListAdapter
            this.setHasFixedSize(true)
        }
    }

    override fun initDataBinding() {
        viewModel.currentPosition.observe(this) {
            binding.risingCoverViewPager.currentItem = it
        }

        viewModel.rankedCoverList.observe(this) {

        }

        viewModel.rankedUserList.observe(this) {

        }
    }

    override fun initAfterBinding() {
        risingBandCoverViewPagerAdapter.setItem(TestRisingCoverData.TEST_BAND_COVER_LIST)

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