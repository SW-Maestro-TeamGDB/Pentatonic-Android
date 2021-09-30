package com.team_gdb.pentatonic.repository.weekly_challenge

import com.apollographql.apollo.api.Response
import com.apollographql.apollo.rx3.rxQuery
import com.team_gdb.pentatonic.GetSongInfoQuery
import com.team_gdb.pentatonic.network.NetworkHelper
import io.reactivex.rxjava3.core.Observable

class WeeklyChallengeRepositoryImpl : WeeklyChallengeRepository {
    override fun getWeeklyChallengeSongInfo(songId: String): Observable<Response<GetSongInfoQuery.Data>> =
        NetworkHelper.apolloClient.rxQuery(
            GetSongInfoQuery(songId)
        )
}