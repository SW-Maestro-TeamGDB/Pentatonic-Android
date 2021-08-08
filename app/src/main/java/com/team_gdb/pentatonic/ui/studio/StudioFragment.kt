package com.team_gdb.pentatonic.ui.studio

import android.content.Intent
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.team_gdb.pentatonic.R
import com.team_gdb.pentatonic.adapter.cover_list.CoverHorizontalListAdapter
import com.team_gdb.pentatonic.base.BaseFragment
import com.team_gdb.pentatonic.databinding.FragmentStudioBinding
import com.team_gdb.pentatonic.TestData
import com.team_gdb.pentatonic.adapter.song_list.SongHorizontalListAdapter
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

        // 추천 커버 리사이클러뷰 어댑터 생성
        recommendCoverListAdapter = CoverHorizontalListAdapter {
            findNavController().navigate(StudioFragmentDirections.actionNavigationStudioToNavigationBandCover(it))
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

    companion object {
        const val COVER_MODE = "COVER_MODE"
        const val SOLO_COVER = "SOLO_COVER"
        const val BAND_COVER ="BAND_COVER"
    }
}