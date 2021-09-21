package com.team_gdb.pentatonic.repository.profile

import com.apollographql.apollo.api.Response
import com.team_gdb.pentatonic.GetUserInfoQuery
import io.reactivex.rxjava3.core.Observable

interface ProfileRepository {
    fun getUserInfo(userId: String): Observable<Response<GetUserInfoQuery.Data>>
}