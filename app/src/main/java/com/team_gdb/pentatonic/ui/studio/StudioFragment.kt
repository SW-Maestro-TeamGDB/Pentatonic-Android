package com.team_gdb.pentatonic.ui.studio

import com.newidea.mcpestore.libs.base.BaseFragment
import com.team_gdb.pentatonic.R
import com.team_gdb.pentatonic.databinding.FragmentStudioBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class StudioFragment : BaseFragment<FragmentStudioBinding, StudioViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.fragment_studio
    override val viewModel: StudioViewModel by viewModel()

    override fun initStartView() {
    }

    override fun initDataBinding() {
    }

    override fun initAfterBinding() {
    }
}