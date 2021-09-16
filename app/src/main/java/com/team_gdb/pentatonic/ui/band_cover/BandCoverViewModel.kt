package com.team_gdb.pentatonic.ui.band_cover

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import com.team_gdb.pentatonic.base.BaseViewModel
import com.team_gdb.pentatonic.data.model.UserEntity
import com.team_gdb.pentatonic.data.session.SessionSetting


class BandCoverViewModel : BaseViewModel() {
    val selectedSession: HashMap<String, String> = hashMapOf()
    val selectedSessionLiveData: MutableLiveData<HashMap<String, String>> = MutableLiveData()

    @RequiresApi(Build.VERSION_CODES.N)
    fun addSession(sessionName: String, userName: String){
        if (selectedSession.containsKey(sessionName)){  // 이미 있는 세션인 경우 Replace
            selectedSession.replace(sessionName, userName)
        } else{  // 새로운 세션인 경우 put
            selectedSession.put(sessionName, userName)
        }
        selectedSessionLiveData.postValue(selectedSession)
    }

}