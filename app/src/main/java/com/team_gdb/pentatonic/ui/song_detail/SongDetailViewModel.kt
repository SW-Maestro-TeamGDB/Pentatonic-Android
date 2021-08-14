package com.team_gdb.pentatonic.ui.song_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.team_gdb.pentatonic.base.BaseViewModel

class SongDetailViewModel : BaseViewModel() {
    val songTitle: MutableLiveData<String> = MutableLiveData()
    val artistName: MutableLiveData<String> = MutableLiveData()
    val albumTitle: MutableLiveData<String> = MutableLiveData()
    val albumReleaseDate: MutableLiveData<String> = MutableLiveData()
    val songGenre: MutableLiveData<String> = MutableLiveData()
    val songWriter: MutableLiveData<String> = MutableLiveData()
    val lyricist: MutableLiveData<String> = MutableLiveData()

}