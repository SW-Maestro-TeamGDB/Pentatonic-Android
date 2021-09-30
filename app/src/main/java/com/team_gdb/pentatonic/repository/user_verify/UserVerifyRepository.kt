package com.team_gdb.pentatonic.repository.user_verify

import com.apollographql.apollo.rx3.rxMutate
import com.team_gdb.pentatonic.SendAuthCodeMutation
import io.reactivex.rxjava3.core.Single
import com.apollographql.apollo.api.Response
import com.team_gdb.pentatonic.RegisterMutation
import com.team_gdb.pentatonic.data.model.RegisterForm

interface UserVerifyRepository {

    fun sendAuthCode(phoneNumber: String): Single<Response<SendAuthCodeMutation.Data>>

    fun requestRegister(
        registerForm: RegisterForm,
        phoneNumber: String,
        authCode: String
    ): Single<Response<RegisterMutation.Data>>


}