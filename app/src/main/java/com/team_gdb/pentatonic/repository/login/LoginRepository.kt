package com.team_gdb.pentatonic.repository.login

import com.apollographql.apollo.api.Response
import com.apollographql.apollo.rx3.rxMutate
import com.team_gdb.pentatonic.LoginMutation
import com.team_gdb.pentatonic.network.NetworkHelper.apolloClient
import com.team_gdb.pentatonic.type.*
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

interface LoginRepository {
    fun requestLogin(id: String, password: String): Single<Response<LoginMutation.Data>>
}