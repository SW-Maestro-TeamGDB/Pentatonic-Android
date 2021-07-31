package com.team_gdb.pentatonic.ui.lounge

import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.newidea.mcpestore.libs.base.BaseFragment
import com.team_gdb.pentatonic.R
import com.team_gdb.pentatonic.adapter.CoverListAdapter
import com.team_gdb.pentatonic.databinding.FragmentLoungeBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoungeFragment : BaseFragment<FragmentLoungeBinding, LoungeViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.fragment_lounge
    override val viewModel: LoungeViewModel by viewModel()

    private lateinit var bandCoverListAdapter: CoverListAdapter  // 밴드 커버 리스트
    private lateinit var soloCoverListAdapter: CoverListAdapter  // 솔로 커버 리스트

    override fun initStartView() {
        binding.viewModel = viewModel
    }

    override fun initDataBinding() {

    }

    override fun initAfterBinding() {
        // 밴드 커버 리사이클러뷰 어댑터 생성
        bandCoverListAdapter = CoverListAdapter {
            findNavController().navigate(LoungeFragmentDirections.actionNavigationLoungeToNavigationBandCover(it))
        }

        binding.bandCoverList.apply {
            this.adapter = bandCoverListAdapter
            this.layoutManager = LinearLayoutManager(context)
            this.setHasFixedSize(true)
        }

        bandCoverListAdapter.setItem(TestData.testBandCoverList)

        // 솔로 커버 리사이클러뷰 어댑터 생성
        soloCoverListAdapter = CoverListAdapter {
            findNavController().navigate(LoungeFragmentDirections.actionNavigationLoungeToNavigationSoloCover(it))
        }

        binding.soloCoverList.apply {
            this.adapter = soloCoverListAdapter
            this.layoutManager = LinearLayoutManager(context)
            this.setHasFixedSize(true)
        }

        soloCoverListAdapter.setItem(TestData.testSoloCoverList)

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