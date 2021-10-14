package com.team_gdb.pentatonic.repository.whole_artist

import com.apollographql.apollo.api.Response
import com.apollographql.apollo.rx3.rxQuery
import com.team_gdb.pentatonic.GetUserListQuery
import com.team_gdb.pentatonic.network.NetworkHelper.apolloClient
import io.reactivex.rxjava3.core.Observable

class WholeArtistRepositoryImpl : WholeArtistRepository {
    override fun getUserList(content: String): Observable<Response<GetUserListQuery.Data>> =
        apolloClient.rxQuery(GetUserListQuery(content))
}
