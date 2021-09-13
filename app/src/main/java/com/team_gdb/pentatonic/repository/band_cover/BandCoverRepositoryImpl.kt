package com.team_gdb.pentatonic.repository.band_cover

import com.apollographql.apollo.rx3.rxMutate
import com.team_gdb.pentatonic.MergeAudiosMutation
import com.team_gdb.pentatonic.network.NetworkHelper.apolloClient
import com.team_gdb.pentatonic.type.MergeAudiosInput
import io.reactivex.rxjava3.core.Single
import okhttp3.Response

class BandCoverRepositoryImpl: BandCoverRepository {

    // 전달해준 cover URL 의 List 를 통해 병합된 음원을 제공해주는 뮤테이션
    override fun getMergedAudios(coverList: List<String>)
    = apolloClient.rxMutate(MergeAudiosMutation(mergeAudiosInput = MergeAudiosInput(coverList)))
}