package com.team_gdb.pentatonic.data.model

import java.io.Serializable

data class SongEntity(
    val name: String,
    val artist: String,
    val albumJacketImage: String
): Serializable
