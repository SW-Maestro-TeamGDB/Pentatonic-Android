package com.team_gdb.pentatonic.ui.solo_cover

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.team_gdb.pentatonic.R

class SoloCoverActivity : AppCompatActivity() {

    companion object {
        fun newInstance() = SoloCoverActivity()
    }

    private lateinit var viewModel: SoloCoverViewModel


}