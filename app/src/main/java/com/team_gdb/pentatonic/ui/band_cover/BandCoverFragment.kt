package com.team_gdb.pentatonic.ui.band_cover

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.team_gdb.pentatonic.R

class BandCoverFragment : Fragment() {

    companion object {
        fun newInstance() = BandCoverFragment()
    }

    private lateinit var viewModel: BandCoverViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_band_cover, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(BandCoverViewModel::class.java)
        // TODO: Use the ViewModel
    }

}