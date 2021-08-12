package com.team_gdb.pentatonic.repository.register

import androidx.lifecycle.MutableLiveData
import com.apollographql.apollo.api.Response
import com.team_gdb.pentatonic.CheckIDAndUsernameQuery
import com.team_gdb.pentatonic.util.Event
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

interface RegisterRepository {
    fun isValidForm(id: String, nickname: String): Observable<Response<CheckIDAndUsernameQuery.Data>>
}