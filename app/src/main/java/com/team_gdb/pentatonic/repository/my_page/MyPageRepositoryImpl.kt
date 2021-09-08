package com.team_gdb.pentatonic.repository.my_page

import com.apollographql.apollo.api.Input
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.rx3.rxMutate
import com.apollographql.apollo.rx3.rxQuery
import com.team_gdb.pentatonic.DeleteCoverMutation
import com.team_gdb.pentatonic.GetUserInfoQuery
import com.team_gdb.pentatonic.UpdateCoverMutation
import com.team_gdb.pentatonic.network.NetworkHelper.apolloClient
import com.team_gdb.pentatonic.type.*
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

class MyPageRepositoryImpl : MyPageRepository {
    override fun getUserInfo(id: String): Observable<Response<GetUserInfoQuery.Data>> =
        apolloClient.rxQuery(GetUserInfoQuery(getUserInfoUserId = id))

    override fun editCover(
        coverId: String,
        coverName: String
    ): Single<Response<UpdateCoverMutation.Data>> = apolloClient.rxMutate(
        UpdateCoverMutation(
            updateCoverInput = UpdateCoverInput(
                UpdateCover(coverId, Input.optional(coverName))
            )
        )
    )

    override fun deleteCover(coverId: String): Single<Response<DeleteCoverMutation.Data>> =
        apolloClient.rxMutate(
            DeleteCoverMutation(
                deleteCoverInput = DeleteCoverInput(
                    cover = DeleteCover(
                        coverId
                    )
                )
            )
        )
}