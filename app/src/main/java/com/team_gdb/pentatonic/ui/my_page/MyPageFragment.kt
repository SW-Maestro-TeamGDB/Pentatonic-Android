package com.team_gdb.pentatonic.ui.my_page

import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.team_gdb.pentatonic.R
import com.team_gdb.pentatonic.TestData
import com.team_gdb.pentatonic.adapter.cover_list.CoverHorizontalListAdapter
import com.team_gdb.pentatonic.base.BaseFragment
import com.team_gdb.pentatonic.databinding.FragmentMyPageBinding
import com.team_gdb.pentatonic.ui.lounge.LoungeFragmentDirections
import org.koin.androidx.viewmodel.ext.android.viewModel

class MyPageFragment : BaseFragment<FragmentMyPageBinding, MyPageViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.fragment_my_page
    override val viewModel: MyPageViewModel by viewModel()

    private lateinit var coverHistoryListAdapter: CoverHorizontalListAdapter
    private lateinit var likedCoverListAdapter: CoverHorizontalListAdapter

    override fun initStartView() {
        binding.viewModel = this.viewModel
    }

    override fun initDataBinding() {
    }

    override fun initAfterBinding() {
        coverHistoryListAdapter = CoverHorizontalListAdapter {
            findNavController().navigate(LoungeFragmentDirections.actionNavigationLoungeToNavigationBandCover(it))
        }

        binding.coverHistoryList.apply {
            this.layoutManager = LinearLayoutManager(context).apply {
                this.orientation = LinearLayoutManager.HORIZONTAL
            }
            this.adapter = coverHistoryListAdapter
            this.setHasFixedSize(true)
        }

        coverHistoryListAdapter.setItem(TestData.TEST_BAND_COVER_LIST)

        likedCoverListAdapter = CoverHorizontalListAdapter {
            findNavController().navigate(LoungeFragmentDirections.actionNavigationLoungeToNavigationBandCover(it))
        }

        binding.likedCoverList.apply {
            this.layoutManager = LinearLayoutManager(context).apply {
                this.orientation = LinearLayoutManager.HORIZONTAL
            }
            this.adapter = likedCoverListAdapter
            this.setHasFixedSize(true)
        }

        likedCoverListAdapter.setItem(TestData.TEST_BAND_COVER_LIST)

    }
}