package com.team_gdb.pentatonic.repository.record_processing

import com.apollographql.apollo.api.FileUpload
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.rx3.rxMutate
import com.team_gdb.pentatonic.CreateBandMutation
import com.team_gdb.pentatonic.JoinBandMutation
import com.team_gdb.pentatonic.UploadCoverFileMutation
import com.team_gdb.pentatonic.UploadCoverMutation
import com.team_gdb.pentatonic.data.model.SessionSettingEntity
import com.team_gdb.pentatonic.network.NetworkHelper.apolloClient
import com.team_gdb.pentatonic.type.*
import io.reactivex.rxjava3.core.Single

class RecordProcessingRepositoryImpl : RecordProcessingRepository {
    override fun uploadCoverFile(filePath: String): Single<Response<UploadCoverFileMutation.Data>> =
        apolloClient.rxMutate(
            UploadCoverFileMutation(
                UploadCoverFileInput(file = FileUpload("audio/x-m4a", filePath))
            )
        )

    override fun uploadCoverToLibrary(
        name: String,
        coverURI: String,
        songId: String,
        position: String
    ): Single<Response<UploadCoverMutation.Data>> = apolloClient.rxMutate(
        UploadCoverMutation(
            UploadCoverInput(
                CoverInput(
                    name, coverURI, songId, position = SESSION_TYPE.valueOf(position)
                )
            )
        )
    )

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
        return apolloClient.rxMutate(
            CreateBandMutation(
                CreateBandInput(
                    sessionConfig = sessionList,
                    band = CreateBandArgsInput(
                        name = bandName,
                        introduce = bandIntroduction,
                        backGroundURI = backgroundUrl,
                        songId = songId,
                        isSoloBand = isSoloBand
                    )
                )
            )
        )
    }

    // 밴드 커버에 참여 요청하는 뮤테이션
    override fun joinBand(bandId: String, coverId: String, sessionName: String) =
        apolloClient.rxMutate(
            JoinBandMutation(
                JoinBandInput(
                    band = JoinBandArgsInput(bandId = bandId),
                    session = JoinSessionInput(
                        coverId = coverId,
                        position = SESSION_TYPE.valueOf(sessionName)
                    )
                )
            )
        )
}