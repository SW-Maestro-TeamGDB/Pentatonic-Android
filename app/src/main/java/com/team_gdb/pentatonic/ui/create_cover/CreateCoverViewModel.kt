package com.team_gdb.pentatonic.ui.create_cover

import androidx.lifecycle.MutableLiveData
import com.team_gdb.pentatonic.base.BaseViewModel
import com.team_gdb.pentatonic.data.model.SongEntity
import com.team_gdb.pentatonic.util.Event


class CreateCoverViewModel : BaseViewModel() {
    val coverName: MutableLiveData<String> = MutableLiveData()
    val coverIntroduction: MutableLiveData<String> = MutableLiveData()
    val coverSong: MutableLiveData<SongEntity> = MutableLiveData()

    val coverBasicInfoValidation: MutableLiveData<Event<Boolean>> = MutableLiveData<Event<Boolean>>()

    fun checkBasicInfoValidation() {
        when {
            this.coverName.value.isNullOrBlank() || this.coverIntroduction.value.isNullOrBlank() || this.coverSong.value == null -> {
                coverBasicInfoValidation.value = Event(false)  // Validation False
            }
            else -> {
                coverBasicInfoValidation.value = Event(true)  // Validation True
            }
        }
    }

}