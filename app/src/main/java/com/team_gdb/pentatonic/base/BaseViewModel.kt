package com.newidea.mcpestore.libs.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable

open class BaseViewModel : ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

    // 현재 ViewModel에서 진행하고 있는 작업이 있는지에 대한 여부
    val shouldUIinProgress = MutableLiveData<Boolean>(false)
    private val registeredWorks = mutableMapOf<Long, Boolean>()

    fun createUIProgressWork(): Long {
        val key = System.currentTimeMillis()
        registeredWorks[key] = true
        syncProgress()
        return key
    }

    /**
     * 해당 작업이 완료되었음을 보고함 -> LiveData 업데이트
     * @param disposable
     */
    fun reportProgressIsDone(key: Long) {
        registeredWorks[key] = false
        syncProgress()
    }

    /**
     * LiveData 업데이트
     */
    private fun syncProgress() {
        registeredWorks.values.removeAll { !it }
        shouldUIinProgress.value = registeredWorks.isNotEmpty()
    }
}