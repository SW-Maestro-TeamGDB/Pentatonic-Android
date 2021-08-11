package com.team_gdb.pentatonic.ui.library

import com.team_gdb.pentatonic.R
import com.team_gdb.pentatonic.base.BaseFragment
import com.team_gdb.pentatonic.databinding.FragmentLibraryBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class LibraryFragment : BaseFragment<FragmentLibraryBinding, LibraryViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.fragment_library
    override val viewModel: LibraryViewModel by viewModel()

    override fun initStartView() {
    }

    override fun initDataBinding() {
    }

    override fun initAfterBinding() {
        binding.titleBar.titleTextView.text = "라이브러리"
    }
}