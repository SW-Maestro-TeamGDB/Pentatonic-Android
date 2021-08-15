package com.team_gdb.pentatonic.data.model

import java.io.Serializable

data class SessionData(
    val sessionName: String,
    val sessionParticipantList: List<UserEntity>
): Serializable
