package com.team_gdb.pentatonic.repository.select_song

import com.apollographql.apollo.api.Input
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.rx3.rxQuery
import com.team_gdb.pentatonic.GetSongQuery
import com.team_gdb.pentatonic.network.NetworkHelper.apolloClient
import com.team_gdb.pentatonic.type.GetSongsFilter
import com.team_gdb.pentatonic.type.QuerySongInput
import io.reactivex.rxjava3.core.Observable

class SelectSongRepositoryImpl: SelectSongRepository {
    override fun getSongQuery(content: String): Observable<Response<GetSongQuery.Data>>  =
        apolloClient.rxQuery(GetSongQuery(QuerySongInput(type = GetSongsFilter.ALL, content = Input.optional(content))))
}