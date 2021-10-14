package com.team_gdb.pentatonic.repository.whole_artist

import com.apollographql.apollo.api.Response
import com.team_gdb.pentatonic.GetUserListQuery
import io.reactivex.rxjava3.core.Observable

interface WholeArtistRepository {
    fun getUserList(content: String): Observable<Response<GetUserListQuery.Data>>
}