package com.team_gdb.pentatonic.ui.weekly_challenge

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.team_gdb.pentatonic.GetSongInfoQuery
import com.team_gdb.pentatonic.GetWeeklyChallengeSongInfoQuery
import com.team_gdb.pentatonic.base.BaseViewModel
import com.team_gdb.pentatonic.data.model.SongEntity
import com.team_gdb.pentatonic.network.applySchedulers
import com.team_gdb.pentatonic.repository.weekly_challenge.WeeklyChallengeRepository
import io.reactivex.rxjava3.kotlin.subscribeBy
import timber.log.Timber

class WeeklyChallengeViewModel(private val repository: WeeklyChallengeRepository) :
    BaseViewModel() {

    private val _weeklyChallengeSongEntity: MutableLiveData<SongEntity> = MutableLiveData()
    val weeklyChallengeSongEntity: LiveData<SongEntity>
        get() = _weeklyChallengeSongEntity

    private val _weeklyChallengeSongImage: MutableLiveData<String> = MutableLiveData()
    val weeklyChallengeSongImage: LiveData<String>
        get() = _weeklyChallengeSongImage

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
                        val songEntity = SongEntity(
                            songId = songId,
                            songName = it.data?.getSong?.name!!,
                            artistName = it.data?.getSong?.artist!!,
                            albumReleaseDate = it.data?.getSong?.releaseDate!!,
                            albumName = it.data?.getSong?.album!!,
                            albumJacketImage = it.data?.getSong?.songImg!!,
                            isFreeSong = false,
                            isWeeklyChallenge = true,
                            songGenre = it.data?.getSong?.genre!!.rawValue,
                            songLevel = it.data?.getSong?.level!!,
                            songUrl = it.data?.getSong?.songURI!!
                        )
                        _weeklyChallengeSongEntity.postValue(songEntity)

                        _weeklyChallengeSongImage.postValue(it.data?.getSong?.songImg)
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