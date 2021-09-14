package com.team_gdb.pentatonic.ui.cover_play

import androidx.lifecycle.MutableLiveData
import com.team_gdb.pentatonic.base.BaseViewModel
import com.team_gdb.pentatonic.data.model.CoverEntity
import com.team_gdb.pentatonic.data.model.SongEntity

class CoverPlayingViewModel: BaseViewModel() {
    val coverEntity: MutableLiveData<CoverEntity> = MutableLiveData()

}