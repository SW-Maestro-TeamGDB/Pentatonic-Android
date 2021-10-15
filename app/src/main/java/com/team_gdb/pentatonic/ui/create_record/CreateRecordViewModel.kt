package com.team_gdb.pentatonic.ui.create_record

import androidx.lifecycle.MutableLiveData
import com.team_gdb.pentatonic.base.BaseViewModel
import com.team_gdb.pentatonic.data.model.CreatedRecordEntity
import com.team_gdb.pentatonic.data.model.SongEntity
import com.team_gdb.pentatonic.data.session.Session
import com.team_gdb.pentatonic.util.Event


class CreateRecordViewModel : BaseViewModel() {
    // 녹음 기본 정보를 담음
    val recordName: MutableLiveData<String> = MutableLiveData()
    val recordOriginalSong: MutableLiveData<SongEntity> = MutableLiveData()

    val recordBasicInfoValidationEvent: MutableLiveData<Event<Boolean>> =
        MutableLiveData<Event<Boolean>>()

    // 커버를 구성할 세션 리스트 정보를 담음 (밴드 커버)
    var coverSessionConfig: MutableLiveData<Session> =
        MutableLiveData<Session>()

    // 커버 정보가 모두 완성되면 Event(true) 발행
    val completeCreateCoverEvent: MutableLiveData<Event<Boolean>> =
        MutableLiveData<Event<Boolean>>()

    // 사용자가 입력한 커버 정보를 기반으로 객체 생성하여 반환
    val createdCoverEntity: CreatedRecordEntity
        get() = CreatedRecordEntity(
            recordName = recordName.value!!,
            recordSong = recordOriginalSong.value!!,
            coverSession = coverSessionConfig.value!!,
            coverRecord = null
        )


    /**
     * 커버 제목이 없거나, 커버 곡을 선택하지 않은 경우 Event(false) 지정
     */
    fun checkBasicInfoValidation() {
        when {
            this.recordName.value.isNullOrBlank() || this.recordOriginalSong.value == null -> {
                recordBasicInfoValidationEvent.value = Event(false)  // Validation False
            }
            else -> {
                recordBasicInfoValidationEvent.value = Event(true)  // Validation True
            }
        }
    }
}