package com.team_gdb.pentatonic.data.model

import java.io.Serializable

/**
 * 라이브러리의 커버 아이템
 *
 * @property id             고유 ID
 * @property coverName      해당 커버 이름
 * @property originalSong   커버 원곡 정보
 * @property imageUrl       커버 대표 이미지
 * @property introduction   커버 소개
 * @property coverSession   세션 정보 (어떤 악기로 커버했는 지)
 */
data class LibraryEntity(
    val id: String,
    val coverName: String,
    val coverUrl: String,
    val originalSong: String,
    val imageUrl: String,
    val introduction: String,
    val coverSession: String,
): Serializable