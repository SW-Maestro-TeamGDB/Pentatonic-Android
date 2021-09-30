package com.team_gdb.pentatonic.repository.weekly_challenge

import com.apollographql.apollo.api.Response
import com.team_gdb.pentatonic.GetSongInfoQuery
import io.reactivex.rxjava3.core.Observable

interface WeeklyChallengeRepository {
    fun getWeeklyChallengeSongInfo(songId: String): Observable<Response<GetSongInfoQuery.Data>>
}