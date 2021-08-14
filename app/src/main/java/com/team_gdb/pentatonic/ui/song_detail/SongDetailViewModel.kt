package com.team_gdb.pentatonic.ui.song_detail

import androidx.lifecycle.MutableLiveData
import com.team_gdb.pentatonic.base.BaseViewModel
import com.team_gdb.pentatonic.data.model.SongEntity

class SongDetailViewModel : BaseViewModel() {
    val songEntity: MutableLiveData<SongEntity> = MutableLiveData()

    val songTitle: MutableLiveData<String> = MutableLiveData()
    val artistName: MutableLiveData<String> = MutableLiveData()
    val albumTitle: MutableLiveData<String> = MutableLiveData()
    val albumReleaseDate: MutableLiveData<String> = MutableLiveData()
    val songGenre: MutableLiveData<String> = MutableLiveData()
    val songWriter: MutableLiveData<String> = MutableLiveData()
    val lyricist: MutableLiveData<String> = MutableLiveData()

}