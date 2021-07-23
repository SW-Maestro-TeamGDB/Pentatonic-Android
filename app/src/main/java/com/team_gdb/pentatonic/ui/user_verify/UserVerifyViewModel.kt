package com.team_gdb.pentatonic.ui.user_verify

import androidx.lifecycle.MutableLiveData
import com.newidea.mcpestore.libs.base.BaseViewModel
import com.team_gdb.pentatonic.data.model.RegisterForm
import com.team_gdb.pentatonic.repository.user_verify.UserVerifyRepository
import com.team_gdb.pentatonic.util.Event
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers

class UserVerifyViewModel(private val repository: UserVerifyRepository) : BaseViewModel() {
    val phoneNumberField: MutableLiveData<String> = MutableLiveData<String>()
    val authCodeField: MutableLiveData<String> = MutableLiveData<String>()

    val verifyCompleteEvent: MutableLiveData<Event<Boolean>> = MutableLiveData(Event(false))
    val registerCompleteEvent: MutableLiveData<Event<Boolean>> = MutableLiveData(Event(false))

    fun sendAuthCode() {
        val disposable =
            repository.sendAuthCode(phoneNumberField.value!!).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onSuccess = {
                        if (it.data != null) {
                            verifyCompleteEvent.value = Event((true))
                        } else {
                            verifyCompleteEvent.value = Event((false))
                        }
                    }
                )
        addDisposable(disposable)
    }

    fun requestRegister(user: RegisterForm) {
        val disposable =
            repository.requestRegister(user, phoneNumberField.value!!, authCodeField.value!!)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onSuccess = {
                        if (it.data != null) {
                            registerCompleteEvent.value = Event((true))
                        } else {
                            registerCompleteEvent.value = Event((false))
                        }
                    }
                )
        addDisposable(disposable)
    }
}