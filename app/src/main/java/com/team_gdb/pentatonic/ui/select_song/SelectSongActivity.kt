package com.team_gdb.pentatonic.ui.select_song

import android.content.Intent
import androidx.recyclerview.widget.LinearLayoutManager
import com.team_gdb.pentatonic.R
import com.team_gdb.pentatonic.TestData
import com.team_gdb.pentatonic.adapter.song_list.SongVerticalListAdapter
import com.team_gdb.pentatonic.base.BaseActivity
import com.team_gdb.pentatonic.data.model.SongEntity
import com.team_gdb.pentatonic.databinding.ActivitySelectSongBinding
import com.team_gdb.pentatonic.ui.create_cover.basic_info.SelectSongResultContract.Companion.SELECT_SONG

import org.koin.androidx.viewmodel.ext.android.viewModel

class SelectSongActivity : BaseActivity<ActivitySelectSongBinding, SelectSongViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.activity_select_song
    override val viewModel: SelectSongViewModel by viewModel()

    private lateinit var songListAdapter: SongVerticalListAdapter

    override fun initStartView() {
        binding.viewModel = this.viewModel

        binding.titleBar.titleTextView.text = "곡 선택"

        songListAdapter = SongVerticalListAdapter {
            val bottomSheetDialog = SongConfirmBottomSheetDialog(it)
            bottomSheetDialog.show(supportFragmentManager, bottomSheetDialog.tag)
        }

        binding.songList.apply {
            this.layoutManager = LinearLayoutManager(context)
            adapter = songListAdapter
            setHasFixedSize(true)
        }
    }

    override fun initDataBinding() {
        viewModel.selectedSong.observe(this) {
            if (it is SongEntity) {
                val intent = Intent()
                intent.putExtra(SELECT_SONG, it)
                setResult(RESULT_OK, intent)
                finish()
            }
        }
    }

    override fun initAfterBinding() {
        binding.titleBar.backButton.setOnClickListener {
            finish()
        }

        songListAdapter.setItem(TestData.TEST_SONG_LIST)
    }
}