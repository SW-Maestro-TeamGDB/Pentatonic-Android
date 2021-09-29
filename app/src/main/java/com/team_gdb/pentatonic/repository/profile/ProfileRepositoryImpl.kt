package com.team_gdb.pentatonic.repository.profile

import com.apollographql.apollo.api.Response
import com.apollographql.apollo.rx3.rxMutate
import com.apollographql.apollo.rx3.rxQuery
import com.team_gdb.pentatonic.FollowMutation
import com.team_gdb.pentatonic.GetUserInfoQuery
import com.team_gdb.pentatonic.network.NetworkHelper
import com.team_gdb.pentatonic.network.NetworkHelper.apolloClient
import com.team_gdb.pentatonic.type.FollowInput
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

class ProfileRepositoryImpl : ProfileRepository {
    override fun getUserInfo(userId: String): Observable<Response<GetUserInfoQuery.Data>> =
        apolloClient.rxQuery(GetUserInfoQuery(getUserInfoUserId = userId))

    override fun followUser(userId: String): Single<Response<FollowMutation.Data>> =
        apolloClient.rxMutate(FollowMutation(FollowInput(userId)))

}