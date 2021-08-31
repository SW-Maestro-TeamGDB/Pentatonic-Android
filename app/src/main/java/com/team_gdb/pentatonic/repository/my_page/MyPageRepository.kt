package com.team_gdb.pentatonic.repository.my_page

import com.apollographql.apollo.api.Response
import com.team_gdb.pentatonic.DeleteCoverMutation
import com.team_gdb.pentatonic.GetUserInfoQuery
import com.team_gdb.pentatonic.UpdateCoverMutation
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

interface MyPageRepository {
    fun getUserInfo(id: String): Observable<Response<GetUserInfoQuery.Data>>
    fun editCover(coverId: String, coverName: String): Single<Response<UpdateCoverMutation.Data>>
    fun deleteCover(coverId: String): Single<Response<DeleteCoverMutation.Data>>
}