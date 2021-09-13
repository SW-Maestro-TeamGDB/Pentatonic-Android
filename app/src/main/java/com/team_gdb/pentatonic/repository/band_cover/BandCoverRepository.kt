package com.team_gdb.pentatonic.repository.band_cover

import com.apollographql.apollo.api.Response
import com.team_gdb.pentatonic.MergeAudiosMutation
import io.reactivex.rxjava3.core.Single

interface BandCoverRepository {
    fun getMergedAudios(coverList: List<String>): Single<Response<MergeAudiosMutation.Data>>
}