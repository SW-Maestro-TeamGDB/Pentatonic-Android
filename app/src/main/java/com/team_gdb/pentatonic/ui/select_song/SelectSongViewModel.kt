package com.team_gdb.pentatonic.ui.select_song

import androidx.lifecycle.MutableLiveData
import com.team_gdb.pentatonic.base.BaseViewModel
import com.team_gdb.pentatonic.data.model.SongEntity
import com.team_gdb.pentatonic.repository.select_song.SelectSongRepository

class SelectSongViewModel(val repository: SelectSongRepository) : BaseViewModel() {
    val selectedSong: MutableLiveData<SongEntity> = MutableLiveData()
}