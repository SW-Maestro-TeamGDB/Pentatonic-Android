package com.team_gdb.pentatonic.ui.artist

import android.content.Intent
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenResumed
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.team_gdb.pentatonic.R
import com.team_gdb.pentatonic.TestData
import com.team_gdb.pentatonic.TestRisingCoverData
import com.team_gdb.pentatonic.adapter.cover_list.CoverHorizontalListAdapter
import com.team_gdb.pentatonic.adapter.cover_list.RisingCoverViewPagerAdapter
import com.team_gdb.pentatonic.adapter.ranking.ArtistRankingListAdapter
import com.team_gdb.pentatonic.adapter.ranking.BandRankingListAdapter
import com.team_gdb.pentatonic.base.BaseFragment
import com.team_gdb.pentatonic.databinding.FragmentArtistBinding
import com.team_gdb.pentatonic.ui.band_cover.BandCoverActivity
import com.team_gdb.pentatonic.ui.lounge.LoungeFragment
import com.team_gdb.pentatonic.ui.profile.ProfileActivity
import com.team_gdb.pentatonic.ui.upload_test.UploadTestActivity
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
        viewModel.currentPosition.observe(this) {
            binding.risingCoverViewPager.currentItem = it
        }
    }

    override fun initAfterBinding() {
        risingBandCoverViewPagerAdapter.setItem(TestRisingCoverData.TEST_BAND_COVER_LIST)

        bandRankingListAdapter.setItem(TestData.TEST_BAND_COVER_LIST)
        artistRankingListAdapter.setItem(TestData.TEST_USER_LIST)

        autoScrollViewPager()
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