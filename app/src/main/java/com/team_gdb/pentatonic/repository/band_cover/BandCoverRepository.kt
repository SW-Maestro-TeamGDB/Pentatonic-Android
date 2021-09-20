package com.team_gdb.pentatonic.repository.band_cover

import com.apollographql.apollo.api.Response
import com.team_gdb.pentatonic.GetBandCoverInfoQuery
import com.team_gdb.pentatonic.MergeCoverMutation
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

interface BandCoverRepository {
    fun getBandCoverInfo(bandId: String): Observable<Response<GetBandCoverInfoQuery.Data>>
    fun getMergedCover(coverList: List<String>): Single<Response<MergeCoverMutation.Data>>
}