package com.team_gdb.pentatonic.ui.my_page

import androidx.lifecycle.MutableLiveData
import com.team_gdb.pentatonic.GetUserInfoQuery
import com.team_gdb.pentatonic.base.BaseViewModel
import com.team_gdb.pentatonic.repository.my_page.MyPageRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber


/**
 * MyPageFragment, LibraryFragment 이 SharedViewModel() 형태로 LiveData 공유
 * - 유저의 프로필 정보와, 라이브러리 데이터를 갖게 됨
 *
 * @property repository : GetUserInfo() 쿼리 수행
 */
class MyPageViewModel(val repository: MyPageRepository) : BaseViewModel() {
    val userName: MutableLiveData<String> = MutableLiveData()
    val userFollowerCount: MutableLiveData<String> = MutableLiveData()
    val userIntroduce: MutableLiveData<String> = MutableLiveData()

    val libraryList: MutableLiveData<List<GetUserInfoQuery.Library>> =
        MutableLiveData()  // 라이브러리 리스트 정보

    fun getUserInfo(id: String) {
        val disposable =
            repository.getUserInfo(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onError = {
                        Timber.i(it)
                    },
                    onNext = {
                        if (!it.hasErrors()) {
                            Timber.d(it.data?.getUserInfo.toString())
                            userName.postValue(it.data?.getUserInfo?.username.toString())
                            userFollowerCount.postValue(it.data?.getUserInfo?.followerCount.toString())
                            userIntroduce.postValue(it.data?.getUserInfo?.introduce.toString())

                            // LibraryFragment 에서 참조할 데이터 Library List postValue()
                            libraryList.postValue(it.data?.getUserInfo?.library)
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