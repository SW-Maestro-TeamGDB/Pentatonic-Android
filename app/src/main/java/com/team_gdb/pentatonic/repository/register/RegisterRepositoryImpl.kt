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

class RegisterRepositoryImpl : RegisterRepository {
    // Boolean List 로, ID 검사와 닉네임 검사가 모두 일어나면 두 개 원소가 모두 true 를 담음
    val checkCompleteEvent: MutableLiveData<MutableList<Boolean>> =
        MutableLiveData<MutableList<Boolean>>(mutableListOf())

    val isValidId: MutableLiveData<Boolean> = MutableLiveData<Boolean>(true)
    val isValidNickname: MutableLiveData<Boolean> = MutableLiveData<Boolean>(true)

    /**
     * 회원가입시 입력한 정보가 올바른지 검사함
     * @param id : 사용자의 ID
     * @param nickname : 사용자의 닉네임
     */
    override fun isValidForm(id: String, nickname: String) {
        isValidId(id)
        isValidNickname(nickname)
    }

    /**
     * 사용자가 입력한 ID 가 올바른지 검사
     * @param id : 사용자의 ID
     * @return : 올바른지 true, false 형태로 반환
     */
    override fun isValidId(id: String) {
        apolloClient.rxQuery(CheckIDQuery(CheckIdInput(CheckIdArgs(id))))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = {
                    isValidId.value = it.data != null && it.data!!.checkId == true
                    Timber.d(isValidId.value.toString())
                },
                onComplete = {
                    checkCompleteEvent.value?.add(true)
                    checkCompleteEvent.value = checkCompleteEvent.value
                    Timber.d(checkCompleteEvent.value.toString())
                },
                onError = {
                    Timber.i(it)
                }
            )
    }

    /**
     * 사용자가 입력한 닉네임이 올바른지 검사
     * @param nickname : 사용자의 닉네임
     * @return : 올바른지 true, false 형태로 반환
     */
    override fun isValidNickname(nickname: String) {
        apolloClient.rxQuery(CheckNicknameQuery(CheckUsernameInput(CheckUsernameArgs(nickname))))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = {
                    isValidNickname.value = it.data != null && it.data!!.checkUsername == true
                    Timber.d(isValidId.value.toString())
                },
                onComplete = {
                    checkCompleteEvent.value?.add(true)
                    checkCompleteEvent.value = checkCompleteEvent.value
                    Timber.d(checkCompleteEvent.value.toString())
                },
                onError = {
                    Timber.i(it)
                }
            )
    }
}