package com.team_gdb.pentatonic.media

import android.media.MediaPlayer

/**
 * 싱글톤 패턴으로 MediaPlayer 사용
 */
object PlayerHelper {
    var player: MediaPlayer? = MediaPlayer()
    val duration: Int?
        get() = player?.duration
    val currentPosition: Int?
        get() = player?.currentPosition

    /**
     * MedialPlayer 세팅
     *
     * @param filePath    재생할 음원의 경로
     * @param listener    재생 완료 시 동작
     */
    fun initPlayer(filePath: String, listener: MediaPlayer.OnCompletionListener) {
        if (player == null) {
            player = MediaPlayer()
        }
        player?.setDataSource(filePath)
        player?.prepare()

        setOnCompleteListener(listener)
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

    // 재생 완료 시 수행할 동작
    private fun setOnCompleteListener(listener: MediaPlayer.OnCompletionListener) {
        player?.setOnCompletionListener(listener)
    }

}