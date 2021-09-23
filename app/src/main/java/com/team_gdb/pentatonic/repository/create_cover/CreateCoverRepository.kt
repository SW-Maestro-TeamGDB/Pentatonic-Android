package com.team_gdb.pentatonic.repository.create_cover

import com.apollographql.apollo.api.Response
import com.team_gdb.pentatonic.UploadImageFileMutation
import io.reactivex.rxjava3.core.Single

interface CreateCoverRepository {
    fun uploadImageFile(filePath: String): Single<Response<UploadImageFileMutation.Data>>
}