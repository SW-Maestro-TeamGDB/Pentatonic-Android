package com.team_gdb.pentatonic.repository.register

import androidx.lifecycle.MutableLiveData
import com.apollographql.apollo.rx3.rxQuery
import com.team_gdb.pentatonic.CheckIDAndUsernameQuery
import com.team_gdb.pentatonic.network.NetworkHelper.apolloClient
import com.team_gdb.pentatonic.util.Event
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber

class RegisterRepositoryImpl : RegisterRepository {
    override val completeCheckValidation: MutableLiveData<Event<Array<Boolean>>> =
        MutableLiveData()

    /**
     * 회원가입시 입력한 정보가 올바른지 검사함
     * @param id        사용자의 ID
     * @param nickname  사용자의 닉네임
     */
    override fun isValidForm(id: String, nickname: String) {
        Timber.i("isValidForm() Called with id = $id, nickname = $nickname")
        var eventResult = arrayOf(false, false)  // 각각 ID, 닉네임 유효성
        apolloClient.rxQuery(
            CheckIDAndUsernameQuery(
                isValidIdId = id,
                isValidUsernameUsername = nickname
            )
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = {
                    Timber.d(it.data.toString())
                    Timber.d(it.data?.isValidId.toString())
                    Timber.d(it.data?.isValidUsername.toString())
                    if (it.data?.isValidId == true) {
                        eventResult[0] = true
                    }
                    if (it.data?.isValidUsername == true) {
                        eventResult[1] = true
                    }
                },
                onComplete = {
                    completeCheckValidation.value = Event(eventResult)
                },
                onError = {
                    Timber.e(it)
                }
            )
    }
}