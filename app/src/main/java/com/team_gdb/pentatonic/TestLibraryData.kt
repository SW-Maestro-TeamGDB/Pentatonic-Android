package com.team_gdb.pentatonic

import com.team_gdb.pentatonic.data.model.*

object TestLibraryData {
    val TEST_LIBRARY_DATA: List<LibraryEntity> = listOf(
        LibraryEntity(
            id = 0,
            coverName = "커버 1",
            originalSong = "DAY6 - 반드시 웃는다",
            imageUrl = "",
            introduction = "",
            coverSession = "드럼"
        ),
        LibraryEntity(
            id = 1,
            coverName = "커버 2",
            originalSong = "DAY6 - 역대급(WALK)",
            imageUrl = "",
            introduction = "",
            coverSession = "기타"
        ),
        LibraryEntity(
            id = 2,
            coverName = "커버 3",
            originalSong = "DAY6 - 예뻤어",
            imageUrl = "",
            introduction = "",
            coverSession = "보컬"
        ),
    )
}
