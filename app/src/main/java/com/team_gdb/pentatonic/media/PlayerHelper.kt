package com.team_gdb.pentatonic.media

import android.media.AudioAttributes
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import com.team_gdb.pentatonic.base.BaseApplication
import com.team_gdb.pentatonic.base.BaseApplication.Companion.applicationContext

/**
 * 싱글톤 패턴으로 MediaPlayer 사용
 */
object PlayerHelper {
    var player: MediaPlayer? = MediaPlayer()
    val duration: Int?
        get() = player?.duration

    /**
     * MedialPlayer 세팅
     *
     * @param filePath    재생할 음원의 경로
     * @param listener    재생 완료 시 동작
     */
    fun initPlayer(filePath: String, listener: MediaPlayer.OnCompletionListener) {
        stopPlaying()
        player = MediaPlayer().apply {
            setDataSource(filePath)
            prepare()
            setOnCompletionListener(listener)
        }
    }

    fun initStreamingPlayer(url: String, listener: MediaPlayer.OnCompletionListener) {
        stopPlaying()
        player = MediaPlayer().apply {
            setAudioAttributes(
                AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MOVIE)
                    .setLegacyStreamType(AudioManager.STREAM_MUSIC)
                    .build()
            )
            setDataSource(
                applicationContext(),
                Uri.parse(url)
            )
            prepare()
            setOnCompletionListener(listener)
        }
    }

    // 음악 재생 시작
    fun startPlaying() {
        player?.start()
    }

    // 음악 재생 정지
    fun pausePlaying() {
        player?.pause()
    }

    // 음악 재생 중단
    fun stopPlaying() {
        player?.release()
        player = null
    }

    // 재생 구간 탐색
    fun seekTo(progress: Int) {
        player?.seekTo(progress)
    }

}