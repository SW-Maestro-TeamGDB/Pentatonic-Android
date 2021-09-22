package com.team_gdb.pentatonic.ui.band_cover

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import com.team_gdb.pentatonic.GetBandCoverInfoQuery
import com.team_gdb.pentatonic.GetUserLibraryQuery
import com.team_gdb.pentatonic.base.BaseViewModel
import com.team_gdb.pentatonic.network.applySchedulers
import com.team_gdb.pentatonic.repository.band_cover.BandCoverRepository
import io.reactivex.rxjava3.kotlin.subscribeBy
import timber.log.Timber


class BandCoverViewModel(val repository: BandCoverRepository) : BaseViewModel() {
    val bandInfo: MutableLiveData<GetBandCoverInfoQuery.GetBand> = MutableLiveData()

    private val selectedSession: HashMap<String, String> = hashMapOf()

    // 세션 to 커버 URL 로 구성된 HashMap
    val selectedSessionLiveData: MutableLiveData<HashMap<String, String>> = MutableLiveData()

    // 사용자의 세션 구성 선택대로 병합된 커버 URL
    val mergedCoverURL: MutableLiveData<String> = MutableLiveData()

    // 사용자의 라이브러리 목록
    val libraryList: MutableLiveData<List<GetUserLibraryQuery.Library>> = MutableLiveData()

    // 사용자가 선택한 라이브러리 커버 URL
    val selectedUserCoverID: MutableLiveData<String> = MutableLiveData()

    /**
     *  해당 밴드의 상세 정보를 가져오는 쿼리
     *
     * @param bandId  해당 밴드의 고유 ObjectID
     */
    fun getBandInfoQuery(bandId: String) {
        repository.getBandCoverInfo(bandId)
            .applySchedulers()
            .subscribeBy(
                onError = {
                    Timber.i(it)
                },
                onNext = {
                    if (!it.hasErrors()) {
                        bandInfo.postValue(it.data?.getBand)
                    } else {
                        Timber.i(it.errors.toString())
                    }
                },
                onComplete = {
                    Timber.d("getBandInfoQuery() Complete")
                }
            )
    }

    /**
     * CoverURL 목록을 통해 병합된 음원을 요청하는 뮤테이션
     *
     * @param coverList  병합할 커버 리스트 (URL 형태)
     */
    fun getMergedCover() {
        val coverList = selectedSession.values.toList()
        repository.getMergedCover(coverList)
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

    /**
     * 사용자가 가지고 있는 라이브러리 커버 쿼리
     * */
    fun getUserLibrary(userId: String) {
        repository.getUserLibrary(userId)
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
    }

    /**
     * Band ID, Cover ID, 세션명을 통해 사용자의 라이브러리 커버 기반으로 밴드 참여
     */
    fun joinBand(sessionName: String) {
        repository.joinBand(
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
                    if (!it.hasErrors()) {
                        Timber.d(it.data?.joinBand.toString())
                    }
                }
            )
    }
}