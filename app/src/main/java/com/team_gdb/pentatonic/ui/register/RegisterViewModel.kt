package com.team_gdb.pentatonic.ui.register

import android.util.EventLog
import androidx.lifecycle.MutableLiveData
import com.team_gdb.pentatonic.base.BaseViewModel
import com.team_gdb.pentatonic.repository.register.RegisterRepository
import com.team_gdb.pentatonic.util.Event
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber

class RegisterViewModel(private val repository: RegisterRepository) : BaseViewModel() {
    val idField: MutableLiveData<String> = MutableLiveData<String>()
    val nicknameField: MutableLiveData<String> = MutableLiveData<String>()
    val userTypeField: MutableLiveData<Int> = MutableLiveData<Int>()

    val completeCheckValidation: MutableLiveData<Event<Array<Boolean>>> = MutableLiveData()

    fun isValidForm() {
        val id: String = idField.value!!
        val nickname: String = nicknameField.value!!

        var eventResult = arrayOf(false, false)
        repository.isValidForm(id, nickname)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = {
                    Timber.d(it.data.toString())
                    Timber.d(it.data?.isValidId.toString())
                    Timber.d(it.data?.isValidUsername.toString())
                    if (it.data?.isValidId == true) {
                        eventResult[0] = true
                    }
                    if (it.data?.isValidUsername == true) {
                        eventResult[1] = true
                    }
                },
                onComplete = {
                    completeCheckValidation.value = Event(eventResult)
                },
                onError = {
                    Timber.e(it)
                }
            )
    }
}