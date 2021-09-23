package com.team_gdb.pentatonic.data.model

import java.io.Serializable

/**
 * 펜타토닉에서 제공하는 곡의 정보
 *
 * @property songName          // 곡 제목
 * @property artistName         // 아티스트명 (가수명)
 * @property albumJacketImage   // 앨범자켓 이미지
 * @property albumName         // 앨범 제목
 * @property albumReleaseDate   // 앨범 발매일
 * @property songGenre          // 곡 장르
 * @property songWriter         // 작곡가
 * @property lyricist           // 작사가
 */
data class SongEntity(
    val songName: String,
    val artistName: String,
    val albumJacketImage: String,
    val albumName: String,
    val albumReleaseDate: String,
    val songGenre: String,
    val songWriter: String,
    val lyricist: String,
) : Serializable
