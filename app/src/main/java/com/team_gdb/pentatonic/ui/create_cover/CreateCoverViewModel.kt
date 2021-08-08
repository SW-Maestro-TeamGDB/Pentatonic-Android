package com.team_gdb.pentatonic.ui.create_cover

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.team_gdb.pentatonic.base.BaseViewModel
import com.team_gdb.pentatonic.data.model.SessionSettingEntity
import com.team_gdb.pentatonic.data.model.SongEntity
import com.team_gdb.pentatonic.util.Event


class CreateCoverViewModel : BaseViewModel() {
    val coverName: MutableLiveData<String> = MutableLiveData()
    val coverIntroduction: MutableLiveData<String> = MutableLiveData()
    val coverSong: MutableLiveData<SongEntity> = MutableLiveData()

    val coverBasicInfoValidation: MutableLiveData<Event<Boolean>> =
        MutableLiveData<Event<Boolean>>()

    var coverSessionConfigList: MutableLiveData<List<SessionSettingEntity>> =
        MutableLiveData<List<SessionSettingEntity>>()

    /**
     * 커버 제목이 없거나, 커버 곡을 선택하지 않은 경우 Event(false) 지정
     */
    fun checkBasicInfoValidation() {
        when {
            this.coverName.value.isNullOrBlank() || this.coverSong.value == null -> {
                coverBasicInfoValidation.value = Event(false)  // Validation False
            }
            else -> {
                coverBasicInfoValidation.value = Event(true)  // Validation True
            }
        }
    }

}