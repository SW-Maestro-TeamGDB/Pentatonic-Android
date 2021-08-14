package com.team_gdb.pentatonic.ui.song_detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.team_gdb.pentatonic.R
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.team_gdb.pentatonic.base.BaseActivity
import com.team_gdb.pentatonic.databinding.ActivitySongDetailBinding

class SongDetailActivity : BaseActivity<ActivitySongDetailBinding, SongDetailViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.activity_song_detail
    override val viewModel: SongDetailViewModel by viewModel()

    override fun initStartView() {
        binding.viewModel = this.viewModel
    }

    override fun initDataBinding() {
    }

    override fun initAfterBinding() {
    }
}