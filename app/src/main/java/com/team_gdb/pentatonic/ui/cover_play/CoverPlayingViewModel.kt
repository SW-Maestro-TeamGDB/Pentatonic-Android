package com.team_gdb.pentatonic.ui.cover_play

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.team_gdb.pentatonic.GetCoverCommentQuery
import com.team_gdb.pentatonic.base.BaseViewModel
import com.team_gdb.pentatonic.custom_view.ButtonState
import com.team_gdb.pentatonic.data.model.CoverEntity
import com.team_gdb.pentatonic.data.model.CoverPlayEntity
import com.team_gdb.pentatonic.data.model.SongEntity
import com.team_gdb.pentatonic.network.applySchedulers
import com.team_gdb.pentatonic.repository.cover_play.CoverPlayRepository
import com.team_gdb.pentatonic.util.Event
import io.reactivex.rxjava3.kotlin.subscribeBy
import timber.log.Timber

class CoverPlayingViewModel(val repository: CoverPlayRepository) : BaseViewModel() {
    val coverEntity: MutableLiveData<CoverPlayEntity> = MutableLiveData()

    private val _commentList: MutableLiveData<List<GetCoverCommentQuery.GetComment>> = MutableLiveData()
    val commentList: LiveData<List<GetCoverCommentQuery.GetComment>>
        get() = _commentList

    // EditText 와 양방향 데이터 바인딩
    val commentContent: MutableLiveData<String> = MutableLiveData()

    private val _createCommentComplete: MutableLiveData<Event<Boolean>> = MutableLiveData()
    val createCommentComplete: LiveData<Event<Boolean>>
        get() = _createCommentComplete

    fun getComment(coverId: String) {
        val disposable = repository.getComment(coverId)
            .applySchedulers()
            .subscribeBy(
                onError = {
                    Timber.e(it)
                },
                onNext = {
                    if (!it.hasErrors()) {
                        Timber.d(it.data?.getComments.toString())
                        _commentList.value = it.data?.getComments
                    }
                },
                onComplete = {
                    Timber.d("getComment() Complete")
                }
            )
        addDisposable(disposable)
    }

    fun createComment(coverId: String) {
        val disposable = repository.createComment(coverEntity.value!!.coverID, commentContent.value!!)
            .applySchedulers()
            .subscribeBy(
                onError = {
                    Timber.e(it)
                },
                onSuccess = {
                    if (!it.hasErrors()) {
                        Timber.d(it.data?.createComment.toString())
                    }
                }
            )
        addDisposable(disposable)
    }
}