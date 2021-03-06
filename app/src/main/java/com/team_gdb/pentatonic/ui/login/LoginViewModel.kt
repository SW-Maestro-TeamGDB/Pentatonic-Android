package com.team_gdb.pentatonic.ui.login

import androidx.lifecycle.MutableLiveData
import com.apollographql.apollo.rx3.rxMutate
import com.team_gdb.pentatonic.UpdateDeviceTokenMutation
import com.team_gdb.pentatonic.base.BaseViewModel
import com.team_gdb.pentatonic.network.NetworkHelper
import com.team_gdb.pentatonic.network.NetworkHelper.apolloClient
import com.team_gdb.pentatonic.network.applySchedulers
import com.team_gdb.pentatonic.repository.login.LoginRepository
import com.team_gdb.pentatonic.type.UpdateDeviceTokenInput
import com.team_gdb.pentatonic.util.Event
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber

class LoginViewModel(private val repository: LoginRepository) : BaseViewModel() {
    val idField: MutableLiveData<String> = MutableLiveData()
    val passwordField: MutableLiveData<String> = MutableLiveData()

    var loginCompleteEvent: MutableLiveData<Event<String>> = MutableLiveData<Event<String>>()

    /**
     * 사용자가 입력한 로그인 정보를 기반으로 로그인 뮤테이션 호출
     * - 만약 로그인에 성공하면 JWT 토큰을 Event Content 에 담음
     */
    fun login() {
        val disposable =
            repository.requestLogin(idField.value.toString(), passwordField.value.toString())
                .applySchedulers()
                .subscribeBy {
                    Timber.d(it.data?.login.toString())
                    if (!it.hasErrors()) {
                        loginCompleteEvent.value = Event(it.data?.login.toString())
                    } else {
                        loginCompleteEvent.value = Event("")
                    }
                }
        addDisposable(disposable)
    }

    fun updateDeviceToken(fcmToken: String) {
        val disposable = repository.updateDeviceToken(fcmToken).applySchedulers()
            .subscribeBy(
                onError = {
                    Timber.e(it)
                },
                onSuccess = {
                    Timber.e(it.data?.updateDeviceToken.toString())
                }
            )
        addDisposable(disposable)
    }
}