package com.team_gdb.pentatonic.repository.lounge

import com.team_gdb.pentatonic.data.model.Song
import io.reactivex.rxjava3.core.Single

interface LoungeRepository {
    fun getWeeklyChallengeSong(): Single<Song>

}