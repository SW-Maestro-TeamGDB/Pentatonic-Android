package com.team_gdb.pentatonic.data.model

import android.net.Uri
import java.io.Serializable

/**
 * 서버에 커버 생성 요청을 보내기 위한 커버 정보
 *
 * @property coverName            사용자가 입력한 커버명
 * @property coverIntroduction    사용자가 입력한 커버 소개
 * @property coverSong            사용자가 커버한 원곡
 * @property coverSessionConfig   사용자가 설정한 커버 세션 구성
 * @property coverRecord          사용자의 커버 연주 및 노래 녹음본
 */
data class CreatedCoverEntity(
    val coverName: String,
    val coverIntroduction: String?,
    val coverSong: SongEntity,
    var coverSessionConfig: List<SessionSettingEntity>,
    var coverRecord: Uri?
) : Serializable