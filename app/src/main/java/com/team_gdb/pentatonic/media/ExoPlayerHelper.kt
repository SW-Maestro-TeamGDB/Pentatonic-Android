package com.team_gdb.pentatonic.media

import android.media.MediaPlayer
import android.net.Uri
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.team_gdb.pentatonic.R
import com.team_gdb.pentatonic.base.BaseApplication.Companion.applicationContext

/**
 * 싱글톤 패턴으로 ExoPlayer 사용
 * - MediaPlayer -> ExoPlayer 마이그레이션 완료
 */
object ExoPlayerHelper {
    var player: SimpleExoPlayer = SimpleExoPlayer.Builder(applicationContext()).build()
    val duration: Long
        get() = player.duration

    /**
     * ExoPlayer 객체 생성
     *
     * @param filePath  음원 파일 경로 (URI 파싱 가능한 형태의 String)
     */
    fun initPlayer(filePath: String, onCompletePlaying: () -> Unit) {
        stopPlaying()
        val trackSelector = DefaultTrackSelector(applicationContext())
        player = SimpleExoPlayer.Builder(applicationContext())
            .setTrackSelector(trackSelector)
            .build()
            .apply {
                addListener(object : Player.Listener {
                    override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
                        if (playbackState == Player.STATE_ENDED) {
                            onCompletePlaying()
                        }
                        super.onPlayerStateChanged(playWhenReady, playbackState)
                    }
                })
                setMediaSource(buildMediaSource(filePath = filePath))
                prepare()
            }
    }

    /**
     * MediaSource 생성
     *
     * @param filePath   재생할 음원의 URI
     * @return           MediaSource 객체
     */
    private fun buildMediaSource(filePath: String): MediaSource {
        val dataSourceFactory: DataSource.Factory =
            DefaultDataSourceFactory(
                applicationContext(),
                applicationContext().resources.getString(R.string.app_name)
            ) //userAgent = applicationName
        val mediaSourceFactory = ProgressiveMediaSource.Factory(dataSourceFactory)
        return mediaSourceFactory.createMediaSource(MediaItem.fromUri(Uri.parse(filePath)))
    }

    // 음악 재생 시작
    fun startPlaying() {
        player.play()
    }

    // 음악 재생 정지
    fun pausePlaying() {
        player.pause()
    }

    // 음악 재생 중단
    fun stopPlaying() {
        player.release()
    }

    // 재생 구간 탐색
    fun seekTo(progress: Long) {
        player.seekTo(progress)
    }

}