package com.team_gdb.pentatonic.ui.cover_view

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import com.team_gdb.pentatonic.GetBandCoverInfoQuery
import com.team_gdb.pentatonic.GetSongInstrumentQuery
import com.team_gdb.pentatonic.GetUserLibraryQuery
import com.team_gdb.pentatonic.base.BaseViewModel
import com.team_gdb.pentatonic.network.applySchedulers
import com.team_gdb.pentatonic.repository.cover_view.CoverViewRepository
import com.team_gdb.pentatonic.util.Event
import io.reactivex.rxjava3.kotlin.subscribeBy
import timber.log.Timber


class CoverViewViewModel(val repository: CoverViewRepository) : BaseViewModel() {
    val bandInfo: MutableLiveData<GetBandCoverInfoQuery.GetBand> = MutableLiveData()

    val instList: MutableLiveData<List<GetSongInstrumentQuery.Instrument>> = MutableLiveData()

    private val selectedSession: HashMap<String, String> = hashMapOf()

    // 세션 to 커버 URL 로 구성된 HashMap
    val selectedSessionLiveData: MutableLiveData<HashMap<String, String>> = MutableLiveData()

    // 사용자의 세션 구성 선택대로 병합된 커버 URL
    val mergedCoverURL: MutableLiveData<String> = MutableLiveData()

    // 사용자의 라이브러리 목록
    val libraryList: MutableLiveData<List<GetUserLibraryQuery.Library>> = MutableLiveData()

    // 사용자가 선택한 라이브러리 커버 URL
    val selectedUserCoverID: MutableLiveData<String> = MutableLiveData()

    // 밴드 참여 이벤트
    val joinBandEvent: MutableLiveData<Event<Boolean>> = MutableLiveData()

    // 밴드 삭제 이벤트
    val deleteBandEvent: MutableLiveData<Event<Boolean>> = MutableLiveData()

    // 커버 삭제 이벤트
    val deleteCoverEvent: MutableLiveData<Event<Boolean>> = MutableLiveData()

    /**
     *  해당 밴드의 상세 정보를 가져오는 쿼리
     *
     * @param bandId  해당 밴드의 고유 ObjectID
     */
    fun getBandInfoQuery(bandId: String) {
        val disposable = repository.getBandCoverInfo(bandId)
            .applySchedulers()
            .subscribeBy(
                onError = {
                    Timber.i(it)
                },
                onNext = {
                    if (!it.hasErrors()) {
                        bandInfo.postValue(it.data?.getBand)
                    } else {
                        it.errors?.forEach {
                            Timber.e(it.message)
                        }
                    }
                },
                onComplete = {
                    Timber.d("getBandInfoQuery() Complete")
                }
            )
        addDisposable(disposable)
    }

    /**
     * CoverURL 목록을 통해 병합된 음원을 요청하는 뮤테이션
     *
     * @param coverList  병합할 커버 리스트 (URL 형태)
     */
    fun getMergedCover() {
        val coverList = selectedSession.values.toList()
        val disposable = repository.getMergedCover(coverList)
            .applySchedulers()
            .subscribeBy(
                onError = {
                    Timber.i(it)
                },
                onSuccess = {
                    if (!it.hasErrors()) {
                        Timber.d("getMergedCover() : ${it.data}")
                        mergedCoverURL.postValue(it.data?.mergeAudios)
                    } else {
                        Timber.i(it.errors.toString())
                    }
                }
            )
        addDisposable(disposable)
    }


    /**
     * 밴드에 참여시킬 세션 데이터를 저장
     */
    @RequiresApi(Build.VERSION_CODES.N)
    fun addSession(sessionName: String, coverURL: String) {
        if (selectedSession.containsKey(sessionName)) {  // 이미 있는 세션인 경우
            if (selectedSession[sessionName] == coverURL) {  // 같은 사람에 대한 요청인 경우 세션 정보 자체를 제거
                selectedSession.remove(sessionName)
            } else {
                selectedSession.replace(sessionName, coverURL)  // 일반적인 경우 커버 URL replace
            }
        } else {  // 새로운 세션인 경우 put
            selectedSession[sessionName] = coverURL
        }
        selectedSessionLiveData.postValue(selectedSession)
    }

    fun clearSession() {
        selectedSession.clear()
        selectedSessionLiveData.postValue(selectedSession)
    }


    /**
     * 사용자가 가지고 있는 라이브러리 커버 쿼리
     * */
    fun getUserLibrary(userId: String) {
        val disposable = repository.getUserLibrary(userId)
            .applySchedulers()
            .subscribeBy(
                onError = {
                    Timber.i(it)
                },
                onNext = {
                    if (!it.hasErrors()) {
                        // 가지고 있는 라이브러리들 중, 해당 밴드 커버와 같은 곡을 커버한 것만 필터링
                        libraryList.postValue(it.data?.getUserInfo?.library?.filter { library ->
                            bandInfo.value?.song?.songId == library.song.songId
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
     * Band ID, Cover ID, 세션명을 통해 사용자의 라이브러리 커버 기반으로 밴드 참여
     */
    fun joinBand(sessionName: String) {
        val disposable = repository.joinBand(
            bandId = bandInfo.value!!.bandId,
            coverId = selectedUserCoverID.value!!,
            sessionName = sessionName
        )
            .applySchedulers()
            .subscribeBy(
                onError = {
                    Timber.i(it)
                },
                onSuccess = {
                    Timber.d(it.toString())
                    if (!it.hasErrors()) joinBandEvent.postValue(Event(true))
                    else joinBandEvent.postValue(Event(false))
                }
            )
        addDisposable(disposable)
    }

    /**
     * 밴드 삭제 뮤테이션
     */
    fun deleteBand() {
        val disposable = repository.deleteBand(bandInfo.value!!.bandId)
            .applySchedulers()
            .subscribeBy(
                onError = {
                    Timber.e(it)
                },
                onSuccess = {
                    if (!it.hasErrors()) {
                        deleteBandEvent.postValue(Event(true))
                    } else {
                        it.errors?.forEach {
                            Timber.e(it.message)
                        }
                    }
                }
            )
        addDisposable(disposable)
    }

    /**
     * 밴드 좋아요 토글 뮤테이션
     */
    fun likeBand() {
        val disposable = repository.likeBand(bandInfo.value!!.bandId)
            .applySchedulers()
            .subscribeBy(
                onError = {
                    Timber.e(it)
                },
                onSuccess = {
                    it.errors?.forEach {
                        Timber.e(it.message)
                    }
                }
            )
        addDisposable(disposable)
    }

    /**
     * 밴드 탈퇴 뮤테이션
     *
     * @param coverId : 밴드에서 제거(탈퇴)할 커버 ID
     */
    fun leaveBand(coverId: String) {
        val disposable = repository.leaveBand(bandId = bandInfo.value!!.bandId, coverId = coverId)
            .applySchedulers()
            .subscribeBy(
                onError = {
                    Timber.e(it)
                },
                onSuccess = {
                    if (!it.hasErrors()) {
                        Timber.d(it.data?.leaveBand.toString())
                        deleteCoverEvent.postValue(Event(true))
                    }
                }
            )
        addDisposable(disposable)
    }

    fun getSongInstrument() {
        val disposable = repository.getSongInstrument(bandInfo.value!!.song.songId)
            .applySchedulers()
            .subscribeBy(
                onError = {
                    Timber.e(it)
                },
                onNext = {
                    if (!it.hasErrors()) {
                        Timber.d(it.data?.getSong?.instrument.toString())
                        instList.postValue(it.data?.getSong?.instrument)
                    }
                },
                onComplete = {
                    Timber.d("getSongInstrument() Complete")
                }
            )
        addDisposable(disposable)
    }


}