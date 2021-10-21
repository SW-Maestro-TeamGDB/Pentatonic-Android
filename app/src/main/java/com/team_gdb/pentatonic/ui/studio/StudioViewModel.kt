package com.team_gdb.pentatonic.ui.studio

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.team_gdb.pentatonic.GetRecommendBandListQuery
import com.team_gdb.pentatonic.GetSongListQuery
import com.team_gdb.pentatonic.base.BaseViewModel
import com.team_gdb.pentatonic.network.applySchedulers
import com.team_gdb.pentatonic.repository.studio.StudioRepository
import io.reactivex.rxjava3.kotlin.subscribeBy
import timber.log.Timber

class StudioViewModel(val repository: StudioRepository) : BaseViewModel() {
    private val _currentPosition: MutableLiveData<Int> = MutableLiveData()
    val currentPosition: LiveData<Int>
        get() = _currentPosition

    fun setCurrentPosition(position: Int) {
        _currentPosition.value = position
    }

    fun getCurrentPosition() = currentPosition.value

    private val _songList: MutableLiveData<List<GetSongListQuery.QuerySong>> = MutableLiveData()
    val songList: LiveData<List<GetSongListQuery.QuerySong>>
        get() = _songList

    private val _recommendCoverList: MutableLiveData<List<GetRecommendBandListQuery.GetRecommendBand>> =
        MutableLiveData()
    val recommendCoverList: LiveData<List<GetRecommendBandListQuery.GetRecommendBand>>
        get() = _recommendCoverList

    fun getSongList() {
        val disposable =
            repository.getSongList()
                .applySchedulers()
                .subscribeBy(
                    onError = {
                        Timber.e(it)
                    },
                    onNext = {
                        Timber.d(it.data?.querySong.toString())
                        if (!it.hasErrors()) {
                            _songList.postValue(it.data?.querySong)
                        }
                    },
                    onComplete = {
                        Timber.d("getSongList() Complete")
                    }
                )
        addDisposable(disposable)
    }

    fun getRecommendCoverList() {
        val disposable =
            repository.getRecommendCoverList()
                .applySchedulers()
                .subscribeBy(
                    onError = {
                        Timber.e(it)
                    },
                    onNext = {
                        if (!it.hasErrors()) {
                            Timber.d(it.data?.getRecommendBand.toString())
                            _recommendCoverList.postValue(it.data?.getRecommendBand)
                        } else{
                            it.errors?.forEach {
                                Timber.e(it.message)
                            }
                        }
                    },
                    onComplete = {
                        Timber.d("getRecommendCoverList() Complete")
                    }
                )
        addDisposable(disposable)
    }
}