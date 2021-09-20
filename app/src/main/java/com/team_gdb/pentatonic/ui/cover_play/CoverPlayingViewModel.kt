package com.team_gdb.pentatonic.ui.cover_play

import androidx.lifecycle.MutableLiveData
import com.team_gdb.pentatonic.base.BaseViewModel
import com.team_gdb.pentatonic.custom_view.ButtonState
import com.team_gdb.pentatonic.data.model.CoverEntity
import com.team_gdb.pentatonic.data.model.CoverPlayEntity
import com.team_gdb.pentatonic.data.model.SongEntity

class CoverPlayingViewModel: BaseViewModel() {
    val coverEntity: MutableLiveData<CoverPlayEntity> = MutableLiveData()

    val buttonState: MutableLiveData<ButtonState> =
        MutableLiveData<ButtonState>(ButtonState.BEFORE_PLAYING)

    val playTime: MutableLiveData<String> = MutableLiveData("00:00")
    val remainTime: MutableLiveData<String> = MutableLiveData("00:00")
}