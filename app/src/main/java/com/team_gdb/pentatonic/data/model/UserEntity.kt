package com.team_gdb.pentatonic.data.model

import java.io.Serializable

/**
 * 펜타토닉 사용자 정보
 *
 * @property id             // 사용자 ID
 * @property username       // 사용자 닉네임
 * @property profileImage   // 사용자 프로필 이미지
 * @property introduction   // 사용자 한 줄 소개 (상메)
 */
data class UserEntity(
    val id: String,
    val username: String,
    val profileImage: String,
    val introduction: String
): Serializable
