package com.team_gdb.pentatonic.ui.create_cover.session_setting

import com.team_gdb.pentatonic.R

enum class SessionSetting(val sessionName: String, val icon: Int) {
    VOCAL("보컬", R.drawable.ic_vocal),
    ACOUSTIC_GUITAR("어쿠스틱 기타", R.drawable.ic_acoustic_guitar),
    ELECTRIC_GUITAR("일렉트릭 기타", R.drawable.ic_electric_guitar),
    BASS("베이스 기타", R.drawable.ic_bass),
    DRUM("드럼", R.drawable.ic_drum),
    KEYBOARD("키보드", R.drawable.ic_keyboard),
    VIOLIN("바이올린", R.drawable.ic_violin),
    CELLO("첼로", R.drawable.ic_cello),
    GAYAGEUM("가야금", R.drawable.ic_gayaguem),
    HAEGUEM("해금", R.drawable.ic_haeguem),
    LYRE("거문고", R.drawable.ic_gayaguem)
}