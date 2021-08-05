package com.team_gdb.pentatonic.ui.studio

import android.content.Intent
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.team_gdb.pentatonic.R
import com.team_gdb.pentatonic.adapter.CoverHorizontalListAdapter
import com.team_gdb.pentatonic.base.BaseFragment
import com.team_gdb.pentatonic.databinding.FragmentStudioBinding
import com.team_gdb.pentatonic.TestData
import com.team_gdb.pentatonic.adapter.SongHorizontalListAdapter
import com.team_gdb.pentatonic.ui.create_cover.CreateCoverActivity
import com.team_gdb.pentatonic.ui.record.RecordActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class StudioFragment : BaseFragment<FragmentStudioBinding, StudioViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.fragment_studio
    override val viewModel: StudioViewModel by viewModel()

    private lateinit var recommendCoverListAdapter: CoverHorizontalListAdapter  // 추천 커버 리스트
    private lateinit var recommendSongListAdapter: SongHorizontalListAdapter  // 추천  리스트


    override fun initStartView() {
    }

    override fun initDataBinding() {
    }

    override fun initAfterBinding() {
        // 솔로 커버 버튼 클릭했을 때
        binding.makeSoloCoverButton.setOnClickListener {
            startActivity(Intent(activity, RecordActivity::class.java))
        }

        // 밴드 커버 버튼 클릭했을 때
        binding.makeBandCoverButton.setOnClickListener {
            startActivity(Intent(activity, CreateCoverActivity::class.java))
        }

        // 추천 커버 리사이클러뷰 어댑터 생성
        recommendCoverListAdapter = CoverHorizontalListAdapter {
            findNavController().navigate(
                StudioFragmentDirections.actionNavigationStudioToNavigationBandCover(
                    it
                )
            )
        }

        binding.recommendCoverList.apply {
            this.layoutManager = LinearLayoutManager(context).apply {
                this.orientation = LinearLayoutManager.HORIZONTAL
            }
            this.adapter = recommendCoverListAdapter
            this.setHasFixedSize(true)
        }

        recommendCoverListAdapter.setItem(TestData.TEST_SOLO_COVER_LIST)


        // 추천 곡 리사이클러뷰 어댑터 생성
        recommendSongListAdapter = SongHorizontalListAdapter {

        }

        binding.recommendSongList.apply {
            this.layoutManager = LinearLayoutManager(context).apply {
                this.orientation = LinearLayoutManager.HORIZONTAL
            }
            this.adapter = recommendSongListAdapter
            this.setHasFixedSize(true)
        }
        recommendSongListAdapter.setItem(TestData.TEST_SONG_LIST)
    }
}