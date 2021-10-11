package com.team_gdb.pentatonic.ui.artist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.team_gdb.pentatonic.GetRankedBandListQuery
import com.team_gdb.pentatonic.GetRankedUserListQuery
import com.team_gdb.pentatonic.base.BaseViewModel
import com.team_gdb.pentatonic.network.applySchedulers
import com.team_gdb.pentatonic.repository.artist.ArtistRepository
import com.team_gdb.pentatonic.repository.studio.StudioRepository
import io.reactivex.rxjava3.kotlin.subscribeBy
import timber.log.Timber

class ArtistViewModel(val repository: ArtistRepository) : BaseViewModel() {
    private val _currentPosition: MutableLiveData<Int> = MutableLiveData()
    val currentPosition: LiveData<Int>
        get() = _currentPosition

    private val _rankedCoverList: MutableLiveData<List<GetRankedBandListQuery.GetRankedBand>> =
        MutableLiveData()
    val rankedCoverList: LiveData<List<GetRankedBandListQuery.GetRankedBand>>
        get() = _rankedCoverList

    private val _rankedUserList: MutableLiveData<List<GetRankedUserListQuery.GetRankedUser>> =
        MutableLiveData()
    val rankedUserList: LiveData<List<GetRankedUserListQuery.GetRankedUser>>
        get() = _rankedUserList

    fun setCurrentPosition(position: Int) {
        _currentPosition.value = position
    }

    fun getCurrentPosition() = currentPosition.value

    fun getRankedCoverList() {
        val disposable =
            repository.getRankedCoverList().applySchedulers()
                .subscribeBy(
                    onError = {
                        Timber.i(it)
                    },
                    onNext = {
                        Timber.d(it.data?.getRankedBands.toString())
                        _rankedCoverList.value = it.data?.getRankedBands
                    },
                    onComplete = {
                        Timber.d("getRankedCoverList Complete!")
                    }
                )
        addDisposable(disposable)
    }

    fun getRankedUserList() {
        val disposable =
            repository.getRankedUserList().applySchedulers()
                .subscribeBy(
                    onError = {
                        Timber.i(it)
                    },
                    onNext = {
                        Timber.d(it.data?.getRankedUser.toString())
                        _rankedUserList.value = it.data?.getRankedUser
                    },
                    onComplete = {
                        Timber.d("getRankedUserList Complete!")
                    }
                )
        addDisposable(disposable)
    }
}