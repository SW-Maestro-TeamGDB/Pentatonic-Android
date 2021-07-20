package com.team_gdb.pentatonic.repository.register

import android.util.Log
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.api.toJson
import com.apollographql.apollo.exception.ApolloException
import com.apollographql.apollo.rx3.rxQuery
import com.team_gdb.pentatonic.CheckIDQuery
import com.team_gdb.pentatonic.network.NetworkHelper.apolloClient
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber

class RegisterRepository {
    fun isValidForm(id: String): Boolean {
        apolloClient.rxQuery(CheckIDQuery(id))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = {
                    Timber.d(it.data!!.toJson())
                },
                onComplete = {

                },
                onError = {
                    Timber.i(it)
                }
            )
        return true
    }
}