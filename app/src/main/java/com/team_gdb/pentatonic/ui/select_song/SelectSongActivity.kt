package com.team_gdb.pentatonic.ui.select_song

import android.content.Intent
import androidx.recyclerview.widget.LinearLayoutManager
import com.team_gdb.pentatonic.R
import com.team_gdb.pentatonic.adapter.song_list.SongVerticalListAdapter
import com.team_gdb.pentatonic.base.BaseActivity
import com.team_gdb.pentatonic.data.model.SongEntity
import com.team_gdb.pentatonic.databinding.ActivitySelectSongBinding
import com.team_gdb.pentatonic.ui.create_cover.basic_info.SelectSongResultContract.Companion.SELECT_SONG
import com.team_gdb.pentatonic.util.setQueryDebounce

import org.koin.androidx.viewmodel.ext.android.viewModel

class SelectSongActivity : BaseActivity<ActivitySelectSongBinding, SelectSongViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.activity_select_song
    override val viewModel: SelectSongViewModel by viewModel()

    private lateinit var songListAdapter: SongVerticalListAdapter

    override fun initStartView() {
        binding.viewModel = this.viewModel
        binding.lifecycleOwner = this

        viewModel.getSongList("")

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

        viewModel.songList.observe(this) {
            songListAdapter.setItem(it)
        }
    }

    override fun initAfterBinding() {
        binding.titleBar.backButton.setOnClickListener {
            finish()
        }

        binding.selectFreeCoverButton.setOnClickListener {
            val bottomSheetDialog = RegisterFreeSongBottomSheetDialog()
            bottomSheetDialog.show(supportFragmentManager, bottomSheetDialog.tag)
        }
        binding.textClearButton.setOnClickListener {
            binding.searchView.text.clear()
        }

        addDisposable(
            binding.searchView.setQueryDebounce(
                {
                    viewModel.getSongList(it)
                },
                binding.textClearButton
            )
        )
    }
}