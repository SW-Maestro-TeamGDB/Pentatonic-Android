package com.team_gdb.pentatonic.custom_view

enum class ButtonState {
    BEFORE_RECORDING,  // 녹음 시작 전
    ON_RECORDING,  // 녹음을 하고 있을 때
    STOP_RECORDING,
    AFTER_RECORDING,  // 녹음이 끝났을 때
    ON_PLAYING,  // 녹음본 재생하고 있을 때
    BEFORE_PLAYING  // 재생 시작 전
}