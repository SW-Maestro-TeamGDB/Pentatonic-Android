package com.team_gdb.pentatonic.data.session

/**
 * 사용자가 커버 구성할 때 보여주기 위한 모든 세션 리스트
 */
object SessionList {
    val sessionList: List<Session> = listOf(
        Session.VOCAL,
        Session.ACOUSTIC_GUITAR,
        Session.ELECTRIC_GUITAR,
        Session.BASS,
        Session.DRUM,
        Session.KEYBOARD,
        Session.VIOLIN,
        Session.CELLO,
        Session.GAYAGEUM,
        Session.HAEGUEM,
        Session.LYRE
    )
}