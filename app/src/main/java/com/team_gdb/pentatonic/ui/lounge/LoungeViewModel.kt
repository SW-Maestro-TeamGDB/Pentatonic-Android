package com.team_gdb.pentatonic.ui.lounge

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.fragment.findNavController
import com.team_gdb.pentatonic.GetTrendBandsQuery
import com.team_gdb.pentatonic.R
import com.team_gdb.pentatonic.base.BaseViewModel
import com.team_gdb.pentatonic.network.applySchedulers
import com.team_gdb.pentatonic.repository.lounge.LoungeRepository
import io.reactivex.rxjava3.kotlin.subscribeBy
import timber.log.Timber

class LoungeViewModel(val repository: LoungeRepository) : BaseViewModel() {

    val trendBandCover: MutableLiveData<List<GetTrendBandsQuery.GetTrendBand>> = MutableLiveData()

    private val _userProfileImage: MutableLiveData<String> = MutableLiveData()
    val userProfileImage: MutableLiveData<String>
        get() = _userProfileImage

    fun getTrendBands() {
        val disposable = repository.getTrendBands()
            .applySchedulers()
            .subscribeBy(
                onError = {
                    Timber.i(it)
                },
                onNext = {
                    if (!it.hasErrors()) {
                        Timber.d(it.data.toString())
                        trendBandCover.postValue(it.data?.getTrendBands)
                    }
                },
                onComplete = {
                    Timber.d("getTrendBands() Complete")
                }
            )
        addDisposable(disposable)
    }

    fun getUserInfo(id: String) {
        val disposable = repository.getUserInfo(id)
            .applySchedulers()
            .subscribeBy(
                onError = {
                    Timber.i(it)
                },
                onNext = {
                    if (!it.hasErrors()) {
                        Timber.d(it.data?.getUserInfo.toString())
                        _userProfileImage.postValue(it.data?.getUserInfo?.profileURI)
                    } else {
                        it.errors?.forEach {
                            Timber.e(it.message)
                        }
                    }
                },
                onComplete = {
                    /* no-op */
                }
            )
        addDisposable(disposable)
    }
}