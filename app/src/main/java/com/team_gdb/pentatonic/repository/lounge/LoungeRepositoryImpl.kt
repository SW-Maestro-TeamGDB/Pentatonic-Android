package com.team_gdb.pentatonic.repository.lounge

import com.apollographql.apollo.api.Input
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.rx3.rxQuery
import com.team_gdb.pentatonic.GetTrendBandsQuery
import com.team_gdb.pentatonic.GetUserInfoQuery
import com.team_gdb.pentatonic.GetWeeklyChallengeSongInfoQuery
import com.team_gdb.pentatonic.data.model.SongEntity
import com.team_gdb.pentatonic.network.NetworkHelper.apolloClient
import com.team_gdb.pentatonic.type.GetSongsFilter
import com.team_gdb.pentatonic.type.QuerySongInput
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

class LoungeRepositoryImpl : LoungeRepository {
    override fun getWeeklyChallengeSongInfo(): Observable<Response<GetWeeklyChallengeSongInfoQuery.Data>> =
        apolloClient.rxQuery(
            GetWeeklyChallengeSongInfoQuery(
                querySongFilter = QuerySongInput(
                    GetSongsFilter.ALL,
                    weeklyChallenge = Input.optional(true)
                )
            )
        )

    override fun getTrendBands(): Observable<Response<GetTrendBandsQuery.Data>> =
        apolloClient.rxQuery(GetTrendBandsQuery())

    override fun getUserInfo(userId: String): Observable<Response<GetUserInfoQuery.Data>> =
        apolloClient.rxQuery(GetUserInfoQuery(getUserInfoUserId = userId))
}