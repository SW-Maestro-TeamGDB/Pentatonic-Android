package com.team_gdb.pentatonic.ui.record_processing

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import com.team_gdb.pentatonic.R
import com.team_gdb.pentatonic.base.BaseFragment
import com.team_gdb.pentatonic.databinding.FragmentControlBinding
import com.team_gdb.pentatonic.util.setDebounce
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import timber.log.Timber

class ControlFragment : BaseFragment<FragmentControlBinding, RecordProcessingViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.fragment_control
    override val viewModel: RecordProcessingViewModel by sharedViewModel()

    override fun initStartView() {
        binding.viewModel = this.viewModel
        binding.lifecycleOwner = this
    }

    override fun initDataBinding() {
        viewModel.createdRecordEntity.observe(this) {
            if (it.recordSong.isFreeSong) {
                binding.syncControlTitleText.visibility = View.GONE
                binding.syncControlLayout.visibility = View.GONE
            }
        }
    }

    override fun initAfterBinding() {
        binding.syncProgressBar.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                seekBar?.progress = progress
                binding.syncProgressTextView.text = "${progress}ms"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                viewModel.syncLevel.value = seekBar?.progress
            }
        })

        binding.volumeProgressBar.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                seekBar?.progress = progress
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                viewModel.volumeLevel.value = seekBar?.progress
            }
        })
    }

}