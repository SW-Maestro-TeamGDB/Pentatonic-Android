package com.team_gdb.pentatonic.repository.cover_view

import com.apollographql.apollo.api.Response
import com.team_gdb.pentatonic.*
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

interface CoverViewRepository {
    fun getBandCoverInfo(bandId: String): Observable<Response<GetBandCoverInfoQuery.Data>>
    fun getMergedCover(coverList: List<String>): Single<Response<MergeCoverMutation.Data>>
    fun getUserLibrary(userId: String): Observable<Response<GetUserLibraryQuery.Data>>
    fun joinBand(bandId: String, coverId: String, sessionName: String): Single<Response<JoinBandMutation.Data>>
    fun deleteBand(bandId: String): Single<Response<DeleteBandMutation.Data>>
    fun likeBand(bandId: String): Single<Response<LikeMutation.Data>>
}