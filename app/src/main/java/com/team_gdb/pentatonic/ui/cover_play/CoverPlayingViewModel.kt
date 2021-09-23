package com.team_gdb.pentatonic.ui.cover_play

import androidx.lifecycle.MutableLiveData
import com.team_gdb.pentatonic.GetCoverCommentQuery
import com.team_gdb.pentatonic.base.BaseViewModel
import com.team_gdb.pentatonic.custom_view.ButtonState
import com.team_gdb.pentatonic.data.model.CoverEntity
import com.team_gdb.pentatonic.data.model.CoverPlayEntity
import com.team_gdb.pentatonic.data.model.SongEntity
import com.team_gdb.pentatonic.network.applySchedulers
import com.team_gdb.pentatonic.repository.cover_play.CoverPlayRepository
import io.reactivex.rxjava3.kotlin.subscribeBy
import timber.log.Timber

class CoverPlayingViewModel(val repository: CoverPlayRepository) : BaseViewModel() {
    val coverEntity: MutableLiveData<CoverPlayEntity> = MutableLiveData()

    val commentList: MutableLiveData<List<GetCoverCommentQuery.GetComment>> = MutableLiveData()

    fun getComment(bandId: String) {
        val disposable = repository.getComment(bandId)
            .applySchedulers()
            .subscribeBy(
                onError = {
                    Timber.e(it)
                },
                onNext = {
                    if (!it.hasErrors()) {
                        Timber.d(it.data?.getComments.toString())
                        commentList.postValue(it.data?.getComments)
                    }
                },
                onComplete = {
                    Timber.d("getComment() Complete")
                }
            )
        addDisposable(disposable)
    }
}