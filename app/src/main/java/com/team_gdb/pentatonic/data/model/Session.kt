package com.team_gdb.pentatonic.data.model

data class Session(
    val sessionName: String,
    val sessionParticipantList: List<UserEntity>
)
