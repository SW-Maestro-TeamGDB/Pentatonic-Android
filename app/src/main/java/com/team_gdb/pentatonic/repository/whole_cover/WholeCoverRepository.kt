package com.team_gdb.pentatonic.repository.whole_cover

import com.apollographql.apollo.api.Response
import com.team_gdb.pentatonic.GetBandListQuery
import com.team_gdb.pentatonic.data.genre.Genre
import io.reactivex.rxjava3.core.Observable

interface WholeCoverRepository {
    fun queryBandList(
        content: String,
        genre: Genre?,
        level: Int?,
        first: Int = 20,
        after: String = ""
    ): Observable<Response<GetBandListQuery.Data>>
}