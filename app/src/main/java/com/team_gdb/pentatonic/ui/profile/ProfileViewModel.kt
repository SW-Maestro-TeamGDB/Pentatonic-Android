package com.team_gdb.pentatonic.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.team_gdb.pentatonic.GetUserInfoQuery
import com.team_gdb.pentatonic.base.BaseViewModel
import com.team_gdb.pentatonic.network.applySchedulers
import com.team_gdb.pentatonic.repository.profile.ProfileRepository
import io.reactivex.rxjava3.kotlin.subscribeBy
import timber.log.Timber

class ProfileViewModel(val repository: ProfileRepository) : BaseViewModel() {

    val userData: MutableLiveData<GetUserInfoQuery.GetUserInfo> = MutableLiveData()

    private val _coverHistoryList: MutableLiveData<List<GetUserInfoQuery.Band>> =
        MutableLiveData()  // 커버 (밴드 참여) 히스토리 정보
    val coverHistoryList: LiveData<List<GetUserInfoQuery.Band>>
        get() = _coverHistoryList

    private val _positionRankingList: MutableLiveData<List<GetUserInfoQuery.Position?>> =
        MutableLiveData()  // 포지션 정보
    val positionRankingList: LiveData<List<GetUserInfoQuery.Position?>>
        get() = _positionRankingList

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

                        // 해당 사용자가 참여한 커버 히스토리 ViewModel
                        _coverHistoryList.postValue(it.data?.getUserInfo?.band)

                        // 해당 사용자의 포지션 랭킹 정보
                        _positionRankingList.postValue(it.data?.getUserInfo?.position)
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