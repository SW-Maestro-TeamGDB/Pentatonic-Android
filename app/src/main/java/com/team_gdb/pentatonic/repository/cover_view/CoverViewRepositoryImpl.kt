package com.team_gdb.pentatonic.repository.cover_view

import com.apollographql.apollo.api.Response
import com.apollographql.apollo.rx3.rxMutate
import com.apollographql.apollo.rx3.rxQuery
import com.team_gdb.pentatonic.*
import com.team_gdb.pentatonic.network.NetworkHelper.apolloClient
import com.team_gdb.pentatonic.type.*
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

class CoverViewRepositoryImpl : CoverViewRepository {
    // 넘겨준 ID 에 해당하는 밴드의 모든 정보를 가져오는 쿼리
    override fun getBandCoverInfo(bandId: String): Observable<Response<GetBandCoverInfoQuery.Data>> =
        apolloClient.rxQuery(GetBandCoverInfoQuery(bandId))

    // 전달해준 coverURL 의 List 를 통해 병합된 음원을 제공해주는 뮤테이션
    override fun getMergedCover(coverList: List<String>) =
        apolloClient.rxMutate(MergeCoverMutation(mergeAudiosInput = MergeAudiosInput(coverList)))

    // 사용자의 라이브러리를 조회하는 쿼리
    override fun getUserLibrary(userId: String): Observable<Response<GetUserLibraryQuery.Data>> =
        apolloClient.rxQuery(GetUserLibraryQuery(userId))

    // 밴드 커버에 참여 요청하는 뮤테이션
    override fun joinBand(bandId: String, coverId: String, sessionName: String) =
        apolloClient.rxMutate(
            JoinBandMutation(
                JoinBandInput(
                    band = JoinBandIdInput(bandId = bandId),
                    session = JoinBandSessionInput(
                        coverId = coverId,
                        position = SESSION_TYPE.valueOf(sessionName)
                    )
                )
            )
        )

    // 밴드 삭제 요청 뮤테이션
    override fun deleteBand(bandId: String): Single<Response<DeleteBandMutation.Data>> =
        apolloClient.rxMutate(
            DeleteBandMutation(DeleteBandInput(DeleteBandIdInput(bandId)))
        )

    // 밴드 좋아요 토글 뮤테이션
    override fun likeBand(bandId: String): Single<Response<LikeMutation.Data>> =
        apolloClient.rxMutate(
            LikeMutation(LikeInput(band = LikeBandIdInput(bandId)))
        )

    // 밴드 추방 및 나가기 뮤테이션
    override fun leaveBand(
        bandId: String,
        coverId: String
    ): Single<Response<LeaveBandMutation.Data>> =
        apolloClient.rxMutate(
            LeaveBandMutation(
                LeaveBandInput(
                    band = LeaveBandIdInput(bandId),
                    session = LeaveBandCoverIdInput(coverId)
                )
            )
        )

    override fun getSongInstrument(songId: String): Observable<Response<GetSongInstrumentQuery.Data>> =
        apolloClient.rxQuery(GetSongInstrumentQuery(songId))
}