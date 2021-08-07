package com.team_gdb.pentatonic.ui.select_song

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.team_gdb.pentatonic.R
import com.team_gdb.pentatonic.TestData
import com.team_gdb.pentatonic.adapter.SongVerticalListAdapter
import com.team_gdb.pentatonic.base.BaseActivity
import com.team_gdb.pentatonic.data.model.SongEntity
import com.team_gdb.pentatonic.databinding.ActivitySelectSongBinding
import com.team_gdb.pentatonic.ui.create_cover.SelectSongResultContract.Companion.SELECT_SONG
import com.team_gdb.pentatonic.ui.record.RecordGuideBottomSheetDialog

import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class SelectSongActivity : BaseActivity<ActivitySelectSongBinding, SelectSongViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.activity_select_song
    override val viewModel: SelectSongViewModel by viewModel()

    private lateinit var songListAdapter: SongVerticalListAdapter

    override fun initStartView() {
    }

    override fun initDataBinding() {
        viewModel.selectedSong.observe(this) {
            if (it is SongEntity){
                val intent = Intent()
                intent.putExtra(SELECT_SONG, it)
                setResult(RESULT_OK, intent)
                finish()
            }
        }
    }

    override fun initAfterBinding() {
        binding.titleBar.titleTextView.text = "곡 선택"
        binding.titleBar.backButton.setOnClickListener {
            finish()
        }

        songListAdapter = SongVerticalListAdapter {
            val bottomSheetDialog = SongConfirmBottomSheetDialog(it)
            bottomSheetDialog.show(supportFragmentManager, bottomSheetDialog.tag)
        }

        binding.songList.apply {
            this.layoutManager = LinearLayoutManager(context)
            adapter = songListAdapter
            setHasFixedSize(true)
        }

        songListAdapter.setItem(TestData.TEST_SONG_LIST)
    }
}