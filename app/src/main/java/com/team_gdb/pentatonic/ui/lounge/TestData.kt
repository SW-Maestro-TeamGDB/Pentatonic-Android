package com.team_gdb.pentatonic.ui.lounge

import com.team_gdb.pentatonic.data.model.CoverItem

object TestData {
    val testBandCoverList: List<CoverItem> = listOf(
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

    val testSoloCoverList: List<CoverItem> = listOf(
        CoverItem(
            id = 0,
            coverName = "요즘 보컬 연습중이에요",
            originalSong = "DAY6 - Not Fine",
            imageUrl = "https://img.wowtv.co.kr/wowtv_news/dnrs/20210705/2021070508054205981d3244b4fed182172186127.jpg",
            introduction = "못 불러도 눈감고 들어봐주세요..",
            sessionList = mapOf("김원필" to "보컬"),
            like = 38,
            view = 59
        ),
        CoverItem(
            id = 1,
            coverName = "그저 평범한 일렉 솔로",
            originalSong = "Coldplay - Fix You",
            imageUrl = "https://images.unsplash.com/photo-1598518141892-06ba05f87bbe?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=932&q=80",
            introduction = "일렉 함 뜯어봤습니다",
            sessionList = mapOf("H43RO" to "일렉기타"),
            like = 15,
            view = 31
        )
    )

}