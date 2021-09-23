package com.team_gdb.pentatonic.repository.create_cover

import com.apollographql.apollo.api.FileUpload
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.rx3.rxMutate
import com.team_gdb.pentatonic.UploadCoverFileMutation
import com.team_gdb.pentatonic.UploadImageFileMutation
import com.team_gdb.pentatonic.network.NetworkHelper
import com.team_gdb.pentatonic.network.NetworkHelper.apolloClient
import com.team_gdb.pentatonic.type.UploadCoverFileInput
import com.team_gdb.pentatonic.type.UploadImageInput
import io.reactivex.rxjava3.core.Single

class CreateCoverRepositoryImpl : CreateCoverRepository {
    override fun uploadImageFile(filePath: String): Single<Response<UploadImageFileMutation.Data>> =
        apolloClient.rxMutate(
            UploadImageFileMutation(
                UploadImageInput(file = FileUpload("image/*", filePath))
            )
        )
}