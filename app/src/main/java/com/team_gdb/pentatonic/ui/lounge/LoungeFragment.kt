package com.team_gdb.pentatonic.ui.lounge

import android.graphics.Color
import android.graphics.PorterDuff
import com.newidea.mcpestore.libs.base.BaseFragment
import com.team_gdb.pentatonic.R
import com.team_gdb.pentatonic.databinding.FragmentLoungeBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoungeFragment : BaseFragment<FragmentLoungeBinding, LoungeViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.fragment_lounge
    override val viewModel: LoungeViewModel by viewModel()

    override fun initStartView() {
    }

    override fun initDataBinding() {

    }

    override fun initAfterBinding() {

    }
}