package com.team_gdb.pentatonic.repository.my_page

import com.apollographql.apollo.api.FileUpload
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.rx3.rxMutate
import com.apollographql.apollo.rx3.rxQuery
import com.team_gdb.pentatonic.*
import com.team_gdb.pentatonic.network.NetworkHelper.apolloClient
import com.team_gdb.pentatonic.type.*
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import timber.log.Timber

class MyPageRepositoryImpl : MyPageRepository {
    override fun getUserInfo(userId: String): Observable<Response<GetUserInfoQuery.Data>> =
        apolloClient.rxQuery(GetUserInfoQuery(getUserInfoUserId = userId))

    override fun editCover(
        coverId: String,
        coverName: String
    ): Single<Response<UpdateCoverMutation.Data>> = apolloClient.rxMutate(
        UpdateCoverMutation(
            updateCoverInput = UpdateCoverInput(
                UpdateCoverAllInput(coverId, Input.optional(coverName))
            )
        )
    )

    override fun deleteCover(coverId: String): Single<Response<DeleteCoverMutation.Data>> =
        apolloClient.rxMutate(
            DeleteCoverMutation(
                deleteCoverInput = DeleteCoverInput(
                    cover = DeleteCoverIdInput(
                        coverId
                    )
                )
            )
        )

    override fun updateProfileMutation(
        previousUsername: String,
        username: String,
        profileURI: String,
        introduce: String,
        facebookUrl: String?,
        instagramUrl: String?,
        twitterUrl: String?,
        kakaoUrl: String?
    ): Single<Response<ChangeProfileMutation.Data>> {
        val input = if (previousUsername == username) {
            ChangeUserProfileInput(
                profileURI = Input.optional(profileURI),
                introduce = Input.optional(introduce),
                social = Input.fromNullable(
                    SocialInput(
                        facebook = Input.fromNullable(facebookUrl),
                        instagram = Input.fromNullable(instagramUrl),
                        twitter = Input.fromNullable(twitterUrl),
                        kakao = Input.fromNullable(kakaoUrl)
                    )
                )
            )
        } else {
            ChangeUserProfileInput(
                username = Input.optional(username),
                profileURI = Input.optional(profileURI),
                introduce = Input.optional(introduce),
                social = Input.fromNullable(
                    SocialInput(
                        facebook = Input.fromNullable(facebookUrl),
                        instagram = Input.fromNullable(instagramUrl),
                        twitter = Input.fromNullable(twitterUrl),
                        kakao = Input.fromNullable(kakaoUrl)
                    )
                )
            )
        }
        return apolloClient.rxMutate(
            ChangeProfileMutation(
                ChangeProfileInput(
                    user = input
                )
            )
        )
    }

    override fun uploadImageFile(filePath: String): Single<Response<UploadImageFileMutation.Data>> =
        apolloClient.rxMutate(
            UploadImageFileMutation(
                UploadImageInput(file = FileUpload("image/*", filePath))
            )
        )
}