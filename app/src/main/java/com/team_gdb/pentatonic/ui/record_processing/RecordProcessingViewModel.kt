package com.team_gdb.pentatonic.ui.record_processing

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.team_gdb.pentatonic.base.BaseViewModel

class RecordProcessingViewModel : BaseViewModel() {
    val volumeLevel: MutableLiveData<Int> = MutableLiveData(50)
    val syncLevel: MutableLiveData<Int> = MutableLiveData(50)
    val gainEffectLevel: MutableLiveData<Int> = MutableLiveData(50)
    val reverbEffectLevel: MutableLiveData<Int> = MutableLiveData(50)

    fun controlVolumeLevel(amount: Int) {
        volumeLevel.value = volumeLevel.value?.plus(amount)
    }

    fun controlSyncLevel(amount: Int) {
        syncLevel.value = syncLevel.value?.plus(amount)
    }

    fun controlGainEffectLevel(amount: Int) {
        gainEffectLevel.value = gainEffectLevel.value?.plus(amount)
    }

    fun controlReverbEffectLevel(amount: Int){
        reverbEffectLevel.value = reverbEffectLevel.value?.plus(amount)
    }
}