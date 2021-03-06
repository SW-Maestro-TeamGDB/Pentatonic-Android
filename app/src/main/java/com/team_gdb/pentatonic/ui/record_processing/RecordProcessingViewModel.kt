package com.team_gdb.pentatonic.ui.record_processing

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.team_gdb.pentatonic.base.BaseViewModel
import com.team_gdb.pentatonic.network.applySchedulers
import com.team_gdb.pentatonic.repository.record_processing.RecordProcessingRepository
import com.team_gdb.pentatonic.custom_view.ButtonState
import com.team_gdb.pentatonic.data.model.CreatedCoverEntity
import com.team_gdb.pentatonic.data.model.CreatedRecordEntity
import com.team_gdb.pentatonic.data.model.SessionSettingEntity
import com.team_gdb.pentatonic.util.Event
import io.reactivex.rxjava3.kotlin.subscribeBy
import timber.log.Timber

class RecordProcessingViewModel(val repository: RecordProcessingRepository) : BaseViewModel() {
    val createdRecordEntity: MutableLiveData<CreatedRecordEntity> = MutableLiveData()

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


    val audioProcessingComplete: MutableLiveData<Event<Boolean>> = MutableLiveData()

    val coverUploadComplete: MutableLiveData<Event<String>> = MutableLiveData()

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

    // SeekBar ?????? ??? ??????
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
     * ?????? ??????(?????????)??? ??????????????? ????????????
     *
     * @param filePath : ???????????? ?????? ?????? (?????? ?????? ??????)
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
     * ????????? ????????? ????????? ???????????? ????????????
     *
     * @param coverUrl   : ?????? ???????????? ????????? (?????? ??? MR ?????? ??????)
     * @param songName   : ????????????
     * @param songArtist : ????????? ???????????????
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
            repository.uploadCoverToLibrary(name, coverURI, songId, position,
                (reverbEffectLevel.value!! * 0.001), 0.0)
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

}