package com.team_gdb.pentatonic.ui.whole_artist

import androidx.lifecycle.MutableLiveData
import com.team_gdb.pentatonic.GetUserListQuery
import com.team_gdb.pentatonic.base.BaseViewModel
import com.team_gdb.pentatonic.data.genre.Genre
import com.team_gdb.pentatonic.data.model.CoverEntity
import com.team_gdb.pentatonic.data.model.SessionData
import com.team_gdb.pentatonic.network.applySchedulers
import com.team_gdb.pentatonic.repository.whole_artist.WholeArtistRepository
import com.team_gdb.pentatonic.repository.whole_cover.WholeCoverRepository
import io.reactivex.rxjava3.kotlin.subscribeBy
import timber.log.Timber

class WholeArtistViewModel(val repository: WholeArtistRepository) : BaseViewModel() {

    private val _userList: MutableLiveData<List<GetUserListQuery.QueryUser?>> = MutableLiveData()
    val userList: MutableLiveData<List<GetUserListQuery.QueryUser?>>
        get() = _userList

    val content: MutableLiveData<String> = MutableLiveData("")

    fun getUserList() {
        val disposable = repository.getUserList(content.value!!)
            .applySchedulers()
            .subscribeBy(
                onError = {
                    Timber.e(it)
                },
                onNext = {
                    if (!it.hasErrors()){
                        Timber.d(it.data?.queryUser.toString())
                        _userList.postValue(it.data?.queryUser)
                    }
                },
                onComplete = {

                }
            )
        addDisposable(disposable)
    }
}

