package com.team_gdb.pentatonic.repository.profile

import com.apollographql.apollo.api.Response
import com.apollographql.apollo.rx3.rxQuery
import com.team_gdb.pentatonic.GetUserInfoQuery
import com.team_gdb.pentatonic.network.NetworkHelper
import io.reactivex.rxjava3.core.Observable

class ProfileRepositoryImpl: ProfileRepository {
    override fun getUserInfo(userId: String): Observable<Response<GetUserInfoQuery.Data>> =
        NetworkHelper.apolloClient.rxQuery(GetUserInfoQuery(getUserInfoUserId = userId))
}