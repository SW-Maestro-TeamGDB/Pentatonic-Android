package com.team_gdb.pentatonic.data.model

import java.io.Serializable


/**
 * Cover (밴드, 솔로) 아이템 정보를 담는 클래스
 *
 * @property id             커버 고유 식별 아이디
 * @property coverName      커버 이름
 * @property imageUrl       커버 대표 이미지 URL
 * @property introduction   커버 한줄 소개
 * @property sessionList    커버 참여중인 세션 목록 <악기, 사용자 아이템>
 * @property like           커버 좋아요 수
 * @property view           커버 조회 수
 */
data class CoverItem(
    val id: Int,
    val coverName: String,
    val imageUrl: String,
    val introduction: String,
    val sessionList: List<Map<String, String>>,
    val like: Int,
    val view: Int,
): Serializable