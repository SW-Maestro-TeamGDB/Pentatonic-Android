package com.team_gdb.pentatonic.ui.select_library

import androidx.lifecycle.MutableLiveData
import com.team_gdb.pentatonic.GetUserLibraryQuery
import com.team_gdb.pentatonic.base.BaseViewModel
import com.team_gdb.pentatonic.network.applySchedulers
import com.team_gdb.pentatonic.repository.select_library.SelectLibraryRepository
import io.reactivex.rxjava3.kotlin.subscribeBy
import timber.log.Timber

class SelectLibraryViewModel(val repository: SelectLibraryRepository): BaseViewModel() {

    // 사용자가 선택한 라이브러리 커버 URL
    val selectedUserCoverID: MutableLiveData<String> = MutableLiveData()

    // 사용자의 라이브러리 목록
    val libraryList: MutableLiveData<List<GetUserLibraryQuery.Library>> = MutableLiveData()

    /**
     * 사용자가 가지고 있는 라이브러리 커버 쿼리
     * */
    fun getUserLibrary(userId: String, songId: String) {
        val disposable = repository.getUserLibrary(userId)
            .applySchedulers()
            .subscribeBy(
                onError = {
                    Timber.i(it)
                },
                onNext = {
                    if (!it.hasErrors()) {
                        // 가지고 있는 라이브러리들 중, 해당 커버와 같은 곡을 커버한 것만 필터링
                        libraryList.postValue(it.data?.getUserInfo?.library?.filter { library ->
                            songId == library.song.songId
                        })
                    }
                },
                onComplete = {
                    Timber.d("getUserLibrary() Complete")
                }
            )
        addDisposable(disposable)
    }
}