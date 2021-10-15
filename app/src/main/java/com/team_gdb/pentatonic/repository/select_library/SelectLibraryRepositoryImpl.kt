package com.team_gdb.pentatonic.repository.select_library

import com.apollographql.apollo.api.Input
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.rx3.rxMutate
import com.apollographql.apollo.rx3.rxQuery
import com.team_gdb.pentatonic.CreateBandMutation
import com.team_gdb.pentatonic.GetUserLibraryQuery
import com.team_gdb.pentatonic.JoinBandMutation
import com.team_gdb.pentatonic.data.model.SessionSettingEntity
import com.team_gdb.pentatonic.network.NetworkHelper
import com.team_gdb.pentatonic.type.*
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

class SelectLibraryRepositoryImpl: SelectLibraryRepository {
    override fun getUserLibrary(userId: String): Observable<Response<GetUserLibraryQuery.Data>> =
        NetworkHelper.apolloClient.rxQuery(GetUserLibraryQuery(userId))


    override fun createBand(
        sessionConfig: List<SessionSettingEntity>,
        bandName: String,
        bandIntroduction: String,
        backgroundUrl: String,
        songId: String
    ): Single<Response<CreateBandMutation.Data>> {
        val sessionList = sessionConfig.map {
            SessionConfigInput(
                maxMember = it.count,
                session = SESSION_TYPE.valueOf(it.sessionSetting.name)
            )
        }
        val isSoloBand = sessionConfig.size == 1 && sessionConfig[0].count == 1
        return NetworkHelper.apolloClient.rxMutate(
            CreateBandMutation(
                CreateBandInput(
                    sessionConfig = sessionList,
                    band = CreateBandAllInput(
                        name = bandName,
                        introduce = bandIntroduction,
                        backGroundURI = Input.optional(backgroundUrl),
                        songId = songId,
                        isSoloBand = isSoloBand
                    )
                )
            )
        )
    }

    // 밴드 커버에 참여 요청하는 뮤테이션
    override fun joinBand(bandId: String, coverId: String, sessionName: String) =
        NetworkHelper.apolloClient.rxMutate(
            JoinBandMutation(
                JoinBandInput(
                    band = JoinBandIdInput(bandId = bandId),
                    session = JoinBandSessionInput(
                        coverId = coverId,
                        position = SESSION_TYPE.valueOf(sessionName)
                    )
                )
            )
        )
}

