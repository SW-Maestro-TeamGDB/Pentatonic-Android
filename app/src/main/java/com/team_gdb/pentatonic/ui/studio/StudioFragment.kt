package com.team_gdb.pentatonic.ui.studio

import android.content.Intent
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenResumed
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.team_gdb.pentatonic.R
import com.team_gdb.pentatonic.adapter.cover_list.CoverHorizontalListAdapter
import com.team_gdb.pentatonic.base.BaseFragment
import com.team_gdb.pentatonic.databinding.FragmentStudioBinding
import com.team_gdb.pentatonic.TestData
import com.team_gdb.pentatonic.adapter.cover_list.RisingCoverViewPagerAdapter
import com.team_gdb.pentatonic.adapter.song_list.SongHorizontalListAdapter
import com.team_gdb.pentatonic.ui.create_cover.CreateCoverActivity
import com.team_gdb.pentatonic.ui.record.RecordActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class StudioFragment : BaseFragment<FragmentStudioBinding, StudioViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.fragment_studio
    override val viewModel: StudioViewModel by viewModel()

    private lateinit var recommendCoverViewPagerAdapter: RisingCoverViewPagerAdapter  // 추천 커버 리스트
    private lateinit var recommendSongListAdapter: SongHorizontalListAdapter  // 추천  리스트


    override fun initStartView() {
        binding.viewModel = this.viewModel

        // 추천 커버 뷰 페이저 어댑터 생성
        recommendCoverViewPagerAdapter = RisingCoverViewPagerAdapter {
            findNavController().navigate(
                StudioFragmentDirections.actionNavigationStudioToNavigationBandCover(
                    it
                )
            )
        }

        binding.recommendCoverViewPager.apply {
            orientation = ViewPager2.ORIENTATION_HORIZONTAL
            adapter = recommendCoverViewPagerAdapter
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)

                    //직접 유저가 스크롤했을 때 현재 위치 변경 처리
                    viewModel.setCurrentPosition(position)
                }
            })
        }

        // 추천 곡 리사이클러뷰 어댑터 생성
        recommendSongListAdapter = SongHorizontalListAdapter {

        }

        binding.recommendSongList.apply {
            layoutManager = LinearLayoutManager(context).apply {
                orientation = LinearLayoutManager.HORIZONTAL
            }
            adapter = recommendSongListAdapter
            setHasFixedSize(true)
        }
    }

    override fun initDataBinding() {
        viewModel.currentPosition.observe(this) {
            binding.recommendCoverViewPager.currentItem = it
        }
    }

    override fun initAfterBinding() {
        // 솔로 커버 버튼 클릭했을 때
        binding.makeSoloCoverButton.setOnClickListener {
            val intent = Intent(activity, CreateCoverActivity::class.java).apply {
                putExtra(COVER_MODE, SOLO_COVER)
            }
            startActivity(intent)
        }

        // 밴드 커버 버튼 클릭했을 때
        binding.makeBandCoverButton.setOnClickListener {
            val intent = Intent(activity, CreateCoverActivity::class.java).apply {
                putExtra(COVER_MODE, BAND_COVER)
            }
            startActivity(intent)
        }

        recommendCoverViewPagerAdapter.setItem(TestData.TEST_BAND_COVER_LIST)
        recommendSongListAdapter.setItem(TestData.TEST_SONG_LIST)

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


    companion object {
        const val COVER_MODE = "COVER_MODE"
        const val SOLO_COVER = "SOLO_COVER"
        const val BAND_COVER = "BAND_COVER"
    }
}