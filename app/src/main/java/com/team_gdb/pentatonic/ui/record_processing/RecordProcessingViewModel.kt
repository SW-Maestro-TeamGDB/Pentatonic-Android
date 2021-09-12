package com.team_gdb.pentatonic.ui.record_processing

import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.team_gdb.pentatonic.base.BaseViewModel
import com.team_gdb.pentatonic.network.applySchedulers
import com.team_gdb.pentatonic.repository.record_processing.RecordProcessingRepository
import com.team_gdb.pentatonic.ui.record.ButtonState
import com.team_gdb.pentatonic.util.Event
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
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

    val coverUploadComplete: MutableLiveData<Event<Boolean>> = MutableLiveData()

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
                            coverUploadComplete.postValue(Event(true))
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