package com.team_gdb.pentatonic.ui.weekly_challenge

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.team_gdb.pentatonic.GetSongInfoQuery
import com.team_gdb.pentatonic.GetWeeklyChallengeSongInfoQuery
import com.team_gdb.pentatonic.base.BaseViewModel
import com.team_gdb.pentatonic.network.applySchedulers
import com.team_gdb.pentatonic.repository.weekly_challenge.WeeklyChallengeRepository
import io.reactivex.rxjava3.kotlin.subscribeBy
import timber.log.Timber

class WeeklyChallengeViewModel(private val repository: WeeklyChallengeRepository) :
    BaseViewModel() {

    private val _weeklyChallengeSongImage: MutableLiveData<String> = MutableLiveData()
    val weeklyChallengeSongImage: LiveData<String>
        get() = _weeklyChallengeSongImage

    val weeklyChallengeSongName: MutableLiveData<String> = MutableLiveData()
    val weeklyChallengeSongArtist: MutableLiveData<String> = MutableLiveData()

    private val _weeklyChallengeCoverList: MutableLiveData<List<GetSongInfoQuery.Band>> =
        MutableLiveData()
    val weeklyChallengeCoverList: LiveData<List<GetSongInfoQuery.Band>>
        get() = _weeklyChallengeCoverList

    fun getWeeklyChallengeSongInfo(songId: String) {
        val disposable = repository.getWeeklyChallengeSongInfo(songId)
            .applySchedulers()
            .subscribeBy(
                onError = {
                    Timber.e(it)
                },
                onNext = {
                    if (!it.hasErrors()) {
                        Timber.d(it.data.toString())
                        _weeklyChallengeSongImage.postValue(it.data?.getSong?.songImg)
                        weeklyChallengeSongName.postValue(it.data?.getSong?.name)
                        weeklyChallengeSongArtist.postValue(it.data?.getSong?.artist)
                        _weeklyChallengeCoverList.postValue(it.data?.getSong?.band)
                    }
                },
                onComplete = {
                    Timber.d("getWeeklyChallengeSongInfo() Completed")
                }
            )
        addDisposable(disposable)
    }

}