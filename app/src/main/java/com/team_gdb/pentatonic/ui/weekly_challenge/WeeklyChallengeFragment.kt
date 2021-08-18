package com.team_gdb.pentatonic.ui.weekly_challenge

import android.content.Intent
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.team_gdb.pentatonic.R
import com.team_gdb.pentatonic.TestData
import com.team_gdb.pentatonic.adapter.cover_list.CoverVerticalListAdapter
import com.team_gdb.pentatonic.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.team_gdb.pentatonic.databinding.FragmentWeeklyChallengeBinding
import com.team_gdb.pentatonic.ui.band_cover.BandCoverActivity
import com.team_gdb.pentatonic.ui.lounge.LoungeFragment
import com.team_gdb.pentatonic.ui.lounge.LoungeFragmentDirections

class WeeklyChallengeFragment :
    BaseFragment<FragmentWeeklyChallengeBinding, WeeklyChallengeViewModel>() {
    override val viewModel: WeeklyChallengeViewModel by viewModel()
    override val layoutResourceId: Int
        get() = R.layout.fragment_weekly_challenge

    private lateinit var weeklyChallengeCoverListAdapter: CoverVerticalListAdapter

    override fun initStartView() {
        weeklyChallengeCoverListAdapter = CoverVerticalListAdapter {
            val intent = Intent(requireContext(), BandCoverActivity::class.java)
            intent.putExtra(LoungeFragment.COVER_ENTITY, it)
            startActivity(intent)
        }
        binding.weeklyChallengeCoverList.apply {
            this.layoutManager = LinearLayoutManager(context)
            this.adapter = weeklyChallengeCoverListAdapter
            this.setHasFixedSize(true)
        }
    }

    override fun initDataBinding() {
    }

    override fun initAfterBinding() {
        weeklyChallengeCoverListAdapter.setItem(TestData.TEST_WEEKLY_COVER_LIST)

        binding.backButton.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_weekly_challenge_to_navigation_lounge)
        }
    }
}