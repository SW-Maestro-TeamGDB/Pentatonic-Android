package com.team_gdb.pentatonic.ui.register

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.newidea.mcpestore.libs.base.BaseViewModel

class RegisterViewModel : BaseViewModel() {
    val id: ObservableField<String> = ObservableField("")
    val password: ObservableField<String> = ObservableField("")
    val passwordConfirm: ObservableField<String> = ObservableField("")
    val nickname: ObservableField<String> = ObservableField("")
    var userType: MutableLiveData<Int> = MutableLiveData<Int>()
}