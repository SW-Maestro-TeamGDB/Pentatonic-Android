package com.team_gdb.pentatonic.ui.record

import androidx.lifecycle.MutableLiveData
import com.team_gdb.pentatonic.base.BaseViewModel
import com.team_gdb.pentatonic.custom_view.ButtonState

class RecordViewModel : BaseViewModel() {
    val buttonState: MutableLiveData<ButtonState> = MutableLiveData<ButtonState>(ButtonState.BEFORE_RECORDING)

}