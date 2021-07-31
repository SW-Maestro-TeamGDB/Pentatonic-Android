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

class UserVerifyRepositoryImpl : UserVerifyRepository {

    /**
     * 인증번호 전송을 요청하는 메소드
     * @param phoneNumber   사용자 휴대전화 번호
     * @return              Rx Single Observable 객체
     */
    override fun sendAuthCode(phoneNumber: String): Single<Response<SendAuthCodeMutation.Data>> =
        apolloClient.rxMutate(
            SendAuthCodeMutation(
                SendAuthCodeInput(
                    isRegistration = true,
                    phoneNumber = phoneNumber
                )
            )
        ).subscribeOn(Schedulers.io())

    /**
     * 회원가입을 요청하는 메소드
     * @param registerForm  사용자 회원가입 입력폼 정보
     * @param phoneNumber   사용자 휴대전화 번호
     * @param authCode      사용자 인증번호
     * @return              Rx Single Observable 객체
     */
    override fun requestRegister(
        registerForm: RegisterForm,
        phoneNumber: String,
        authCode: String
    ): Single<Response<RegisterMutation.Data>> =
        apolloClient.rxMutate(
            RegisterMutation(
                RegisterInput(
                    RegisterArgs(
                        registerForm.nickname,
                        registerForm.id,
                        registerForm.password,
                        registerForm.userType
                    ),
                    phoneNumber,
                    authCode.toInt()
                )
            )
        ).subscribeOn(Schedulers.io())

}