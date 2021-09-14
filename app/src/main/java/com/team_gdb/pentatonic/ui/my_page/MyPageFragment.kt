package com.team_gdb.pentatonic.ui.my_page

import android.content.Intent
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.team_gdb.pentatonic.R
import com.team_gdb.pentatonic.TestData
import com.team_gdb.pentatonic.adapter.cover_list.CoverHorizontalListAdapter
import com.team_gdb.pentatonic.base.BaseFragment
import com.team_gdb.pentatonic.data.model.CoverEntity
import com.team_gdb.pentatonic.databinding.FragmentMyPageBinding
import com.team_gdb.pentatonic.ui.band_cover.BandCoverActivity
import com.team_gdb.pentatonic.ui.lounge.LoungeFragment.Companion.COVER_ENTITY
import com.team_gdb.pentatonic.ui.lounge.LoungeFragmentDirections
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MyPageFragment : BaseFragment<FragmentMyPageBinding, MyPageViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.fragment_my_page
    override val viewModel: MyPageViewModel by sharedViewModel()

    private lateinit var coverHistoryListAdapter: CoverHorizontalListAdapter
    private lateinit var likedCoverListAdapter: CoverHorizontalListAdapter

    override fun initStartView() {
        binding.viewModel = this.viewModel
        binding.lifecycleOwner = this


        coverHistoryListAdapter = CoverHorizontalListAdapter {
            val intent = Intent(requireContext(), BandCoverActivity::class.java)
            intent.putExtra(COVER_ENTITY, it)
            startActivity(intent)
        }

        binding.coverHistoryList.apply {
            this.layoutManager = LinearLayoutManager(context).apply {
                this.orientation = LinearLayoutManager.HORIZONTAL
            }
            this.adapter = coverHistoryListAdapter
            this.setHasFixedSize(true)
        }


        likedCoverListAdapter = CoverHorizontalListAdapter {
            val intent = Intent(requireContext(), BandCoverActivity::class.java)
            intent.putExtra(COVER_ENTITY, it)
            startActivity(intent)
        }

        binding.likedCoverList.apply {
            this.layoutManager = LinearLayoutManager(context).apply {
                this.orientation = LinearLayoutManager.HORIZONTAL
            }
            this.adapter = likedCoverListAdapter
            this.setHasFixedSize(true)
        }
    }

    override fun initDataBinding() {
        viewModel.userProfileImage.observe(this) {
            Glide.with(this)
                .load(it)
                .placeholder(R.drawable.profile_image_placeholder)
                .override(100, 100)
                .into(binding.userProfileImage)
        }
    }

    override fun initAfterBinding() {
        coverHistoryListAdapter.setItem(TestData.TEST_BAND_COVER_LIST)

        likedCoverListAdapter.setItem(TestData.TEST_BAND_COVER_LIST)

        binding.goToLibraryButton.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_my_page_to_navigation_library)
        }

        viewModel.getUserInfo("h2is1234")
    }
}