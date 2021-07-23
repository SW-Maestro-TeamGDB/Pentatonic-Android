package com.team_gdb.pentatonic.ui.register

import androidx.lifecycle.MutableLiveData
import com.newidea.mcpestore.libs.base.BaseViewModel
import com.team_gdb.pentatonic.repository.register.RegisterRepository

class RegisterViewModel(private val repository: RegisterRepository) : BaseViewModel() {
    val idField: MutableLiveData<String> = MutableLiveData<String>()
    val nicknameField: MutableLiveData<String> = MutableLiveData<String>()
    val userTypeField: MutableLiveData<Int> = MutableLiveData<Int>()

    val checkDone: MutableLiveData<MutableList<Boolean>>
        get() = repository.checkCompleteEvent

    val isValidId: MutableLiveData<Boolean>
        get() = repository.isValidId
    val isValidNickname: MutableLiveData<Boolean>
        get() = repository.isValidNickname

    fun isValidForm() {
        val id: String = idField.value ?: ""
        val nickname: String = nicknameField.value ?: ""

        repository.isValidForm(id, nickname)
    }
}