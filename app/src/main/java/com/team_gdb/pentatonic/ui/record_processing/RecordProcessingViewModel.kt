package com.team_gdb.pentatonic.ui.record_processing

import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.team_gdb.pentatonic.base.BaseViewModel
import com.team_gdb.pentatonic.repository.record_processing.RecordProcessingRepository
import com.team_gdb.pentatonic.ui.record.ButtonState
import timber.log.Timber

class RecordProcessingViewModel(val repository: RecordProcessingRepository) : BaseViewModel() {
    val buttonState: MutableLiveData<ButtonState> = MutableLiveData<ButtonState>(ButtonState.BEFORE_PLAYING)

    val playTime: MutableLiveData<String> = MutableLiveData("00:00")
    val remainTime: MutableLiveData<String> = MutableLiveData("00:00")

    val volumeLevel: MutableLiveData<Int> = MutableLiveData(50)
    val syncLevel: MutableLiveData<Int> = MutableLiveData(50)
    val gainEffectLevel: MutableLiveData<Int> = MutableLiveData(50)
    val reverbEffectLevel: MutableLiveData<Int> = MutableLiveData(50)

    fun controlVolumeLevel(amount: Int) {
        volumeLevel.value = volumeLevel.value?.plus(amount)
        Timber.d("Volume Control 예아")
        Timber.d("VolumeLevel : ${volumeLevel.value}")
    }

    fun controlSyncLevel(amount: Int) {
        syncLevel.value = syncLevel.value?.plus(amount)
    }

    fun controlGainEffectLevel(amount: Int) {
        gainEffectLevel.value = gainEffectLevel.value?.plus(amount)
    }

    fun controlReverbEffectLevel(amount: Int) {
        reverbEffectLevel.value = reverbEffectLevel.value?.plus(amount)
    }
}