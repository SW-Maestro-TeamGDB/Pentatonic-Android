package com.team_gdb.pentatonic.repository.select_song

import com.apollographql.apollo.api.Response
import com.team_gdb.pentatonic.GetSongListQuery
import io.reactivex.rxjava3.core.Observable
import java.util.*

interface SelectSongRepository {
    fun getSongQuery(content: String): Observable<Response<GetSongListQuery.Data>>
}