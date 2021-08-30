package com.team_gdb.pentatonic.ui.my_page

import com.team_gdb.pentatonic.base.BaseViewModel
import com.team_gdb.pentatonic.repository.my_page.MyPageRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber


class MyPageViewModel(val repository: MyPageRepository) : BaseViewModel() {

    fun getUserInfo(id: String) {
        val disposable =
            repository.getUserInfo(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onError = {

                    },
                    onNext = {
                        if (!it.hasErrors()) {
                            Timber.d(it.data?.getUserInfo.toString())
                        } else {
                            it.errors?.forEach {
                                Timber.e(it.message)
                            }
                        }
                    },
                    onComplete = {

                    }
                )
        addDisposable(disposable)
    }
}