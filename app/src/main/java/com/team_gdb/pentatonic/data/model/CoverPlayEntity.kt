package com.team_gdb.pentatonic.data.model

import java.io.Serializable

data class CoverPlayEntity(
    val coverID: String,
    val coverName: String,
    val coverIntroduction: String,
    val coverURL: String,
    val backgroundImgURL: String,
    val likeCount: Int,
    val likeStatus: Boolean,
    val viewCount: Int,
): Serializable
