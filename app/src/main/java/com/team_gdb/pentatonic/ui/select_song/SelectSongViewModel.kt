package com.team_gdb.pentatonic.ui.select_song

import androidx.lifecycle.MutableLiveData
import com.team_gdb.pentatonic.base.BaseViewModel
import com.team_gdb.pentatonic.data.model.SongEntity
import com.team_gdb.pentatonic.network.applySchedulers
import com.team_gdb.pentatonic.repository.select_song.SelectSongRepository
import com.team_gdb.pentatonic.type.GENRE_TYPE
import io.reactivex.rxjava3.kotlin.subscribeBy
import timber.log.Timber

class SelectSongViewModel(val repository: SelectSongRepository) : BaseViewModel() {
    val selectedSong: MutableLiveData<SongEntity> = MutableLiveData()
    val songList: MutableLiveData<List<SongEntity>> = MutableLiveData()

    fun getSongList(content: String) {
        val disposable = repository.getSongQuery(content)
            .applySchedulers()
            .subscribeBy(
                onError = {
                    Timber.e(it)
                },
                onNext = {
                    if (!it.hasErrors()) {
                        Timber.d(it.data?.querySong.toString())
                        songList.postValue(it.data?.querySong?.map {
                            SongEntity(
                                songId = it.songId,
                                songName = it.name,
                                songGenre = it.genre.name,
                                songUrl = it.songURI,
                                artistName = it.artist,
                                albumName = it.album,
                                albumReleaseDate = it.releaseDate,
                                albumJacketImage = it.songImg,
                                lyricist = "",
                                songWriter = ""
                            )
                        })
                    }
                },
                onComplete = {
                    Timber.d("getSongList() Complete")
                }
            )
        addDisposable(disposable)
    }
}