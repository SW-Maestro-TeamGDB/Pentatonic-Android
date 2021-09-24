package com.team_gdb.pentatonic.repository.record_processing

import com.apollographql.apollo.api.FileUpload
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.rx3.rxMutate
import com.team_gdb.pentatonic.CreateBandMutation
import com.team_gdb.pentatonic.JoinBandMutation
import com.team_gdb.pentatonic.UploadCoverFileMutation
import com.team_gdb.pentatonic.UploadCoverMutation
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
        sessionName: String,
        bandName: String,
        bandIntroduction: String,
        backgroundUrl: String,
        songId: String
    ): Single<Response<CreateBandMutation.Data>> = apolloClient.rxMutate(
        CreateBandMutation(
            CreateBandInput(
                sessionConfig = listOf(SessionConfigInput(SESSION_TYPE.valueOf(sessionName), 1)),
                band = CreateBandArgsInput(
                    name = bandName,
                    introduce = bandIntroduction,
                    backGroundURI = backgroundUrl,
                    songId = songId
                )
            )
        )
    )

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