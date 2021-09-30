package com.team_gdb.pentatonic.repository.studio

import com.apollographql.apollo.api.Response
import com.team_gdb.pentatonic.GetSongListQuery
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import java.util.*

interface StudioRepository {
    fun getSongList(): Observable<Response<GetSongListQuery.Data>>
}