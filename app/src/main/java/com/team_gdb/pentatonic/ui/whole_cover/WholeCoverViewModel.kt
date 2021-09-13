package com.team_gdb.pentatonic.ui.whole_cover

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.apollographql.apollo.api.Response
import com.team_gdb.pentatonic.GetCoverQuery
import com.team_gdb.pentatonic.base.BaseViewModel
import com.team_gdb.pentatonic.data.model.CoverEntity
import com.team_gdb.pentatonic.data.model.SessionData
import com.team_gdb.pentatonic.data.model.UserEntity
import com.team_gdb.pentatonic.network.applySchedulers
import com.team_gdb.pentatonic.repository.whole_cover.WholeCoverRepository
import io.reactivex.rxjava3.kotlin.subscribeBy
import timber.log.Timber

class WholeCoverViewModel(val repository: WholeCoverRepository) : BaseViewModel() {

    val coverList: MutableLiveData<List<CoverEntity>> = MutableLiveData()

    fun getCover(query: String) {
        val disposable = repository.getCover(query)
            .applySchedulers()
            .subscribeBy(
                onError = {
                    Timber.i(it)
                },
                onNext = {
                    if (!it.hasErrors()) {
                        coverList.postValue(it.data?.queryBand?.map {
                            CoverEntity(
                                id = 0,
                                coverName = it.name,
                                introduction = it.introduce,
                                imageUrl = it.backGroundURI,
                                sessionDataList = listOf(),
                                like = it.likeCount,
                                view = 50,
                                originalSong = "${it.song.artist} - ${it.song.name}"
                            )
                        })
                    }
                },
                onComplete = {
                    Timber.d("Query Cover Complete")
                }
            )

        addDisposable(disposable)
    }

}

