package com.team_gdb.pentatonic.repository.my_page

import com.apollographql.apollo.api.Input
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.rx3.rxQuery
import com.team_gdb.pentatonic.GetUserInfoQuery
import com.team_gdb.pentatonic.network.NetworkHelper.apolloClient
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

class MyPageRepositoryImpl : MyPageRepository {
    override fun getUserInfo(id: String): Observable<Response<GetUserInfoQuery.Data>> =
        apolloClient.rxQuery(GetUserInfoQuery(getUserInfoUserId = Input.optional(id)))
}