package com.team_gdb.pentatonic.ui.cover_play

import androidx.lifecycle.MutableLiveData
import com.team_gdb.pentatonic.base.BaseViewModel
import com.team_gdb.pentatonic.custom_view.ButtonState
import com.team_gdb.pentatonic.data.model.CoverEntity
import com.team_gdb.pentatonic.data.model.CoverPlayEntity
import com.team_gdb.pentatonic.data.model.SongEntity
import com.team_gdb.pentatonic.repository.cover_play.CoverPlayRepository

class CoverPlayingViewModel(val repository: CoverPlayRepository): BaseViewModel() {
    val coverEntity: MutableLiveData<CoverPlayEntity> = MutableLiveData()

}