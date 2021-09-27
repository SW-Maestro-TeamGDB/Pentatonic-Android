package com.team_gdb.pentatonic.ui.my_page

import android.content.Intent
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.team_gdb.pentatonic.R
import com.team_gdb.pentatonic.adapter.cover_list.CoverHistoryListAdapter
import com.team_gdb.pentatonic.adapter.cover_list.TrendingCoverListAdapter
import com.team_gdb.pentatonic.base.BaseApplication
import com.team_gdb.pentatonic.base.BaseFragment
import com.team_gdb.pentatonic.databinding.FragmentMyPageBinding
import com.team_gdb.pentatonic.ui.cover_view.band_cover.BandCoverActivity
import com.team_gdb.pentatonic.ui.lounge.LoungeFragment.Companion.COVER_ENTITY
import com.team_gdb.pentatonic.ui.lounge.LoungeFragment.Companion.COVER_ID
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class MyPageFragment : BaseFragment<FragmentMyPageBinding, MyPageViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.fragment_my_page
    override val viewModel: MyPageViewModel by sharedViewModel()

    private lateinit var coverHistoryListAdapter: CoverHistoryListAdapter
    private lateinit var likedCoverListAdapter: TrendingCoverListAdapter

    override fun initStartView() {
        binding.viewModel = this.viewModel
        binding.lifecycleOwner = this


        coverHistoryListAdapter = CoverHistoryListAdapter {
            val intent = Intent(requireContext(), BandCoverActivity::class.java)
            intent.putExtra(COVER_ID, it)
            startActivity(intent)
        }

        binding.coverHistoryList.apply {
            this.layoutManager = LinearLayoutManager(context).apply {
                this.orientation = LinearLayoutManager.HORIZONTAL
            }
            this.adapter = coverHistoryListAdapter
            this.setHasFixedSize(true)
        }


        likedCoverListAdapter = TrendingCoverListAdapter {
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

        viewModel.coverHistoryList.observe(this) {
            coverHistoryListAdapter.setItem(it)
        }
    }

    override fun initAfterBinding() {
//        likedCoverListAdapter.setItem(TestData.TEST_BAND_COVER_LIST)

        binding.goToLibraryButton.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_my_page_to_navigation_library)
        }

        // SharedPreferences 에 저장된 사용자의 ID 를 기반으로 상세 정보 쿼리
        BaseApplication.prefs.userId?.let { viewModel.getUserInfo(it) }
    }
}