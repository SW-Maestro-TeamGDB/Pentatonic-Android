package com.team_gdb.pentatonic.ui.song_detail

import androidx.lifecycle.MutableLiveData
import com.team_gdb.pentatonic.base.BaseViewModel
import com.team_gdb.pentatonic.data.model.SongEntity

class SongDetailViewModel : BaseViewModel() {
    val songEntity: MutableLiveData<SongEntity> = MutableLiveData()
}