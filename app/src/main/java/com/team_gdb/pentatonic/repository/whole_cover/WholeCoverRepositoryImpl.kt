package com.team_gdb.pentatonic.repository.whole_cover

import com.apollographql.apollo.api.Input
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.rx3.rxQuery
import com.team_gdb.pentatonic.GetCoverQuery
import com.team_gdb.pentatonic.network.NetworkHelper.apolloClient
import com.team_gdb.pentatonic.type.BandFilter
import com.team_gdb.pentatonic.type.QueryBandInput
import io.reactivex.rxjava3.core.Observable

class WholeCoverRepositoryImpl : WholeCoverRepository {
    override fun getCover(query: String): Observable<Response<GetCoverQuery.Data>> =
        apolloClient.rxQuery(
            GetCoverQuery(
                queryBandFilter = QueryBandInput(
                    type = BandFilter.NAME,
                    content = Input.optional(query)
                )
            )
        )
}