package com.team_gdb.pentatonic.repository.whole_cover

import com.apollographql.apollo.api.Response
import com.team_gdb.pentatonic.GetCoverQuery
import io.reactivex.rxjava3.core.Observable

interface WholeCoverRepository {
    fun getCover(query: String): Observable<Response<GetCoverQuery.Data>>
}