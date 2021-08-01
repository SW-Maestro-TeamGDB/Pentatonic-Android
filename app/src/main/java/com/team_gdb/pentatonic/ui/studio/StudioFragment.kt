package com.team_gdb.pentatonic.ui.studio

import android.content.Intent
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.team_gdb.pentatonic.R
import com.team_gdb.pentatonic.adapter.CoverListAdapter
import com.team_gdb.pentatonic.base.BaseFragment
import com.team_gdb.pentatonic.databinding.FragmentStudioBinding
import com.team_gdb.pentatonic.TestData
import com.team_gdb.pentatonic.adapter.SongListAdapter
import com.team_gdb.pentatonic.ui.record.RecordActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class StudioFragment : BaseFragment<FragmentStudioBinding, StudioViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.fragment_studio
    override val viewModel: StudioViewModel by viewModel()

    private lateinit var recommendCoverListAdapter: CoverListAdapter  // 추천 커버 리스트
    private lateinit var recommendSongListAdapter: SongListAdapter  // 추천  리스트


    override fun initStartView() {
    }

    override fun initDataBinding() {
    }

    override fun initAfterBinding() {
        binding.makeSoloCoverButton.setOnClickListener {
            startActivity(Intent(activity, RecordActivity::class.java))
        }

        // 추천 커버 리사이클러뷰 어댑터 생성
        recommendCoverListAdapter = CoverListAdapter {
            findNavController().navigate(StudioFragmentDirections.actionNavigationStudioToNavigationBandCover(it))
        }

        binding.recommendCoverList.apply {
            this.adapter = recommendCoverListAdapter
            this.layoutManager = LinearLayoutManager(context)
            this.setHasFixedSize(true)
        }

        recommendCoverListAdapter.setItem(TestData.TEST_SOLO_COVER_LIST)


        // 추천 곡 리사이클러뷰 어댑터 생성
        recommendSongListAdapter = SongListAdapter {

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