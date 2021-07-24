package com.team_gdb.pentatonic.ui.lounge

import android.graphics.Color
import android.graphics.PorterDuff
import androidx.navigation.fragment.findNavController
import com.newidea.mcpestore.libs.base.BaseFragment
import com.team_gdb.pentatonic.R
import com.team_gdb.pentatonic.databinding.FragmentLoungeBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoungeFragment : BaseFragment<FragmentLoungeBinding, LoungeViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.fragment_lounge
    override val viewModel: LoungeViewModel by viewModel()

    override fun initStartView() {
        binding.viewModel = viewModel
    }

    override fun initDataBinding() {

    }

    override fun initAfterBinding() {
        // 위클리 챌린지 페이지로 이동
        binding.weeklyChallengeSongButton.setOnClickListener {
            findNavController().navigate(
                R.id.action_navigation_lounge_to_navigation_weekly_challenge
            )
        }
    }
}