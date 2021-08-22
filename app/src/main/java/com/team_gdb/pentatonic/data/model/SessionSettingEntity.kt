package com.team_gdb.pentatonic.data.model

import com.team_gdb.pentatonic.data.session.SessionSetting
import java.io.Serializable

/**
 * 밴드 커버를 생성할 때, 구성하는 세션 각각의 사용자 설정 정보
 *
 * @property sessionSetting   어떤 세션 (보컬, 악기 등) 인지에 대한 정보
 * @property count            해당 세션의 최대 참가 가능 인원 설정 정보
 */
data class SessionSettingEntity(
    val sessionSetting: SessionSetting,
    var count: Int
) : Serializable
