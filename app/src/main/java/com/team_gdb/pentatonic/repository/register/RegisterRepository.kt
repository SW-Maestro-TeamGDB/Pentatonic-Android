package com.team_gdb.pentatonic.repository.register

import androidx.lifecycle.MutableLiveData
import com.apollographql.apollo.rx3.rxQuery
import com.team_gdb.pentatonic.CheckIDQuery
import com.team_gdb.pentatonic.CheckNicknameQuery
import com.team_gdb.pentatonic.network.NetworkHelper.apolloClient
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber

interface RegisterRepository {
    val checkCompleteEvent: MutableLiveData<MutableList<Boolean>>
    val isValidId: MutableLiveData<Boolean>
    val isValidNickname: MutableLiveData<Boolean>

    fun isValidForm(id: String, nickname: String)
    fun isValidIdQuery(id: String)
    fun isValidNicknameQuery(nickname: String)
}