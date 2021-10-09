package com.team_gdb.pentatonic.ui.record_processing

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.team_gdb.pentatonic.base.BaseViewModel
import com.team_gdb.pentatonic.network.applySchedulers
import com.team_gdb.pentatonic.repository.record_processing.RecordProcessingRepository
import com.team_gdb.pentatonic.custom_view.ButtonState
import com.team_gdb.pentatonic.data.model.CreatedCoverEntity
import com.team_gdb.pentatonic.data.model.SessionSettingEntity
import com.team_gdb.pentatonic.util.Event
import io.reactivex.rxjava3.kotlin.subscribeBy
import timber.log.Timber

class RecordProcessingViewModel(val repository: RecordProcessingRepository) : BaseViewModel() {
    val createdCoverEntity: MutableLiveData<CreatedCoverEntity> = MutableLiveData()

    val buttonState: MutableLiveData<ButtonState> =
        MutableLiveData<ButtonState>()

    private val _instMergedCover: MutableLiveData<String> = MutableLiveData()
    val instMergedCover: LiveData<String>
        get() = _instMergedCover

    val playTime: MutableLiveData<String> = MutableLiveData("00:00")
    val remainTime: MutableLiveData<String> = MutableLiveData("00:00")

    val volumeLevel: MutableLiveData<Int> = MutableLiveData(50)
    val syncLevel: MutableLiveData<Int> = MutableLiveData(-20)
    val gainEffectLevel: MutableLiveData<Int> = MutableLiveData(0)
    val reverbEffectLevel: MutableLiveData<Int> = MutableLiveData(0)

    val coverNameField: MutableLiveData<String> = MutableLiveData()
    val coverFileURL: MutableLiveData<String> = MutableLiveData()

    val freeSongId: MutableLiveData<String> = MutableLiveData()

    val coverNameInputComplete: MutableLiveData<Event<Boolean>> = MutableLiveData()

    val audioProcessingComplete: MutableLiveData<Event<Boolean>> = MutableLiveData()

    val coverUploadComplete: MutableLiveData<Event<String>> = MutableLiveData()
    val createBandComplete: MutableLiveData<Event<String>> = MutableLiveData()
    val joinBandComplete: MutableLiveData<Event<Boolean>> = MutableLiveData()

    fun controlVolumeLevel(amount: Int) {
        val addedValue = volumeLevel.value?.plus(amount)
        if (0 <= addedValue!! && addedValue <= 100) {
            volumeLevel.value = addedValue
        }
    }

    fun controlSyncLevel(amount: Int) {
        val addedValue = syncLevel.value?.plus(amount)
        if (-500 <= addedValue!! && addedValue <= 200) {
            syncLevel.value = addedValue
        }
    }

    fun controlGainEffectLevel(amount: Int) {
        val addedValue = gainEffectLevel.value?.plus(amount)
        if (0 <= addedValue!! && addedValue <= 100) {
            gainEffectLevel.value = addedValue
        }
    }

    fun controlReverbEffectLevel(amount: Int) {
        val addedValue = reverbEffectLevel.value?.plus(amount)
        if (0 <= addedValue!! && addedValue <= 100) {
            reverbEffectLevel.value = addedValue
        }
    }

    // SeekBar 바뀔 때 호출
    fun setVolumeLevel(amount: Int) {
        volumeLevel.value = amount
    }

    fun setSyncLevel(amount: Int) {
        syncLevel.value = amount
    }

    fun setGainEffectLevel(amount: Int) {
        gainEffectLevel.value = amount
    }

    fun setReverbEffectLevel(amount: Int) {
        reverbEffectLevel.value = amount
    }


    fun getInstMergedCover(songUrl: String, coverUrl: String) {
        val coverList = listOf(songUrl, coverUrl)
        val disposable = repository.getMergedResult(coverList)
            .applySchedulers()
            .subscribeBy(
                onError = {
                    Timber.i(it)
                },
                onSuccess = {
                    if (!it.hasErrors()) {
                        Timber.d("getMergedCover() : ${it.data}")
                        _instMergedCover.postValue(it.data?.mergeAudios)
                    } else {
                        Timber.i(it.errors.toString())
                    }
                }
            )
        addDisposable(disposable)
    }

    fun setInstMergedCover(coverUrl: String) {
        _instMergedCover.value = coverUrl
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

    /**
     * 서버에 자유곡 정보를 등록하는 뮤테이션
     *
     * @param coverUrl   : 해당 자유곡의 연주본 (이게 곧 MR 역할 수행)
     * @param songName   : 자유곡명
     * @param songArtist : 자유곡 아티스트명
     */
    fun registerFreeSong(coverUrl: String, songName: String, songArtist: String) {
        val disposable =
            repository.registerFreeSong(coverUrl, songName, songArtist)
                .applySchedulers()
                .subscribeBy(
                    onError = {
                        Timber.i(it)
                    },
                    onSuccess = {
                        if (!it.hasErrors()) {
                            Timber.d(it.data?.uploadFreeSong)
                            freeSongId.postValue(it.data?.uploadFreeSong)
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