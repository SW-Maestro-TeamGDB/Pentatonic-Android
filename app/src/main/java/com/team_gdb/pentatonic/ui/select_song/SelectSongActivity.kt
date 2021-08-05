package com.team_gdb.pentatonic.ui.select_song

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.team_gdb.pentatonic.R
import com.team_gdb.pentatonic.base.BaseActivity
import com.team_gdb.pentatonic.databinding.ActivitySelectSongBinding

import org.koin.androidx.viewmodel.ext.android.viewModel

class SelectSongActivity : BaseActivity<ActivitySelectSongBinding, SelectSongViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.activity_select_song
    override val viewModel: SelectSongViewModel by viewModel()

    override fun initStartView() {
    }

    override fun initDataBinding() {
    }

    override fun initAfterBinding() {
        binding.titleBar.titleTextView.text = "곡 선택"
    }
}