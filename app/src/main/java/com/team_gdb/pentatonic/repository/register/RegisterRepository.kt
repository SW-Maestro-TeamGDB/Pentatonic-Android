package com.team_gdb.pentatonic.repository.register

import androidx.lifecycle.MutableLiveData
import com.team_gdb.pentatonic.util.Event

interface RegisterRepository {
    val completeCheckValidation: MutableLiveData<Event<Array<Boolean>>>

    fun isValidForm(id: String, nickname: String)
}