package com.team_gdb.pentatonic.data.model

import java.io.Serializable

data class LibraryEntity(
    val id: Int,
    val coverName: String,
    val originalSong: String,
    val imageUrl: String,
    val introduction: String,
    val coverSession: String,
): Serializable