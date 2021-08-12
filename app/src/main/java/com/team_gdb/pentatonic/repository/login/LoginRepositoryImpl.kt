package com.team_gdb.pentatonic.repository.login

import com.apollographql.apollo.api.Response
import com.apollographql.apollo.rx3.rxMutate
import com.team_gdb.pentatonic.LoginMutation
import com.team_gdb.pentatonic.network.NetworkHelper
import com.team_gdb.pentatonic.network.NetworkHelper.apolloClient
import com.team_gdb.pentatonic.type.LoginArgs
import com.team_gdb.pentatonic.type.LoginInput
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
        apolloClient.rxMutate(LoginMutation(LoginInput(LoginArgs(id, password))))
}