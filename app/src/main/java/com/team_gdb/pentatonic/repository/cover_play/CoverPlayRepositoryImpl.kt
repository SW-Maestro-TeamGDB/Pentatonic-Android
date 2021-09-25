package com.team_gdb.pentatonic.repository.cover_play

import com.apollographql.apollo.api.Response
import com.apollographql.apollo.rx3.rxMutate
import com.apollographql.apollo.rx3.rxQuery
import com.team_gdb.pentatonic.CreateCommentMutation
import com.team_gdb.pentatonic.GetCoverCommentQuery
import com.team_gdb.pentatonic.UpdateCommentMutation
import com.team_gdb.pentatonic.network.NetworkHelper.apolloClient
import com.team_gdb.pentatonic.type.CreateCommentInCommentInput
import com.team_gdb.pentatonic.type.CreateCommentInput
import com.team_gdb.pentatonic.type.UpdateCommentInComment
import com.team_gdb.pentatonic.type.UpdateCommentInput
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
                    comment = CreateCommentInCommentInput(
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
                    comment = UpdateCommentInComment(commentId = commentId, content = content)
                )
            )
        )
}