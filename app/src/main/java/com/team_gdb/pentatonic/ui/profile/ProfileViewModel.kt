package com.team_gdb.pentatonic.ui.profile

import androidx.lifecycle.MutableLiveData
import com.team_gdb.pentatonic.GetUserInfoQuery
import com.team_gdb.pentatonic.base.BaseViewModel
import com.team_gdb.pentatonic.network.applySchedulers
import com.team_gdb.pentatonic.repository.profile.ProfileRepository
import io.reactivex.rxjava3.kotlin.subscribeBy
import timber.log.Timber

class ProfileViewModel(val repository: ProfileRepository) : BaseViewModel() {

    val userData: MutableLiveData<GetUserInfoQuery.GetUserInfo> = MutableLiveData()

    fun getUserInfo(userId: String) {
        repository.getUserInfo(userId)
            .applySchedulers()
            .subscribeBy(
                onError = {
                    Timber.i(it)
                },
                onNext = {
                    Timber.d(it.data.toString())
                    if (!it.hasErrors()) {
                        userData.postValue(it.data?.getUserInfo)
                    }
                    it.errors?.forEach {
                        Timber.i(it.message)
                    }
                },
                onComplete = {
                    Timber.d("getUserInfo() Completed")
                }
            )
    }
}