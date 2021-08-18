package com.team_gdb.pentatonic.repository.record_processing

import com.apollographql.apollo.api.Response
import com.team_gdb.pentatonic.UploadCoverFileMutation
import com.team_gdb.pentatonic.UploadCoverMutation
import io.reactivex.rxjava3.core.Single

interface RecordProcessingRepository {
    fun uploadCoverFile(filePath: String): Single<Response<UploadCoverFileMutation.Data>>
    fun uploadCoverToLibrary(name: String, coverURI: String, songId: String, position: String): Single<Response<UploadCoverMutation.Data>>
}