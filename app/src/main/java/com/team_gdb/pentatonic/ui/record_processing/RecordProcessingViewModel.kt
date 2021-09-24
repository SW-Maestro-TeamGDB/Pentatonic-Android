package com.team_gdb.pentatonic.ui.record_processing

import androidx.lifecycle.MutableLiveData
import com.team_gdb.pentatonic.base.BaseViewModel
import com.team_gdb.pentatonic.network.applySchedulers
import com.team_gdb.pentatonic.repository.record_processing.RecordProcessingRepository
import com.team_gdb.pentatonic.custom_view.ButtonState
import com.team_gdb.pentatonic.util.Event
import io.reactivex.rxjava3.kotlin.subscribeBy
import timber.log.Timber

class RecordProcessingViewModel(val repository: RecordProcessingRepository) : BaseViewModel() {
    val buttonState: MutableLiveData<ButtonState> =
        MutableLiveData<ButtonState>(ButtonState.BEFORE_PLAYING)

    val playTime: MutableLiveData<String> = MutableLiveData("00:00")
    val remainTime: MutableLiveData<String> = MutableLiveData("00:00")

    val volumeLevel: MutableLiveData<Int> = MutableLiveData(50)
    val syncLevel: MutableLiveData<Int> = MutableLiveData(50)
    val gainEffectLevel: MutableLiveData<Int> = MutableLiveData(50)
    val reverbEffectLevel: MutableLiveData<Int> = MutableLiveData(50)

    val coverNameField: MutableLiveData<String> = MutableLiveData()
    val coverFileURL: MutableLiveData<String> = MutableLiveData()

    val coverNameInputComplete: MutableLiveData<Event<Boolean>> = MutableLiveData()

    val coverUploadComplete: MutableLiveData<Event<String>> = MutableLiveData()

    val createBandComplete: MutableLiveData<Event<String>> = MutableLiveData()

    val joinBandComplete: MutableLiveData<Event<Boolean>> = MutableLiveData()

    fun controlVolumeLevel(amount: Int) {
        volumeLevel.value = volumeLevel.value?.plus(amount)
        Timber.d("Volume Control 예아")
        Timber.d("VolumeLevel : ${volumeLevel.value}")
    }

    fun controlSyncLevel(amount: Int) {
        syncLevel.value = syncLevel.value?.plus(amount)
    }

    fun controlGainEffectLevel(amount: Int) {
        gainEffectLevel.value = gainEffectLevel.value?.plus(amount)
    }

    fun controlReverbEffectLevel(amount: Int) {
        reverbEffectLevel.value = reverbEffectLevel.value?.plus(amount)
    }

    /**
     * 커버 파일(녹음본)을 업로드하는 뮤테이션
     *
     * @param filePath : 녹음본의 파일 경로 (보통 캐시 파일)
     */
    fun uploadCoverFile(filePath: String) {
        val disposable =
            repository.uploadCoverFile(filePath)
                .applySchedulers()
                .subscribeBy(
                    onError = {
                        Timber.i(it)
                    },
                    onSuccess = {
                        if (!it.hasErrors()) {
                            Timber.d(it.data?.uploadCoverFile.toString())
                            coverFileURL.postValue(it.data?.uploadCoverFile)
                        } else {
                            it.errors?.forEach { e ->
                                Timber.i(e.message)
                            }
                        }
                    }
                )
        addDisposable(disposable)
    }

    fun uploadCoverToLibrary(name: String, coverURI: String, songId: String, position: String) {
        val disposable =
            repository.uploadCoverToLibrary(name, coverURI, songId, position)
                .applySchedulers()
                .subscribeBy(
                    onError = {
                        Timber.i(it)
                    },
                    onSuccess = {
                        if (!it.hasErrors()) {
                            Timber.d(it.data?.uploadCover.toString())
                            coverUploadComplete.postValue(Event(it.data?.uploadCover?.coverId!!))
                        } else {
                            it.errors?.forEach { e ->
                                Timber.i(e.message)
                            }
                        }
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
        sessionName: String,
        bandName: String,
        bandIntroduction: String,
        backgroundUrl: String,
        songId: String
    ) {
        val disposable =
            repository.createBand(sessionName, bandName, bandIntroduction, backgroundUrl, songId)
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