package com.team_gdb.pentatonic.ui.login

import androidx.lifecycle.MutableLiveData
import com.newidea.mcpestore.libs.base.BaseViewModel
import com.team_gdb.pentatonic.repository.login.LoginRepository
import com.team_gdb.pentatonic.util.Event
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber

class LoginViewModel(private val repository: LoginRepository) : BaseViewModel() {
    val idField: MutableLiveData<String> = MutableLiveData()
    val passwordField: MutableLiveData<String> = MutableLiveData()

    var loginCompleteEvent: MutableLiveData<Event<Boolean>> =
        MutableLiveData<Event<Boolean>>(Event(false))
    var userToken: MutableLiveData<String> = MutableLiveData<String>("")

    /**
     * 사용자가 입력한 로그인 정보를 기반으로 로그인 뮤테이션 호출
     */
    fun login() {
        val disposable =
            repository.requestLogin(idField.value.toString(), passwordField.value.toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy {
                    Timber.d(it.data.toString())
                    if (it.data != null) {
                        loginCompleteEvent.value = Event(true)
                        userToken.value = it.data!!.login.toString()
                    }
                }
        addDisposable(disposable)
    }
}