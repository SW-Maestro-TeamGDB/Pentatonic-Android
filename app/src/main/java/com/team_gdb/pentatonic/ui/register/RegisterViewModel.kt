package com.team_gdb.pentatonic.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.newidea.mcpestore.libs.base.BaseViewModel
import com.team_gdb.pentatonic.repository.register.RegisterRepository
import timber.log.Timber

class RegisterViewModel(private val repository: RegisterRepository) : BaseViewModel() {
    val idField: MutableLiveData<String> = MutableLiveData<String>()
    val passwordField: MutableLiveData<String> = MutableLiveData<String>()
    val passwordConfirmField: MutableLiveData<String> = MutableLiveData<String>()
    val nicknameField: MutableLiveData<String> = MutableLiveData<String>()
    val userTypeField: MutableLiveData<Int> = MutableLiveData<Int>()

    val checkDone: MutableLiveData<MutableList<Boolean>>
        get() = repository.checkDone

    val isValidId: MutableLiveData<Boolean>
        get() = repository.isValidId
    val isValidNickname: MutableLiveData<Boolean>
        get() = repository.isValidNickname

    /**
     * @return : RegisterFormError enum 클래스 값 중 하나 (어떤 오류가 발생한 건지)
     */
    fun isValidForm() {
        val id: String = idField.value ?: ""
        val nickname: String = nicknameField.value ?: ""

        repository.isValidForm(id, nickname)
    }


}