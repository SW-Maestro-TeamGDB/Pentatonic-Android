package com.team_gdb.pentatonic.ui.solo_cover

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.team_gdb.pentatonic.R

class SoloCoverFragment : Fragment() {

    companion object {
        fun newInstance() = SoloCoverFragment()
    }

    private lateinit var viewModel: SoloCoverViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_solo_cover, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SoloCoverViewModel::class.java)
        // TODO: Use the ViewModel
    }

}