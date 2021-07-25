package com.team_gdb.pentatonic.repository.register

import androidx.lifecycle.MutableLiveData
import com.apollographql.apollo.rx3.rxQuery
import com.team_gdb.pentatonic.CheckIDQuery
import com.team_gdb.pentatonic.CheckNicknameQuery
import com.team_gdb.pentatonic.network.NetworkHelper.apolloClient
import com.team_gdb.pentatonic.type.CheckIdArgs
import com.team_gdb.pentatonic.type.CheckIdInput
import com.team_gdb.pentatonic.type.CheckUsernameArgs
import com.team_gdb.pentatonic.type.CheckUsernameInput
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber

interface RegisterRepository {
    fun isValidForm(id: String, nickname: String)
    fun isValidId(id: String) fun isValidNickname(nickname: String)
}