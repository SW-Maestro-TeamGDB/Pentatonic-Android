package com.team_gdb.pentatonic.repository.lounge

import com.apollographql.apollo.api.Response
import com.apollographql.apollo.rx3.rxQuery
import com.team_gdb.pentatonic.GetTrendBandsQuery
import com.team_gdb.pentatonic.GetUserInfoQuery
import com.team_gdb.pentatonic.data.model.SongEntity
import com.team_gdb.pentatonic.network.NetworkHelper.apolloClient
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

class LoungeRepositoryImpl : LoungeRepository {
    override fun getTrendBands(): Observable<Response<GetTrendBandsQuery.Data>> =
        apolloClient.rxQuery(GetTrendBandsQuery())

    override fun getUserInfo(userId: String): Observable<Response<GetUserInfoQuery.Data>> =
        apolloClient.rxQuery(GetUserInfoQuery(getUserInfoUserId = userId))
}