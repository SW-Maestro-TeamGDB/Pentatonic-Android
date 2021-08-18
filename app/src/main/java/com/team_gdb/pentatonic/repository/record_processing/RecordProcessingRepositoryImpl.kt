package com.team_gdb.pentatonic.repository.record_processing

import com.apollographql.apollo.api.FileUpload
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.rx3.rxMutate
import com.team_gdb.pentatonic.UploadCoverFileMutation
import com.team_gdb.pentatonic.UploadCoverMutation
import com.team_gdb.pentatonic.network.NetworkHelper.apolloClient
import com.team_gdb.pentatonic.type.CoverInput
import com.team_gdb.pentatonic.type.SESSION_TYPE
import com.team_gdb.pentatonic.type.UploadCoverFileInput
import com.team_gdb.pentatonic.type.UploadCoverInput
import io.reactivex.rxjava3.core.Single

class RecordProcessingRepositoryImpl : RecordProcessingRepository {
    override fun uploadCoverFile(filePath: String): Single<Response<UploadCoverFileMutation.Data>> =
        apolloClient.rxMutate(
            UploadCoverFileMutation(
                UploadCoverFileInput(file = FileUpload("audio/mpeg3", filePath))
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
}