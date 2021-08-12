package com.team_gdb.pentatonic.ui.register

import android.util.EventLog
import androidx.lifecycle.MutableLiveData
import com.team_gdb.pentatonic.base.BaseViewModel
import com.team_gdb.pentatonic.repository.register.RegisterRepository
import com.team_gdb.pentatonic.util.Event

class RegisterViewModel(private val repository: RegisterRepository) : BaseViewModel() {
    val idField: MutableLiveData<String> = MutableLiveData<String>()
    val nicknameField: MutableLiveData<String> = MutableLiveData<String>()
    val userTypeField: MutableLiveData<Int> = MutableLiveData<Int>()

    val completeCheckValidation: MutableLiveData<Event<Array<Boolean>>>
        get() = repository.completeCheckValidation

    fun isValidForm() {
        val id: String = idField.value!!
        val nickname: String = nicknameField.value!!

        repository.isValidForm(id, nickname)
    }
}