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

    /**
     * @return : RegisterFormError enum 클래스 값 중 하나 (어떤 오류가 발생한 건지)
     */
    fun isValidForm(): List<RegisterFormError> {
        val id: String = idField.value ?: ""
        val nickname: String = nicknameField.value ?: ""

        return repository.isValidForm(id, nickname)
    }
}