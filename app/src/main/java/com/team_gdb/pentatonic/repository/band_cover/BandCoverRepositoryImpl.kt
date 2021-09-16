package com.team_gdb.pentatonic.repository.band_cover

import com.apollographql.apollo.rx3.rxMutate
import com.apollographql.apollo.rx3.rxQuery
import com.team_gdb.pentatonic.GetBandCoverInfoQuery
import com.team_gdb.pentatonic.MergeAudiosMutation
import com.team_gdb.pentatonic.network.NetworkHelper.apolloClient
import com.team_gdb.pentatonic.type.MergeAudiosInput
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import okhttp3.Response

class BandCoverRepositoryImpl : BandCoverRepository {
    // 넘겨준 ID 에 해당하는 밴드의 모든 정보를 가져오는 쿼리
    override fun getBandCoverInfo(bandId: String): Observable<com.apollographql.apollo.api.Response<GetBandCoverInfoQuery.Data>> =
        apolloClient.rxQuery(GetBandCoverInfoQuery(bandId))

    // 전달해준 cover URL 의 List 를 통해 병합된 음원을 제공해주는 뮤테이션
    override fun getMergedAudios(coverList: List<String>) =
        apolloClient.rxMutate(MergeAudiosMutation(mergeAudiosInput = MergeAudiosInput(coverList)))
}