package com.team_gdb.pentatonic.ui.select_song

import android.content.Intent
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.team_gdb.pentatonic.R
import com.team_gdb.pentatonic.adapter.song_list.SongVerticalListAdapter
import com.team_gdb.pentatonic.base.BaseActivity
import com.team_gdb.pentatonic.data.genre.GenreList
import com.team_gdb.pentatonic.data.level.LevelList
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

        viewModel.getSongList()

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

        viewModel.genre.observe(this) {
            // 장르 새로 선택할 때마다 다시 쿼리
            viewModel.getSongList()
        }

        viewModel.level.observe(this) {
            // 레벨 새로 선택할 때마다 다시 쿼리
            viewModel.getSongList()
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
                    viewModel.getSongList()
                },
                binding.textClearButton
            )
        )

        val genreItems = resources.getStringArray(R.array.genre_array)
        binding.genreSpinner.adapter =
            ArrayAdapter(this, R.layout.item_genre_spinner, genreItems)
        binding.genreSpinner.onItemSelectedListener = genreSpinnerItemSelectedListener

        val levelItems = resources.getStringArray(R.array.level_array)
        binding.levelSpinner.adapter =
            ArrayAdapter(this, R.layout.item_level_spinner, levelItems)
        binding.levelSpinner.onItemSelectedListener = levelSpinnerItemSelectedListener

    }

    private val genreSpinnerItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(
            parent: AdapterView<*>?,
            view: View?,
            position: Int,
            id: Long
        ) {
            if (position == 0) {
                (parent?.getChildAt(0) as TextView).text = "장르"
                viewModel.genre.value = null
            } else {
                viewModel.genre.value = GenreList.genreList[position]
            }
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {}
    }

    private val levelSpinnerItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(
            parent: AdapterView<*>?,
            view: View?,
            position: Int,
            id: Long
        ) {
            (parent?.getChildAt(0) as TextView).run {
                if (position == 0) {
                    text = "난이도"
                    textSize = 14F
                    viewModel.level.value = null
                } else {
                    viewModel.level.value = LevelList.levelList[position]
                }
                setTextColor(ContextCompat.getColor(context, R.color.white))
            }
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {}
    }
}