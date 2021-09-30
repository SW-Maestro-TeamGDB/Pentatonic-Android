package com.team_gdb.pentatonic.repository.record_processing

import com.apollographql.apollo.api.Response
import com.team_gdb.pentatonic.*
import com.team_gdb.pentatonic.data.model.SessionSettingEntity
import io.reactivex.rxjava3.core.Single

interface RecordProcessingRepository {
    fun getMergedResult(coverList: List<String>): Single<Response<MergeCoverMutation.Data>>

    fun uploadCoverFile(filePath: String): Single<Response<UploadCoverFileMutation.Data>>
    fun uploadCoverToLibrary(
        name: String,
        coverURI: String,
        songId: String,
        position: String
    ): Single<Response<UploadCoverMutation.Data>>

    fun createBand(
        sessionConfig: List<SessionSettingEntity>,
        bandName: String,
        bandIntroduction: String,
        backgroundUrl: String,
        songId: String
    ): Single<Response<CreateBandMutation.Data>>

    fun joinBand(bandId: String, coverId: String, sessionName: String): Single<Response<JoinBandMutation.Data>>
}