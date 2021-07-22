package com.team_gdb.pentatonic.repository.register

import android.provider.ContactsContract
import com.apollographql.apollo.rx3.rxQuery
import com.team_gdb.pentatonic.CheckIDQuery
import com.team_gdb.pentatonic.CheckNicknameQuery
import com.team_gdb.pentatonic.network.NetworkHelper.apolloClient
import com.team_gdb.pentatonic.type.CheckIdArgs
import com.team_gdb.pentatonic.type.CheckIdInput
import com.team_gdb.pentatonic.type.CheckUsernameArgs
import com.team_gdb.pentatonic.type.CheckUsernameInput
import com.team_gdb.pentatonic.ui.register.RegisterFormError
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber

class RegisterRepository {
    /**
     * 회원가입시 입력한 정보가 올바른지 검사함
     * @param id : 사용자의 ID
     * @param nickname : 사용자의 닉네임
     * @return 어떤 정보가 잘못 되었는지 반환 (아이디 오류, 닉네임 오류 등)
     */
    fun isValidForm(id: String, nickname: String): List<RegisterFormError> {
        val error = mutableListOf<RegisterFormError>()
        if (!isValidId(id)) {
            error.add(RegisterFormError.ID_INVALID)
        }
        if (!isValidNickname(nickname)) {
            error.add(RegisterFormError.NICKNAME_INVALID)
        }
        return error
    }

    /**
     * 사용자가 입력한 ID 가 올바른지 검사
     * @param id : 사용자의 ID
     * @return 올바른지 true, false 형태로 반환
     */
    private fun isValidId(id: String): Boolean {
        var result = true
        apolloClient.rxQuery(CheckIDQuery(CheckIdInput(CheckIdArgs(id))))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = {
                    if (it.data == null) {
                        result = false
                    }
                },
                onError = {
                    Timber.i(it)
                }
            )
        return result
    }

    /**
     * 사용자가 입력한 닉네임이 올바른지 검사
     * @param nickname : 사용자의 닉네임
     * @return 올바른지 true, false 형태로 반환
     */
    private fun isValidNickname(nickname: String): Boolean {
        var result = true
        apolloClient.rxQuery(CheckNicknameQuery(CheckUsernameInput(CheckUsernameArgs(nickname))))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = {
                    if (it.data == null) {
                        result = false
                    }
                },
                onError = {
                    Timber.i(it)
                }
            )
        return result
    }
}