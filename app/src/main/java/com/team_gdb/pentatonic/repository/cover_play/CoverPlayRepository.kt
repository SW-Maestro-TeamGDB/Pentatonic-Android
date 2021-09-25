package com.team_gdb.pentatonic.repository.cover_play

import com.apollographql.apollo.api.Response
import com.team_gdb.pentatonic.CreateCommentMutation
import com.team_gdb.pentatonic.DeleteCommentMutation
import com.team_gdb.pentatonic.GetCoverCommentQuery
import com.team_gdb.pentatonic.UpdateCommentMutation
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

interface CoverPlayRepository {
    fun getComment(bandId: String): Observable<Response<GetCoverCommentQuery.Data>>
    fun createComment(bandId: String, content: String): Single<Response<CreateCommentMutation.Data>>
    fun updateComment(commentId: String, content: String): Single<Response<UpdateCommentMutation.Data>>
    fun deleteComment(commentId: String): Single<Response<DeleteCommentMutation.Data>>
}