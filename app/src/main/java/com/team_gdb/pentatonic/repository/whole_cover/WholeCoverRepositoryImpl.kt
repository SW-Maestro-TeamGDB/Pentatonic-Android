package com.team_gdb.pentatonic.repository.whole_cover

import com.apollographql.apollo.api.Input
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.rx3.rxQuery
import com.team_gdb.pentatonic.GetBandListQuery
import com.team_gdb.pentatonic.data.genre.Genre
import com.team_gdb.pentatonic.network.NetworkHelper.apolloClient
import com.team_gdb.pentatonic.type.BandFilter
import com.team_gdb.pentatonic.type.GENRE_TYPE
import com.team_gdb.pentatonic.type.QueryBandInput
import io.reactivex.rxjava3.core.Observable
import timber.log.Timber

class WholeCoverRepositoryImpl : WholeCoverRepository {
    override fun queryBandList(
        content: String,
        genre: Genre,
        level: Int,
        first: Int,
        after: String
    ): Observable<Response<GetBandListQuery.Data>> {
        val input = if (genre == Genre.WHOLE) {
            if (level == 0) {
                QueryBandInput(
                    type = BandFilter.ALL,
                    content = Input.optional(content),
                )
            } else {
                QueryBandInput(
                    type = BandFilter.ALL,
                    content = Input.optional(content),
                    level = Input.optional(level)
                )
            }
        } else {
            if (level == 0) {
                QueryBandInput(
                    type = BandFilter.ALL,
                    content = Input.optional(content),
                    genre = Input.optional(GENRE_TYPE.valueOf(genre.name))
                )
            } else {
                QueryBandInput(
                    type = BandFilter.ALL,
                    content = Input.optional(content),
                    genre = Input.optional(GENRE_TYPE.valueOf(genre.name)),
                    level = Input.optional(level)
                )
            }
        }

        return apolloClient.rxQuery(
            GetBandListQuery(
                queryBandsFirst = 30,
                queryBandsFilter = input
            )
        )

    }
}