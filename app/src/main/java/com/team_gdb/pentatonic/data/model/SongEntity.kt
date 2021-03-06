package com.team_gdb.pentatonic.data.model

import java.io.Serializable

/**
 * 펜타토닉에서 제공하는 곡의 정보
 *
 * @property songId             // 곡 고유 아이디
 * @property songUrl            // 곡 MR URL
 * @property songName           // 곡 제목
 * @property songLevel          // 곡 난이도
 * @property artistName         // 아티스트명 (가수명)
 * @property albumJacketImage   // 앨범자켓 이미지
 * @property albumName          // 앨범 제목
 * @property albumReleaseDate   // 앨범 발매일
 * @property songGenre          // 곡 장르
 * @property isFreeSong         // 자유곡 여부
 * @property duration           // 기간
 */
data class SongEntity(
    var songId: String,
    val songUrl: String,
    val songName: String,
    val songLevel: Int,
    val artistName: String,
    val albumJacketImage: String,
    val albumName: String,
    val albumReleaseDate: String,
    val songGenre: String,
    val isWeeklyChallenge: Boolean,
    val isFreeSong: Boolean,
    val duration: Double
) : Serializable
