package com.team_gdb.pentatonic.data.model

import java.io.Serializable

data class User(
    val id: String,
    val username: String,
    val profileImage: String,
    val introduction: String
): Serializable
