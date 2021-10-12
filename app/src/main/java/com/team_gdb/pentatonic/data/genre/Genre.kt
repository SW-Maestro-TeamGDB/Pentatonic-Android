package com.team_gdb.pentatonic.data.genre

/**
 * 펜타토닉 서비스가 제공하는 장르 ENUM 클래스
 *
 * @property genreName : 장르명 (한국어)
 */
enum class Genre(val genreName: String) {
    WHOLE("전체"),
    HIP_HOP("힙합"),
    ELECTRONIC("일렉트로닉"),
    BLUES("블루스"),
    POP("팝"),
    JAZZ("재즈"),
    CLASSICAL("클래식"),
    ROCK("락"),
    DANCE("댄스"),
    BALLAD("발라드"),
    K_POP("K-POP")
}