package com.team_gdb.pentatonic.repository.select_song

import com.apollographql.apollo.api.Response
import com.team_gdb.pentatonic.GetSongListQuery
import com.team_gdb.pentatonic.data.genre.Genre
import io.reactivex.rxjava3.core.Observable
import java.util.*

interface SelectSongRepository {
    fun getSongQuery(
        content: String,
        genre: Genre?,
        level: Int?,
    ): Observable<Response<GetSongListQuery.Data>>
}