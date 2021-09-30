package com.team_gdb.pentatonic.repository.lounge

import com.apollographql.apollo.api.Response
import com.apollographql.apollo.rx3.rxQuery
import com.team_gdb.pentatonic.GetTrendBandsQuery
import com.team_gdb.pentatonic.GetUserInfoQuery
import com.team_gdb.pentatonic.GetWeeklyChallengeSongInfoQuery
import com.team_gdb.pentatonic.data.model.SongEntity
import com.team_gdb.pentatonic.network.NetworkHelper.apolloClient
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

interface LoungeRepository {
    fun getWeeklyChallengeSongInfo(): Observable<Response<GetWeeklyChallengeSongInfoQuery.Data>>
    fun getTrendBands(): Observable<Response<GetTrendBandsQuery.Data>>
    fun getUserInfo(userId: String): Observable<Response<GetUserInfoQuery.Data>>
}