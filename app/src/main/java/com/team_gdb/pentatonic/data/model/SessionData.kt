package com.team_gdb.pentatonic.data.model

data class SessionData(
    val sessionName: String,
    val sessionParticipantList: List<UserEntity>
)
