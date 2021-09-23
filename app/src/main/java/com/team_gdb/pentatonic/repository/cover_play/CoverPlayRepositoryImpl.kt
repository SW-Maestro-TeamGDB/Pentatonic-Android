package com.team_gdb.pentatonic.repository.cover_play

import com.apollographql.apollo.api.Response
import com.apollographql.apollo.rx3.rxQuery
import com.team_gdb.pentatonic.GetCoverCommentQuery
import com.team_gdb.pentatonic.network.NetworkHelper.apolloClient
import io.reactivex.rxjava3.core.Observable

class CoverPlayRepositoryImpl: CoverPlayRepository {
    override fun getComment(bandId: String): Observable<Response<GetCoverCommentQuery.Data>> =
        apolloClient.rxQuery(GetCoverCommentQuery(bandId))
}