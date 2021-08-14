package com.team_gdb.pentatonic.data.model

import java.io.Serializable

data class SongEntity(
    val songTitle: String,
    val artistName: String,
    val albumJacketImage: String,
    val albumTitle: String,
    val albumReleaseDate: String,
    val songGenre: String,
    val songWriter: String,
    val lyricist: String,
) : Serializable
