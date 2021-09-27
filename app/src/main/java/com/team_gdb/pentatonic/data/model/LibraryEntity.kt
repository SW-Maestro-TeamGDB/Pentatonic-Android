package com.team_gdb.pentatonic.data.model

import com.team_gdb.pentatonic.GetUserInfoQuery
import java.io.Serializable

/**
 * 라이브러리의 커버 아이템
 *
 * @property id             고유 ID
 * @property coverDate      커버 일시
 * @property coverName      해당 커버 이름
 * @property coverDuration  커버 녹음본 길이
 * @property coverBy        커버 사용자
 * @property coverUrl       커버 녹음본 URL
 * @property originalSong   커버 원곡 정보
 * @property imageUrl       커버 대표 이미지
 * @property coverSession   세션 정보 (어떤 악기로 커버했는 지)
 */
data class LibraryEntity(
    val id: String,
    val coverDate: String,
    val coverName: String,
    val coverDuration: Double,
    val coverBy: String,
    val coverUrl: String,
    val originalSong: GetUserInfoQuery.Song1,
    val imageUrl: String,
    val coverSession: String,
): Serializable