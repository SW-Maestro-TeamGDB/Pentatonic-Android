package com.team_gdb.pentatonic.repository.cover_play

import com.apollographql.apollo.api.Response
import com.team_gdb.pentatonic.GetCoverCommentQuery
import io.reactivex.rxjava3.core.Observable

interface CoverPlayRepository {
    fun getComment(bandId: String): Observable<Response<GetCoverCommentQuery.Data>>
}