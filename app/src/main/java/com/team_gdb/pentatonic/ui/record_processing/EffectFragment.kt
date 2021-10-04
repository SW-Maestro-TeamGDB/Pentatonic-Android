package com.team_gdb.pentatonic.ui.record_processing

import android.widget.SeekBar
import com.jakewharton.rxbinding4.widget.SeekBarProgressChangeEvent
import com.team_gdb.pentatonic.R
import com.team_gdb.pentatonic.base.BaseFragment
import com.team_gdb.pentatonic.databinding.FragmentEffectBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class EffectFragment : BaseFragment<FragmentEffectBinding, RecordProcessingViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.fragment_effect
    override val viewModel: RecordProcessingViewModel by sharedViewModel()

    override fun initStartView() {
        binding.viewModel = this.viewModel
        binding.lifecycleOwner = this
    }

    override fun initDataBinding() {

    }

    override fun initAfterBinding() {
    }
}