package com.team_gdb.pentatonic.data.model

import java.io.Serializable

/**
 * 커버를 구성하는 세션 각각의 정보
 *
 * @property sessionName          해당 세션의 이름
 * @property sessionMaxSize       해당 세션 최대 참가 가능 인원
 * @property currentParticipant   해당 세션에 참가하고 있는 인원 수
 */
data class SessionData(
    val sessionName: String,
    val sessionMaxSize: Int,
    val currentParticipant: Int
): Serializable
