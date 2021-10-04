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
        setOnSeekBarChangeListener()
    }

    private fun setOnSeekBarChangeListener() {
        binding.reverbProgressBar.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                viewModel.setReverbEffectLevel(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                /* no-op */
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                /* no-op */
            }
        })

        binding.gainProgressBar.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                viewModel.setGainEffectLevel(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                /* no-op */
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                /* no-op */
            }
        })
    }

}