package com.team_gdb.pentatonic.ui.select_library

import androidx.lifecycle.MutableLiveData
import com.team_gdb.pentatonic.GetUserLibraryQuery
import com.team_gdb.pentatonic.base.BaseViewModel
import com.team_gdb.pentatonic.data.model.SessionSettingEntity
import com.team_gdb.pentatonic.network.applySchedulers
import com.team_gdb.pentatonic.repository.select_library.SelectLibraryRepository
import com.team_gdb.pentatonic.util.Event
import io.reactivex.rxjava3.kotlin.subscribeBy
import timber.log.Timber

class SelectLibraryViewModel(val repository: SelectLibraryRepository): BaseViewModel() {

    // 사용자가 선택한 라이브러리 커버 URL
    val selectedUserCoverID: MutableLiveData<String> = MutableLiveData()

    // 사용자의 라이브러리 목록
    val libraryList: MutableLiveData<List<GetUserLibraryQuery.Library>> = MutableLiveData()


    val createBandComplete: MutableLiveData<Event<String>> = MutableLiveData()
    val joinBandComplete: MutableLiveData<Event<Boolean>> = MutableLiveData()

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



    /**
     * 솔로 커버 생성
     *
     * @param sessionName       세션명
     * @param bandName          커버명
     * @param bandIntroduction  밴드 소개
     * @param backgroundUrl     대표 이미지
     * @param songId            원곡 ID
     */
    fun createBand(
        sessionConfig: List<SessionSettingEntity>,
        bandName: String,
        bandIntroduction: String,
        backgroundUrl: String,
        songId: String
    ) {
        val disposable =
            repository.createBand(sessionConfig, bandName, bandIntroduction, backgroundUrl, songId)
                .applySchedulers()
                .subscribeBy(
                    onError = {
                        Timber.e(it)
                    },
                    onSuccess = {
                        if (!it.hasErrors()) {
                            Timber.d(it.data?.createBand?.bandId)
                            createBandComplete.postValue(Event(it.data?.createBand!!.bandId))
                        }
                    }
                )
        addDisposable(disposable)
    }

    /**
     * Band ID, Cover ID, 세션명을 통해 사용자의 라이브러리 커버 기반으로 밴드 참여
     */
    fun joinBand(sessionName: String, bandId: String, coverId: String) {
        val disposable = repository.joinBand(
            bandId = bandId,
            coverId = coverId,
            sessionName = sessionName
        )
            .applySchedulers()
            .subscribeBy(
                onError = {
                    Timber.i(it)
                },
                onSuccess = {
                    if (!it.hasErrors()) {
                        Timber.d(it.data?.joinBand.toString())
                        joinBandComplete.postValue(Event(true))
                    }
                }
            )
        addDisposable(disposable)
    }
}