package com.team_gdb.pentatonic.ui.studio

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.team_gdb.pentatonic.base.BaseViewModel
import com.team_gdb.pentatonic.repository.studio.StudioRepository

class StudioViewModel(val repository: StudioRepository) : BaseViewModel() {
    private val _currentPosition: MutableLiveData<Int> = MutableLiveData()

    val currentPosition: LiveData<Int>
        get() = _currentPosition


    fun setCurrentPosition(position: Int){
        _currentPosition.value = position
    }

    fun getCurrentPosition() = currentPosition.value
}