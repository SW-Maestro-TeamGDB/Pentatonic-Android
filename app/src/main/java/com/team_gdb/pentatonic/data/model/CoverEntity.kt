package com.team_gdb.pentatonic.data.model

import java.io.Serializable


/**
 * 커버 (밴드, 솔로) 아이템 정보
 *
 * @property id                커버 고유 식별 아이디
 * @property coverName         커버 이름
 * @property imageURL          커버 대표 이미지 URL
 * @property introduction      커버 한줄 소개
 * @property sessionDataList   커버 참여중인 세션 목록 <사용자 아이템, 악기>
 * @property like              커버 좋아요 수
 * @property view              커버 조회 수
 */
data class CoverEntity(
    val id: String,
    val originalSong: String,
    val coverName: String,
    val imageURL: String,
    val introduction: String,
    val sessionDataList: List<SessionData>,
    val like: Int,
    val view: Int,
): Serializable

