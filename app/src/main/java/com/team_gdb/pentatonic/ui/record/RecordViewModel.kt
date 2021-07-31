package com.team_gdb.pentatonic.ui.record

import androidx.lifecycle.MutableLiveData
import com.newidea.mcpestore.libs.base.BaseViewModel

class RecordViewModel : BaseViewModel() {
    val buttonState: MutableLiveData<ButtonState> = MutableLiveData<ButtonState>(ButtonState.BEFORE_RECORDING)

}