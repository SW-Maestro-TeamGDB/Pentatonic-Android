package com.team_gdb.pentatonic.repository.my_page

import com.apollographql.apollo.api.Response
import com.team_gdb.pentatonic.*
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

interface MyPageRepository {
    fun getUserInfo(userId: String): Observable<Response<GetUserInfoQuery.Data>>
    fun editCover(coverId: String, coverName: String): Single<Response<UpdateCoverMutation.Data>>
    fun deleteCover(coverId: String): Single<Response<DeleteCoverMutation.Data>>
    fun updateProfileMutation(
        username: String,
        profileURI: String,
        introduce: String
    ): Single<Response<ChangeProfileMutation.Data>>

    fun uploadImageFile(filePath: String): Single<Response<UploadImageFileMutation.Data>>
}