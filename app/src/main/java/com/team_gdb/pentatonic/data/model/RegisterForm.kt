package com.team_gdb.pentatonic.data.model

import java.io.Serializable

/**
 * 서버에 회원가입 요청을 보내기 위한 기본 정보
 *
 * @property id         사용자 ID
 * @property password   사용자 PW
 * @property nickname   사용자 닉네임
 * @property userType   사용자 유형 (본업, 취미, 리스너)
 */
data class RegisterForm(
    val id: String,
    val password: String,
    val nickname: String,
    val userType: Int
) : Serializable
