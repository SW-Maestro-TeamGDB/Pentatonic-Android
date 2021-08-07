package com.team_gdb.pentatonic.ui.select_song

import androidx.lifecycle.MutableLiveData
import com.team_gdb.pentatonic.base.BaseViewModel
import com.team_gdb.pentatonic.data.model.SongEntity

class SelectSongViewModel : BaseViewModel() {
    val selectedSong: MutableLiveData<SongEntity> = MutableLiveData()
}