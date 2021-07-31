package com.team_gdb.pentatonic.ui.lounge

import com.team_gdb.pentatonic.data.model.CoverItem

object TestData {
    val itemList: List<CoverItem> = listOf(
        CoverItem(
            id = 0,
            coverName = "데이식스 담당 찐따",
            originalSong = "DAY6 - 반드시 웃는다",
            imageUrl = "",
            introduction = "그저 데식을 사랑하는..",
            sessionList = mapOf("H43RO" to "일렉기타", "LULU" to "키보드"),
            like = 38,
            view = 59
        ),
        CoverItem(
            id = 1,
            coverName = "무지성 합주",
            originalSong = "Coldplay - Fix You",
            imageUrl = "https://images.unsplash.com/photo-1499364615650-ec38552f4f34?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1866&q=80",
            introduction = "본격 띵곡 신성모독",
            sessionList = mapOf("부기" to "드럼" , "Xenon2" to "일렉기타", "LEON" to "키보드"),
            like = 15,
            view = 31
        )
    )
}