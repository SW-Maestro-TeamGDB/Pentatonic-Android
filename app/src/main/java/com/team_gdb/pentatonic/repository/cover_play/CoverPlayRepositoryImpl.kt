package com.team_gdb.pentatonic.repository.cover_play

import com.apollographql.apollo.api.Response
import com.apollographql.apollo.rx3.rxMutate
import com.apollographql.apollo.rx3.rxQuery
import com.team_gdb.pentatonic.*
import com.team_gdb.pentatonic.network.NetworkHelper.apolloClient
import com.team_gdb.pentatonic.type.*
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

class CoverPlayRepositoryImpl : CoverPlayRepository {
    override fun getComment(bandId: String): Observable<Response<GetCoverCommentQuery.Data>> =
        apolloClient.rxQuery(GetCoverCommentQuery(bandId))

    override fun createComment(
        bandId: String,
        content: String
    ): Single<Response<CreateCommentMutation.Data>> =
        apolloClient.rxMutate(
            CreateCommentMutation(
                CreateCommentInput(
                    comment = CreateCommentAllInput(
                        content = content,
                        bandId = bandId
                    )
                )
            )
        )

    override fun updateComment(
        commentId: String,
        content: String
    ): Single<Response<UpdateCommentMutation.Data>> =
        apolloClient.rxMutate(
            UpdateCommentMutation(
                UpdateCommentInput(
                    comment = UpdateCommentAllInput(commentId = commentId, content = content)
                )
            )
        )

    override fun deleteComment(commentId: String): Single<Response<DeleteCommentMutation.Data>> =
        apolloClient.rxMutate(
            DeleteCommentMutation(
                DeleteCommentInput(
                    DeleteCommentIdInput(commentId)
                )
            )
        )

    // 밴드 좋아요 토글 뮤테이션
    override fun likeBand(bandId: String): Single<Response<LikeMutation.Data>> =
        apolloClient.rxMutate(
            LikeMutation(LikeInput(band = LikeBandIdInput(bandId)))
        )
}