package com.team_gdb.pentatonic.repository.artist

import com.apollographql.apollo.api.Response
import com.apollographql.apollo.rx3.rxQuery
import com.team_gdb.pentatonic.GetRankedBandListQuery
import com.team_gdb.pentatonic.GetRankedUserListQuery
import com.team_gdb.pentatonic.network.NetworkHelper.apolloClient
import io.reactivex.rxjava3.core.Observable

class ArtistRepositoryImpl : ArtistRepository {
    override fun getRankedUserList(): Observable<Response<GetRankedUserListQuery.Data>> =
        apolloClient.rxQuery(GetRankedUserListQuery())

    override fun getRankedCoverList(): Observable<Response<GetRankedBandListQuery.Data>> =
        apolloClient.rxQuery(GetRankedBandListQuery())
}