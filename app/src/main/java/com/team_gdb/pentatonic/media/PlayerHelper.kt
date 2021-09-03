package com.team_gdb.pentatonic.media

import android.media.MediaPlayer

/**
 * 싱글톤 패턴으로 MediaPlayer 사용
 */
object PlayerHelper {
    var player: MediaPlayer? = MediaPlayer()

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

    fun startPlaying() {
        player?.start()
    }

    fun pausePlaying() {
        player?.pause()
    }

    fun stopPlaying() {
        player?.release()
        player = null
    }

    private fun setOnCompleteListener(listener: MediaPlayer.OnCompletionListener){
        player?.setOnCompletionListener(listener)
    }
}