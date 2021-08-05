package com.team_gdb.pentatonic.ui.lounge

import android.graphics.Color
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.appbar.AppBarLayout
import com.team_gdb.pentatonic.R
import com.team_gdb.pentatonic.TestData
import com.team_gdb.pentatonic.adapter.CoverHorizontalListAdapter
import com.team_gdb.pentatonic.base.BaseFragment
import com.team_gdb.pentatonic.databinding.FragmentLoungeBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoungeFragment : BaseFragment<FragmentLoungeBinding, LoungeViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.fragment_lounge
    override val viewModel: LoungeViewModel by viewModel()

    private lateinit var bandCoverListAdapter: CoverHorizontalListAdapter  // 밴드 커버 리스트
    private lateinit var soloCoverListAdapter: CoverHorizontalListAdapter  // 솔로 커버 리스트

    override fun initStartView() {
        binding.viewModel = viewModel
    }

    override fun initDataBinding() {

    }

    override fun initAfterBinding() {
        binding.appBarLayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            if (Math.abs(verticalOffset) == appBarLayout.getTotalScrollRange()) {
                binding.titleTextView.setTextColor(ContextCompat.getColor(requireContext(), R.color.main_regular))
            } else{
                binding.titleTextView.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            }
        })

        // 밴드 커버 리사이클러뷰 어댑터 생성
        bandCoverListAdapter = CoverHorizontalListAdapter {
            findNavController().navigate(LoungeFragmentDirections.actionNavigationLoungeToNavigationBandCover(it))
        }

        binding.bandCoverList.apply {
            this.layoutManager = LinearLayoutManager(context).apply {
                this.orientation = LinearLayoutManager.HORIZONTAL
            }
            this.adapter = bandCoverListAdapter
            this.setHasFixedSize(true)
        }

        bandCoverListAdapter.setItem(TestData.TEST_BAND_COVER_LIST)

        // 솔로 커버 리사이클러뷰 어댑터 생성
        soloCoverListAdapter = CoverHorizontalListAdapter {
            findNavController().navigate(LoungeFragmentDirections.actionNavigationLoungeToNavigationSoloCover(it))
        }

        binding.soloCoverList.apply {
            this.layoutManager = LinearLayoutManager(context).apply {
                this.orientation = LinearLayoutManager.HORIZONTAL
            }
            this.adapter = soloCoverListAdapter
            this.setHasFixedSize(true)
        }

        soloCoverListAdapter.setItem(TestData.TEST_SOLO_COVER_LIST)

        // 위클리 챌린지 페이지로 이동
        binding.weeklyChallengeSongButton.setOnClickListener {
            findNavController().navigate(
                R.id.action_navigation_lounge_to_navigation_weekly_challenge
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

    }
}