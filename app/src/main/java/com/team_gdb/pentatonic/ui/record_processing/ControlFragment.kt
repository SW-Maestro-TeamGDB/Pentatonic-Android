package com.team_gdb.pentatonic.ui.record_processing

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.team_gdb.pentatonic.R
import com.team_gdb.pentatonic.base.BaseFragment
import com.team_gdb.pentatonic.databinding.FragmentControlBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class ControlFragment : BaseFragment<FragmentControlBinding, RecordProcessingViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.fragment_control
    override val viewModel: RecordProcessingViewModel by sharedViewModel()

    override fun initStartView() {
    }

    override fun initDataBinding() {
    }

    override fun initAfterBinding() {
    }


}