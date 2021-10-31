package com.team_gdb.pentatonic.repository.select_song

import com.apollographql.apollo.api.Input
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.rx3.rxQuery
import com.team_gdb.pentatonic.GetSongListQuery
import com.team_gdb.pentatonic.data.genre.Genre
import com.team_gdb.pentatonic.network.NetworkHelper.apolloClient
import com.team_gdb.pentatonic.type.*
import io.reactivex.rxjava3.core.Observable

class SelectSongRepositoryImpl : SelectSongRepository {
    override fun getSongQuery(
        content: String,
        genre: Genre?,
        level: Int?,
    ): Observable<Response<GetSongListQuery.Data>> {

        val input = QuerySongInput(
            type = GetSongsFilter.NAME,
            content = Input.optional(content),
            genre = Input.optional(genre?.let { GENRE_TYPE.valueOf(it.name) }),
            level = Input.optional(level)
        )

        return apolloClient.rxQuery(GetSongListQuery(input))
    }
}