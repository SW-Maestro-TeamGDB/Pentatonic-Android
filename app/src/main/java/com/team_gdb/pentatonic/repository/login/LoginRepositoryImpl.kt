package com.team_gdb.pentatonic.repository.login

import com.apollographql.apollo.api.Response
import com.apollographql.apollo.rx3.rxMutate
import com.team_gdb.pentatonic.LoginMutation
import com.team_gdb.pentatonic.UpdateDeviceTokenMutation
import com.team_gdb.pentatonic.network.NetworkHelper.apolloClient
import com.team_gdb.pentatonic.type.LoginInput
import com.team_gdb.pentatonic.type.LoginUserInput
import com.team_gdb.pentatonic.type.UpdateDeviceTokenInput
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class LoginRepositoryImpl: LoginRepository {
    /**
     * 로그인 시 입력한 정보가 올바른 지 검사함
     * @param id        사용자의 ID
     * @param password  사용자의 패스워드
     * @return          Rx Single Observable 객체
     */
    override fun requestLogin(id: String, password: String): Single<Response<LoginMutation.Data>> =
        apolloClient.rxMutate(LoginMutation(LoginInput(LoginUserInput(id, password))))

    override fun updateDeviceToken(fcmToken: String): Single<Response<UpdateDeviceTokenMutation.Data>> =
        apolloClient.rxMutate(UpdateDeviceTokenMutation(UpdateDeviceTokenInput(fcmToken)))
}