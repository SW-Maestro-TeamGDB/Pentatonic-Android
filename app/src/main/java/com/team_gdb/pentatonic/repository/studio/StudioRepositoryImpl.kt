package com.team_gdb.pentatonic.repository.studio

import com.apollographql.apollo.api.Response
import com.apollographql.apollo.rx3.rxQuery
import com.team_gdb.pentatonic.GetRecommendBandListQuery
import com.team_gdb.pentatonic.GetSongListQuery
import com.team_gdb.pentatonic.network.NetworkHelper.apolloClient
import com.team_gdb.pentatonic.type.GetSongsFilter
import com.team_gdb.pentatonic.type.QuerySongInput
import com.team_gdb.pentatonic.type.SORT_OPTION
import io.reactivex.rxjava3.core.Observable

class StudioRepositoryImpl : StudioRepository {
    override fun getSongList(): Observable<Response<GetSongListQuery.Data>> =
        apolloClient.rxQuery(
            GetSongListQuery(
                QuerySongInput(
                    type = GetSongsFilter.ALL,
                    sort = SORT_OPTION.DATE_DESC
                )
            )
        )

    override fun getRecommendCoverList(): Observable<Response<GetRecommendBandListQuery.Data>> =
        apolloClient.rxQuery(GetRecommendBandListQuery())
}