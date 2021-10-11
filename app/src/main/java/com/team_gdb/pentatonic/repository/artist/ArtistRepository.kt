package com.team_gdb.pentatonic.repository.artist

import com.apollographql.apollo.api.Response
import com.team_gdb.pentatonic.GetRankedBandListQuery
import com.team_gdb.pentatonic.GetRankedUserListQuery
import io.reactivex.rxjava3.core.Observable

interface ArtistRepository {
    fun getRankedUserList(): Observable<Response<GetRankedUserListQuery.Data>>
    fun getRankedCoverList(): Observable<Response<GetRankedBandListQuery.Data>>
}