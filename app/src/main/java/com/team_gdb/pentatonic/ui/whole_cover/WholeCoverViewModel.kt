package com.team_gdb.pentatonic.ui.whole_cover

import androidx.lifecycle.MutableLiveData
import com.team_gdb.pentatonic.base.BaseViewModel
import com.team_gdb.pentatonic.data.genre.Genre
import com.team_gdb.pentatonic.data.model.CoverEntity
import com.team_gdb.pentatonic.data.model.SessionData
import com.team_gdb.pentatonic.network.applySchedulers
import com.team_gdb.pentatonic.repository.whole_cover.WholeCoverRepository
import io.reactivex.rxjava3.kotlin.subscribeBy
import timber.log.Timber

class WholeCoverViewModel(val repository: WholeCoverRepository) : BaseViewModel() {

    val coverList: MutableLiveData<List<CoverEntity>> = MutableLiveData()

    val content: MutableLiveData<String> = MutableLiveData()
    val genre: MutableLiveData<Genre> = MutableLiveData()
    val level: MutableLiveData<Int> = MutableLiveData()

    fun getCover() {
        val disposable = repository.queryBandList(
            content = content.value ?: "",
            genre = genre.value,
            level = level.value
        )
            .applySchedulers()
            .subscribeBy(
                onError = {
                    Timber.i(it)
                },
                onNext = {
                    if (!it.hasErrors()) {
                        coverList.postValue(it.data?.queryBand?.bands!!.map {
                            val sessionDataList = it?.session?.map {
                                SessionData(
                                    sessionName = it?.position?.rawValue!!,
                                    sessionMaxSize = it.maxMember,
                                    currentParticipant = it.cover!!.size
                                )
                            }
                            CoverEntity(
                                id = it!!.bandId,
                                coverName = it.name,
                                introduction = it.introduce,
                                imageURL = it.backGroundURI,
                                sessionDataList = sessionDataList!!,
                                like = it.likeCount,
                                view = it.viewCount,
                                originalSong = "${it.song.name} - ${it.song.artist}"
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

