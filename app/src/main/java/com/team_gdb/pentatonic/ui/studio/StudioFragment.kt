package com.team_gdb.pentatonic.ui.studio

import android.content.Intent
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenResumed
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.team_gdb.pentatonic.R
import com.team_gdb.pentatonic.base.BaseFragment
import com.team_gdb.pentatonic.databinding.FragmentStudioBinding
import com.team_gdb.pentatonic.adapter.cover_list.RecommendCoverViewPagerAdapter
import com.team_gdb.pentatonic.adapter.song_list.SongHorizontalListAdapter
import com.team_gdb.pentatonic.base.BaseApplication
import com.team_gdb.pentatonic.ui.cover_view.band_cover.BandCoverActivity
import com.team_gdb.pentatonic.ui.cover_view.solo_cover.SoloCoverActivity
import com.team_gdb.pentatonic.ui.create_cover.CreateCoverActivity
import com.team_gdb.pentatonic.ui.create_record.CreateRecordActivity
import com.team_gdb.pentatonic.ui.lounge.LoungeFragment.Companion.COVER_ID
import com.team_gdb.pentatonic.ui.song_detail.SongDetailActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class StudioFragment : BaseFragment<FragmentStudioBinding, StudioViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.fragment_studio
    override val viewModel: StudioViewModel by viewModel()

    private lateinit var recommendCoverViewPagerAdapter: RecommendCoverViewPagerAdapter  // 추천 커버 리스트
    private lateinit var recommendSongListAdapter: SongHorizontalListAdapter  // 추천  리스트


    override fun initStartView() {
        binding.viewModel = this.viewModel
        binding.lifecycleOwner = this

        viewModel.getSongList()
        viewModel.getRecommendCoverList()

        setImageResource()  // 정적 이미지 리소스 추가

        // 추천 커버 뷰 페이저 어댑터 생성
        recommendCoverViewPagerAdapter = RecommendCoverViewPagerAdapter { isSoloBand, id ->
            val intent: Intent = if (isSoloBand) {
                Intent(requireContext(), SoloCoverActivity::class.java)
            } else {
                Intent(requireContext(), BandCoverActivity::class.java)
            }
            intent.putExtra(COVER_ID, id)
            startActivity(intent)
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

        binding.viewpagerIndicator.setViewPager2(binding.recommendCoverViewPager)

        // 추천 곡 리사이클러뷰 어댑터 생성
        recommendSongListAdapter = SongHorizontalListAdapter {
            val intent = Intent(activity?.applicationContext, SongDetailActivity::class.java)
            intent.putExtra(SONG_ENTITY, it)
            startActivity(intent)
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

        viewModel.recommendCoverList.observe(this) {
            recommendCoverViewPagerAdapter.setItem(it)
        }

        viewModel.songList.observe(this) {
            recommendSongListAdapter.setItem(it)
        }
    }

    override fun initAfterBinding() {
        // 사용자 닉네임 설정
        binding.usernameTextView.text = BaseApplication.prefs.username

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

        binding.createRecordButton.setOnClickListener {
            val intent = Intent(activity, CreateRecordActivity::class.java)
            startActivity(intent)
        }
        autoScrollViewPager()
    }

    private fun autoScrollViewPager() {
        lifecycleScope.launch {
            while (true) {
                whenResumed {
                    delay(3000)
                    viewModel.getCurrentPosition()?.let {
                        viewModel.setCurrentPosition((it.plus(1)) % 5)
                    }
                }
            }
        }
    }

    private fun setImageResource() {
        Glide.with(this)
            .load(R.drawable.band_cover)
            .into(binding.makeBandCoverImageVIew)

        Glide.with(this)
            .load(R.drawable.solo_cover_bg)
            .into(binding.makeSoloCoverImageView)

        Glide.with(this)
            .load(R.drawable.record_studio)
            .into(binding.createRecordImageView)
    }


    companion object {
        const val COVER_MODE = "COVER_MODE"
        const val SOLO_COVER = "SOLO_COVER"
        const val BAND_COVER = "BAND_COVER"
        const val SONG_ENTITY = "SONG_ENTITY"
    }
}