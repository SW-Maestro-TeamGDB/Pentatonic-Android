package com.team_gdb.pentatonic.ui.weekly_challenge

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.newidea.mcpestore.libs.base.BaseFragment
import com.team_gdb.pentatonic.R
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.team_gdb.pentatonic.databinding.FragmentWeeklyChallengeBinding

class WeeklyChallengeFragment : BaseFragment<FragmentWeeklyChallengeBinding, WeeklyChallengeViewModel>() {
    override val viewModel: WeeklyChallengeViewModel by viewModel()
    override val layoutResourceId: Int
        get() = R.layout.fragment_weekly_challenge

    override fun initStartView() {
//        TODO("Not yet implemented")
    }

    override fun initDataBinding() {
//        TODO("Not yet implemented")
    }

    override fun initAfterBinding() {
//        TODO("Not yet implemented")
    }
}