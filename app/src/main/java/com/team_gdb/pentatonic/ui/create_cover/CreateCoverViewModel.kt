package com.team_gdb.pentatonic.ui.create_cover

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import com.team_gdb.pentatonic.base.BaseViewModel
import com.team_gdb.pentatonic.data.model.CreatedCoverEntity
import com.team_gdb.pentatonic.data.model.SessionSettingEntity
import com.team_gdb.pentatonic.data.model.SongEntity
import com.team_gdb.pentatonic.data.session.SessionSetting
import com.team_gdb.pentatonic.repository.create_cover.CreateCoverRepository
import com.team_gdb.pentatonic.util.Event


class CreateCoverViewModel(val repository: CreateCoverRepository) : BaseViewModel() {
    // 커버 기본 정보를 담음
    val coverName: MutableLiveData<String> = MutableLiveData()
    val coverIntroduction: MutableLiveData<String> = MutableLiveData()
    val coverSong: MutableLiveData<SongEntity> = MutableLiveData()
    val coverBackgroundImage: MutableLiveData<Uri> = MutableLiveData()

    val coverBasicInfoValidationEvent: MutableLiveData<Event<Boolean>> =
        MutableLiveData<Event<Boolean>>()

    // 커버를 구성할 세션 리스트 정보를 담음 (밴드 커버)
    var coverSessionConfigList: MutableLiveData<List<SessionSettingEntity>> =
        MutableLiveData<List<SessionSettingEntity>>()

    // 커버 정보가 모두 완성되면 Event(true) 발행
    val completeCreateCoverEvent: MutableLiveData<Event<Boolean>> =
        MutableLiveData<Event<Boolean>>()

    // 사용자가 입력한 커버 정보를 기반으로 객체 생성하여 반환
    val createdCoverEntity: CreatedCoverEntity
        get() {
            val entity = CreatedCoverEntity(
                coverName = coverName.value!!,
                coverIntroduction = coverIntroduction.value,
                coverSong = coverSong.value!!,
                backgroundImg = "",
                coverSessionConfig = coverSessionConfigList.value!!,
                coverRecord = null
            )
            return entity
        }

    /**
     * 커버 제목이 없거나, 커버 곡을 선택하지 않은 경우 Event(false) 지정
     */
    fun checkBasicInfoValidation() {
        when {
            this.coverName.value.isNullOrBlank() || this.coverSong.value == null -> {
                coverBasicInfoValidationEvent.value = Event(false)  // Validation False
            }
            else -> {
                coverBasicInfoValidationEvent.value = Event(true)  // Validation True
            }
        }
    }

    fun completeCreateCover() {
        completeCreateCoverEvent.value = Event(true)
    }

}