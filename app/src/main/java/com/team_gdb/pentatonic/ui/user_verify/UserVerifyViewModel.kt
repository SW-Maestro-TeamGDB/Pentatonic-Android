package com.team_gdb.pentatonic.ui.user_verify

import androidx.lifecycle.MutableLiveData
import com.newidea.mcpestore.libs.base.BaseViewModel
import com.team_gdb.pentatonic.data.model.RegisterForm
import com.team_gdb.pentatonic.repository.user_verify.UserVerifyRepository
import com.team_gdb.pentatonic.util.Event
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber

class UserVerifyViewModel(private val repository: UserVerifyRepository) : BaseViewModel() {
    val phoneNumberField: MutableLiveData<String> = MutableLiveData<String>()
    val authCodeField: MutableLiveData<String> = MutableLiveData<String>()

    val verifyCompleteEvent: MutableLiveData<Event<Boolean>> = MutableLiveData<Event<Boolean>>()
    val registerCompleteEvent: MutableLiveData<Event<Boolean>> = MutableLiveData<Event<Boolean>>()

    fun sendAuthCode() {
        // +8210 형식으로 전화번호 변경 해줘야 함
        val parsedPhoneNumber = "+82" + phoneNumberField.value!!.substring(1)
        Timber.d("변환한 전화번호 : $parsedPhoneNumber")
        val disposable =
            repository.sendAuthCode(parsedPhoneNumber).subscribeOn(Schedulers.io())
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
        val parsedPhoneNumber = "+82" + phoneNumberField.value!!.substring(1)
        val disposable =
            repository.requestRegister(user, parsedPhoneNumber, authCodeField.value!!)
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