package com.team_gdb.pentatonic.repository.user_verify

import com.apollographql.apollo.rx3.rxMutate
import com.team_gdb.pentatonic.SendAuthCodeMutation
import com.team_gdb.pentatonic.network.NetworkHelper.apolloClient
import com.team_gdb.pentatonic.type.SendAuthCodeInput
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import com.apollographql.apollo.api.Response
import com.team_gdb.pentatonic.RegisterMutation
import com.team_gdb.pentatonic.data.model.RegisterForm
import com.team_gdb.pentatonic.type.RegisterArgs
import com.team_gdb.pentatonic.type.RegisterInput

interface UserVerifyRepository {

    fun sendAuthCode(phoneNumber: String): Single<Response<SendAuthCodeMutation.Data>>

    fun requestRegister(
        registerForm: RegisterForm,
        phoneNumber: String,
        authCode: String
    ): Single<Response<RegisterMutation.Data>>


}