package com.team_gdb.pentatonic.data.model

import android.net.Uri
import java.io.Serializable

data class CreatedCoverEntity(
    val coverName: String,
    val coverIntroduction: String,
    val coverSong: SongEntity,
    var coverSessionConfig: List<SessionSettingEntity>,
    var coverRecord: Uri?
): Serializable