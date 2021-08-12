package com.team_gdb.pentatonic

import com.team_gdb.pentatonic.data.model.*

object TestLibraryData {
    val TEST_LIBRARY_DATA: List<LibraryEntity> = listOf(
        LibraryEntity(
            id = 0,
            coverName = "녹음 1",
            originalSong = "DAY6 - 반드시 웃는다",
            imageUrl = "https://image.bugsm.co.kr/album/images/500/200284/20028430.jpg",
            introduction = "",
            coverSession = "드럼"
        ),
        LibraryEntity(
            id = 1,
            coverName = "녹음 2",
            originalSong = "DAY6 - 역대급(WALK)",
            imageUrl = "https://image.bugsm.co.kr/album/images/500/204070/20407021.jpg",
            introduction = "",
            coverSession = "기타"
        ),
        LibraryEntity(
            id = 2,
            coverName = "녹음 3",
            originalSong = "DAY6 - 예뻤어",
            imageUrl = "https://image.bugsm.co.kr/album/images/500/200804/20080494.jpg",
            introduction = "",
            coverSession = "보컬"
        ),
    )
}
