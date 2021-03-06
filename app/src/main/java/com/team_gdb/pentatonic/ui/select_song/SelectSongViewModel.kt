package com.team_gdb.pentatonic.ui.select_song

import androidx.lifecycle.MutableLiveData
import com.team_gdb.pentatonic.base.BaseViewModel
import com.team_gdb.pentatonic.data.genre.Genre
import com.team_gdb.pentatonic.data.model.SongEntity
import com.team_gdb.pentatonic.network.applySchedulers
import com.team_gdb.pentatonic.repository.select_song.SelectSongRepository
import com.team_gdb.pentatonic.type.GENRE_TYPE
import com.team_gdb.pentatonic.util.combineWith
import io.reactivex.rxjava3.kotlin.subscribeBy
import timber.log.Timber

class SelectSongViewModel(val repository: SelectSongRepository) : BaseViewModel() {
    val selectedSong: MutableLiveData<SongEntity> = MutableLiveData()
    val songList: MutableLiveData<List<SongEntity>> = MutableLiveData()

    val content: MutableLiveData<String> = MutableLiveData()
    val genre: MutableLiveData<Genre> = MutableLiveData()
    val level: MutableLiveData<Int> = MutableLiveData()

    val freeSongName: MutableLiveData<String> = MutableLiveData()
    val freeSongArtist: MutableLiveData<String> = MutableLiveData()

    // freeSongName 필드와 freeSongArtist 필드가 모두 비어있을 때 true 반환
    val isValidForm = freeSongName.combineWith(freeSongArtist) { songName, artist ->
        !songName.isNullOrBlank() && !artist.isNullOrBlank()
    }

    fun getSongList() {
        val disposable = repository.getSongQuery(content.value ?: "", genre.value, level.value)
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
                                songGenre = (it.genre ?: GENRE_TYPE.POP).name,
                                songUrl = it.songURI,
                                songLevel = it.level ?: 2,
                                isWeeklyChallenge = it.weeklyChallenge,
                                artistName = it.artist,
                                albumName = it.album ?: "자유곡",
                                albumReleaseDate = it.releaseDate ?: "자유곡",
                                albumJacketImage = it.songImg ?: "",
                                isFreeSong = false,
                                duration = it.duration
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