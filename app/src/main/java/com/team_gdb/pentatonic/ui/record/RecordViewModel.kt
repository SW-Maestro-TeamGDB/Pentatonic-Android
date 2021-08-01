package com.team_gdb.pentatonic.ui.record

import androidx.lifecycle.MutableLiveData
import com.team_gdb.pentatonic.base.BaseViewModel

class RecordViewModel : BaseViewModel() {
    val buttonState: MutableLiveData<ButtonState> = MutableLiveData<ButtonState>(ButtonState.BEFORE_RECORDING)

}