package com.team_gdb.pentatonic.ui.register

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.newidea.mcpestore.libs.base.BaseViewModel
import com.team_gdb.pentatonic.data.model.RegisterForm
import com.team_gdb.pentatonic.repository.register.RegisterRepository

class RegisterViewModel(private val repository: RegisterRepository) : BaseViewModel() {
    val idField: ObservableField<String> = ObservableField("")
    val passwordField: ObservableField<String> = ObservableField("")
    val passwordConfirmField: ObservableField<String> = ObservableField("")
    val nicknameField: ObservableField<String> = ObservableField("")
    var userTypeField: MutableLiveData<Int> = MutableLiveData<Int>()


    /**
     * @return : RegisterFormError enum 클래스 값 중 하나 (어떤 오류가 발생한 건지)
     */
    fun isValidForm(): RegisterFormError {
        val id: String = idField.get() ?: ""
        val nickname: String = nicknameField.get() ?: ""

        repository.isValidForm(id)



        return RegisterFormError.VALID
    }
}