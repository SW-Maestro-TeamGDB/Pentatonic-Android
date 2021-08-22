package com.team_gdb.pentatonic.data.session

/**
 * 사용자가 커버 구성할 때 보여주기 위한 모든 세션 리스트
 */
object SessionData {
    val sessionData: List<SessionSetting> = listOf(
        SessionSetting.VOCAL,
        SessionSetting.ACOUSTIC_GUITAR,
        SessionSetting.ELECTRIC_GUITAR,
        SessionSetting.BASS,
        SessionSetting.DRUM,
        SessionSetting.KEYBOARD,
        SessionSetting.VIOLIN,
        SessionSetting.CELLO,
        SessionSetting.GAYAGEUM,
        SessionSetting.HAEGUEM,
        SessionSetting.LYRE
    )
}