package com.team_gdb.pentatonic.ui.my_page

import android.content.Intent
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.materialdialogs.MaterialDialog
import com.bumptech.glide.Glide
import com.team_gdb.pentatonic.R
import com.team_gdb.pentatonic.adapter.cover_list.CoverHistoryListAdapter
import com.team_gdb.pentatonic.adapter.cover_list.TrendingCoverListAdapter
import com.team_gdb.pentatonic.adapter.position.PositionRankingListAdapter
import com.team_gdb.pentatonic.base.BaseApplication
import com.team_gdb.pentatonic.base.BaseFragment
import com.team_gdb.pentatonic.databinding.FragmentMyPageBinding
import com.team_gdb.pentatonic.ui.cover_view.band_cover.BandCoverActivity
import com.team_gdb.pentatonic.ui.cover_view.solo_cover.SoloCoverActivity
import com.team_gdb.pentatonic.ui.login.LoginActivity
import com.team_gdb.pentatonic.ui.lounge.LoungeFragment.Companion.COVER_ENTITY
import com.team_gdb.pentatonic.ui.lounge.LoungeFragment.Companion.COVER_ID
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class MyPageFragment : BaseFragment<FragmentMyPageBinding, MyPageViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.fragment_my_page
    override val viewModel: MyPageViewModel by sharedViewModel()

    private lateinit var coverHistoryListAdapter: CoverHistoryListAdapter
    private lateinit var positionRankingAdapter: PositionRankingListAdapter

    override fun initStartView() {
        binding.viewModel = this.viewModel
        binding.lifecycleOwner = this

        // SharedPreferences 에 저장된 사용자의 ID 를 기반으로 상세 정보 쿼리
        BaseApplication.prefs.userId?.let { viewModel.getUserInfo(it) }

        coverHistoryListAdapter = CoverHistoryListAdapter { coverId, isSoloCover ->
            val intent = if (isSoloCover) {
                Intent(requireContext(), SoloCoverActivity::class.java)
            } else {
                Intent(requireContext(), BandCoverActivity::class.java)
            }
            intent.putExtra(COVER_ID, coverId)
            startActivity(intent)
        }

        binding.coverHistoryList.apply {
            this.layoutManager = LinearLayoutManager(context).apply {
                this.orientation = LinearLayoutManager.HORIZONTAL
            }
            this.adapter = coverHistoryListAdapter
            this.setHasFixedSize(true)
        }

        positionRankingAdapter = PositionRankingListAdapter()

        binding.positionRankingList.apply {
            this.layoutManager = LinearLayoutManager(context).apply {
                this.orientation = LinearLayoutManager.HORIZONTAL
            }
            this.adapter = positionRankingAdapter
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

        viewModel.isPrimeUser.observe(this) {
            if (it) {
                binding.primeBadgeView.visibility = View.VISIBLE
                binding.primeUserTextView.visibility = View.VISIBLE
            }

        }
        viewModel.coverHistoryList.observe(this) {
            coverHistoryListAdapter.setItem(it)
        }

        viewModel.positionRankingList.observe(this) {
            it?.let {
                positionRankingAdapter.setItem(it)
            }
        }
    }

    override fun initAfterBinding() {

        // 프로필 수정 버튼
        binding.editProfileButton.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_my_page_to_navigation_edit_profile)
        }

        binding.libraryButton.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_my_page_to_navigation_library)
        }

        binding.coverHistoryDetailButton.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_my_page_to_navigation_cover_history)
        }

        binding.logoutButton.setOnClickListener {
            // 저장했던 로그인 정보 소멸 후 초기 화면으로 이동
            showLogoutDialog()
        }
    }

    private fun showLogoutDialog() {
        MaterialDialog(requireContext()).show {
            message(R.string.logout_title)
            positiveButton(R.string.yes_text) {
                BaseApplication.prefs.token = ""
                BaseApplication.prefs.userId = ""

                activity?.finish()
                val intent = Intent(requireContext(), LoginActivity::class.java)
                startActivity(intent)
            }
            negativeButton(R.string.no_text) {
                /* no-op */
            }
        }
    }
}