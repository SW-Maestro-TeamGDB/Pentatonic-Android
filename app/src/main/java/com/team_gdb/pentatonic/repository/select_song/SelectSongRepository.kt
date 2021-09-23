package com.team_gdb.pentatonic.repository.select_song

import com.apollographql.apollo.api.Response
import com.team_gdb.pentatonic.GetSongQuery
import io.reactivex.rxjava3.core.Observable
import java.util.*

interface SelectSongRepository {
    fun getSongQuery(content: String): Observable<Response<GetSongQuery.Data>>
}