package com.team_gdb.pentatonic.ui.band_cover

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import com.team_gdb.pentatonic.base.BaseViewModel


class BandCoverViewModel : BaseViewModel() {
    private val selectedSession: HashMap<String, String> = hashMapOf()
    val selectedSessionLiveData: MutableLiveData<HashMap<String, String>> = MutableLiveData()

    @RequiresApi(Build.VERSION_CODES.N)
    fun addSession(sessionName: String, userName: String) {
        if (selectedSession.containsKey(sessionName)) {  // 이미 있는 세션인 경우
            if (selectedSession[sessionName] == userName) {  // 같은 사람에 대한 요청인 경우 세션 정보 자체를 제거
                selectedSession.remove(sessionName)
            } else {
                selectedSession.replace(sessionName, userName)  // 일반적인 경우 유저 정보를 replace
            }
        } else {  // 새로운 세션인 경우 put
            selectedSession[sessionName] = userName
        }
        selectedSessionLiveData.postValue(selectedSession)
    }

}