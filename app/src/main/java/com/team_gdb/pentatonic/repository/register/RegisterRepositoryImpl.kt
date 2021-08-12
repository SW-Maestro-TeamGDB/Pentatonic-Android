package com.team_gdb.pentatonic.repository.register

import androidx.lifecycle.MutableLiveData
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.rx3.rxQuery
import com.team_gdb.pentatonic.CheckIDAndUsernameQuery
import com.team_gdb.pentatonic.network.NetworkHelper.apolloClient
import com.team_gdb.pentatonic.util.Event
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber

class RegisterRepositoryImpl : RegisterRepository {
    /**
     * 회원가입시 입력한 정보가 올바른지 검사함
     * @param id        사용자의 ID
     * @param nickname  사용자의 닉네임
     */
    override fun isValidForm(id: String, nickname: String): Observable<Response<CheckIDAndUsernameQuery.Data>>
    = apolloClient.rxQuery(CheckIDAndUsernameQuery(isValidIdId = id, isValidUsernameUsername = nickname))


}