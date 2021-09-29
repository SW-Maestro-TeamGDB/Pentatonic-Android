package com.team_gdb.pentatonic.repository.profile

import com.apollographql.apollo.api.Response
import com.team_gdb.pentatonic.FollowMutation
import com.team_gdb.pentatonic.GetUserInfoQuery
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

interface ProfileRepository {
    fun getUserInfo(userId: String): Observable<Response<GetUserInfoQuery.Data>>
    fun followUser(userId: String): Single<Response<FollowMutation.Data>>
}