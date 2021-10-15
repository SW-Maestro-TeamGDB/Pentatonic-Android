package com.team_gdb.pentatonic.repository.select_library

import com.apollographql.apollo.api.Response
import com.team_gdb.pentatonic.CreateBandMutation
import com.team_gdb.pentatonic.GetUserLibraryQuery
import com.team_gdb.pentatonic.JoinBandMutation
import com.team_gdb.pentatonic.data.model.SessionSettingEntity
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

interface SelectLibraryRepository {
    fun getUserLibrary(userId: String): Observable<Response<GetUserLibraryQuery.Data>>

    fun createBand(
        sessionConfig: List<SessionSettingEntity>,
        bandName: String,
        bandIntroduction: String,
        backgroundUrl: String,
        songId: String
    ): Single<Response<CreateBandMutation.Data>>

    fun joinBand(bandId: String, coverId: String, sessionName: String): Single<Response<JoinBandMutation.Data>>
}