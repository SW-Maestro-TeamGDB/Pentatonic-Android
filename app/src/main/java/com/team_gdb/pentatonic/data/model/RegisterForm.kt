package com.team_gdb.pentatonic.data.model

import java.io.Serializable

data class RegisterForm(
    val id: String,
    val password: String,
    val nickname: String,
    val userType: Int
) : Serializable
