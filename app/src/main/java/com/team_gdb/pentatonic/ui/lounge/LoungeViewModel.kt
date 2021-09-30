package com.team_gdb.pentatonic.ui.lounge

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.team_gdb.pentatonic.GetTrendBandsQuery
import com.team_gdb.pentatonic.base.BaseViewModel
import com.team_gdb.pentatonic.network.applySchedulers
import com.team_gdb.pentatonic.repository.lounge.LoungeRepository
import io.reactivex.rxjava3.kotlin.subscribeBy
import timber.log.Timber

class LoungeViewModel(val repository: LoungeRepository) : BaseViewModel() {

    private val _weeklyChallengeSongImage: MutableLiveData<String> =
        MutableLiveData()
    val weeklyChallengeSongImage: LiveData<String>
        get() = _weeklyChallengeSongImage

    private val _weeklyChallengeSongId: MutableLiveData<String> = MutableLiveData()
    val weeklyChallengeSongId: LiveData<String>
        get() = _weeklyChallengeSongId

    val weeklyChallengeSongName: MutableLiveData<String> = MutableLiveData()
    val weeklyChallengeSongArtist: MutableLiveData<String> = MutableLiveData()

    private val _trendBandCover: MutableLiveData<List<GetTrendBandsQuery.GetTrendBand>> =
        MutableLiveData()
    val trendBandsQuery: LiveData<List<GetTrendBandsQuery.GetTrendBand>>
        get() = _trendBandCover

    private val _userProfileImage: MutableLiveData<String> = MutableLiveData()
    val userProfileImage: MutableLiveData<String>
        get() = _userProfileImage

    fun getWeeklyChallengeSongId(): String = _weeklyChallengeSongId.value!!

    fun getWeeklyChallengeSongInfo() {
        val disposable = repository.getWeeklyChallengeSongInfo()
            .applySchedulers()
            .subscribeBy(
                onError = {
                    Timber.e(it)
                },
                onNext = {
                    if (!it.hasErrors()) {
                        Timber.d(it.data.toString())
                        _weeklyChallengeSongId.postValue(it.data?.querySong?.get(0)?.songId)
                        _weeklyChallengeSongImage.postValue(it.data?.querySong?.get(0)?.songImg)
                        weeklyChallengeSongName.postValue(it.data?.querySong?.get(0)?.name)
                        weeklyChallengeSongArtist.postValue(it.data?.querySong?.get(0)?.artist)
                    }
                },
                onComplete = {
                    Timber.d("getWeeklyChallengeSongInfo() Completed")
                }
            )
        addDisposable(disposable)
    }

    fun getTrendBands() {
        val disposable = repository.getTrendBands()
            .applySchedulers()
            .subscribeBy(
                onError = {
                    Timber.i(it)
                },
                onNext = {
                    if (!it.hasErrors()) {
                        Timber.d(it.data.toString())
                        _trendBandCover.postValue(it.data?.getTrendBands)
                    }
                },
                onComplete = {
                    Timber.d("getTrendBands() Complete")
                }
            )
        addDisposable(disposable)
    }

    fun getUserInfo(id: String) {
        val disposable = repository.getUserInfo(id)
            .applySchedulers()
            .subscribeBy(
                onError = {
                    Timber.i(it)
                },
                onNext = {
                    if (!it.hasErrors()) {
                        Timber.d(it.data?.getUserInfo.toString())
                        _userProfileImage.postValue(it.data?.getUserInfo?.profileURI)
                    } else {
                        it.errors?.forEach {
                            Timber.e(it.message)
                        }
                    }
                },
                onComplete = {
                    /* no-op */
                }
            )
        addDisposable(disposable)
    }
}