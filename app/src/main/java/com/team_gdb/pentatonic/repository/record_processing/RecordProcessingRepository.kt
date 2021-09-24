package com.team_gdb.pentatonic.repository.record_processing

import com.apollographql.apollo.api.Response
import com.team_gdb.pentatonic.CreateBandMutation
import com.team_gdb.pentatonic.JoinBandMutation
import com.team_gdb.pentatonic.UploadCoverFileMutation
import com.team_gdb.pentatonic.UploadCoverMutation
import io.reactivex.rxjava3.core.Single

interface RecordProcessingRepository {
    fun uploadCoverFile(filePath: String): Single<Response<UploadCoverFileMutation.Data>>
    fun uploadCoverToLibrary(
        name: String,
        coverURI: String,
        songId: String,
        position: String
    ): Single<Response<UploadCoverMutation.Data>>

    fun createBand(
        sessionName: String,
        bandName: String,
        bandIntroduction: String,
        backgroundUrl: String,
        songId: String
    ): Single<Response<CreateBandMutation.Data>>

    fun joinBand(bandId: String, coverId: String, sessionName: String): Single<Response<JoinBandMutation.Data>>
}