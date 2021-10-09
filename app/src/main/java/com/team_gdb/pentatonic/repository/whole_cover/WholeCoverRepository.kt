package com.team_gdb.pentatonic.repository.whole_cover

import com.apollographql.apollo.api.Response
import com.team_gdb.pentatonic.GetBandListQuery
import io.reactivex.rxjava3.core.Observable

interface WholeCoverRepository {
    fun queryBandList(query: String, first: Int = 20, after: String = ""): Observable<Response<GetBandListQuery.Data>>
}