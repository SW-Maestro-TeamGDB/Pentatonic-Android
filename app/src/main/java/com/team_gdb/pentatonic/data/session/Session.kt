package com.team_gdb.pentatonic.data.session

import com.team_gdb.pentatonic.R

/**
 * 펜타토닉이 지원하는 세션 ENUM 클래스
 *
 * @property sessionName   // 해당 세션 이름
 * @property icon          // 대표 이미지
 */
enum class Session(val sessionName: String, val icon: Int) {
    VOCAL("보컬", R.drawable.ic_vocal),
    ACOUSTIC_GUITAR("어쿠스틱 기타", R.drawable.ic_acoustic_guitar),
    ELECTRIC_GUITAR("일렉트릭 기타", R.drawable.ic_electric_guitar),
    BASS("베이스 기타", R.drawable.ic_bass),
    DRUM("드럼", R.drawable.ic_drum),
    PIANO("피아노", R.drawable.ic_keyboard),
    KEYBOARD("키보드", R.drawable.ic_keyboard),
    VIOLIN("바이올린", R.drawable.ic_violin),
    CELLO("첼로", R.drawable.ic_cello),
    FLUTE("플룻", R.drawable.ic_flute),
    TRUMPET("트럼펫", R.drawable.ic_trumpet),
    CLARINET("클라리넷", R.drawable.ic_clarinet),
    GAYAGEUM("가야금", R.drawable.ic_gayaguem),
    GEOMUNGO("거문고", R.drawable.ic_gayaguem),
    HAEGEUM("해금", R.drawable.ic_haegeum),
    FREE("그 외 악기", R.drawable.ic_etc_session)
}