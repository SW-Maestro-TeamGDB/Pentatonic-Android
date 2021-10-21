package com.team_gdb.pentatonic.ui.lounge

import android.content.Intent
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.appbar.AppBarLayout
import com.team_gdb.pentatonic.R
import com.team_gdb.pentatonic.adapter.cover_list.TrendingCoverListAdapter
import com.team_gdb.pentatonic.base.BaseApplication
import com.team_gdb.pentatonic.base.BaseFragment
import com.team_gdb.pentatonic.databinding.FragmentLoungeBinding
import com.team_gdb.pentatonic.ui.cover_view.band_cover.BandCoverActivity
import com.team_gdb.pentatonic.ui.cover_view.solo_cover.SoloCoverActivity
import com.team_gdb.pentatonic.ui.weekly_challenge.WeeklyChallengeFragmentDirections
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoungeFragment : BaseFragment<FragmentLoungeBinding, LoungeViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.fragment_lounge
    override val viewModel: LoungeViewModel by sharedViewModel()

    private lateinit var bandCoverListAdapter: TrendingCoverListAdapter  // 밴드 커버 리스트
    private lateinit var soloCoverListAdapter: TrendingCoverListAdapter  // 솔로 커버 리스트

    override fun onResume() {
        super.onResume()

        // 위클리 챌린지 곡 정보 조회 쿼리 호출
        viewModel.getWeeklyChallengeSongInfo()

        // 떠오르는 밴드 커버, 솔로 커버 가져오는 쿼리 호출
        viewModel.getTrendBands()
    }

    override fun initStartView() {
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        // 밴드 커버 리사이클러뷰 어댑터 생성
        bandCoverListAdapter = TrendingCoverListAdapter {
            val intent = Intent(requireContext(), BandCoverActivity::class.java)
            intent.putExtra(COVER_ID, it)
            startActivity(intent)
        }

        binding.bandCoverList.apply {
            this.layoutManager = LinearLayoutManager(context).apply {
                this.orientation = LinearLayoutManager.HORIZONTAL
            }
            this.adapter = bandCoverListAdapter
            this.setHasFixedSize(true)
        }

        // 솔로 커버 리사이클러뷰 어댑터 생성
        soloCoverListAdapter = TrendingCoverListAdapter {
            val intent = Intent(requireContext(), SoloCoverActivity::class.java)
            intent.putExtra(COVER_ID, it)
            startActivity(intent)
        }

        binding.soloCoverList.apply {
            this.layoutManager = LinearLayoutManager(context).apply {
                this.orientation = LinearLayoutManager.HORIZONTAL
            }
            this.adapter = soloCoverListAdapter
            this.setHasFixedSize(true)
        }
    }

    override fun initDataBinding() {
        viewModel.weeklyChallengeSongImage.observe(this) {
            applyWeeklyChallengeImage(it)
        }

        viewModel.trendBandsQuery.observe(this) {
            val coverList = it.partition { it.isSoloBand }
            bandCoverListAdapter.setItem(coverList.second)  // 밴드 커버
            soloCoverListAdapter.setItem(coverList.first)  // 솔로 커버
        }

        viewModel.userProfileImage.observe(this) {
            Glide.with(this)
                .load(it)
                .placeholder(R.drawable.profile_image_placeholder)
                .override(50, 50)
                .into(binding.userProfileImage)
            binding.userProfileImage.setOnClickListener {
                findNavController().navigate(
                    R.id.action_navigation_lounge_to_navigation_my_page
                )
            }
        }

    }

    override fun initAfterBinding() {
        // 유저 정보 (프로필 이미지 연동)
        BaseApplication.prefs.userId?.let { viewModel.getUserInfo(it) }

        binding.appBarLayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
//            if (Math.abs(verticalOffset) == appBarLayout.getTotalScrollRange()) {
//                binding.titleTextView.setTextColor(
//                    ContextCompat.getColor(
//                        requireContext(),
//                        R.color.main_regular
//                    )
//                )
//            } else {
//                binding.titleTextView.setTextColor(
//                    ContextCompat.getColor(
//                        requireContext(),
//                        R.color.white
//                    )
//                )
//            }
        })

//        bandCoverListAdapter.setItem(TestData.TEST_BAND_COVER_LIST)
//        soloCoverListAdapter.setItem(TestData.TEST_SOLO_COVER_LIST)

        // 위클리 챌린지 페이지로 이동
        binding.weeklyChallengeSongButton.setOnClickListener {
            findNavController().navigate(
                LoungeFragmentDirections.actionNavigationLoungeToNavigationWeeklyChallenge(
                    weeklyChallengeSongId = viewModel.getWeeklyChallengeSongId()
                )
            )
        }

        // 라이징 밴드 페이지로 이동
        binding.risingBandDetailButton.setOnClickListener {
            findNavController().navigate(
                R.id.action_navigation_lounge_to_navigation_rising_band
            )
        }

        // 라이징 솔로 페이지로 이동
        binding.risingSoloDetailButton.setOnClickListener {
            findNavController().navigate(
                R.id.action_navigation_lounge_to_navigation_rising_solo
            )
        }

        // 전체 커버 탐색 페이지로 이동
        binding.wholeCoverButton.setOnClickListener {
            findNavController().navigate(
                R.id.action_navigation_lounge_to_navigation_whole_cover
            )
        }
    }

    private fun applyWeeklyChallengeImage(imageUrl: String) {
        Glide.with(this)
            .load(imageUrl)
            .placeholder(R.drawable.placeholder_cover_bg)
            .into(binding.weeklyChallengeSongImage)
    }

    companion object {
        const val COVER_ENTITY = "COVER_ENTITY"
        const val COVER_ID = "COVER_ID"
    }
}