package com.team_gdb.pentatonic

import com.team_gdb.pentatonic.data.model.*

object TestRisingCoverData {
    val TEST_BAND_COVER_LIST: List<CoverEntity> = listOf(
        CoverEntity(
            id = 0,
            coverName = "오늘도 어김없이 펜타토닉",
            originalSong = "잔나비 - 전설",
            imageUrl = "https://images.unsplash.com/photo-1512405173804-40c66c0ed709?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1950&q=80",
            introduction = "그댄 나를 사랑이라 불러주오",
            sessionDataList = listOf(
                SessionData(
                    sessionName = "일렉기타",
                    sessionParticipantList = listOf(
                        UserEntity(
                            id = "1234",
                            username = "H43RO",
                            profileImage = "https://avatars.githubusercontent.com/u/30336663?v=4",
                            introduction = "취미로 기타 뜯고 놀아요"
                        ),
                        UserEntity(
                            id = "1234",
                            username = "원필쓰",
                            profileImage = "https://img.theqoo.net/img/kJYkg.jpg",
                            introduction = "안녕하세요 DAY6 원필입니다!"
                        )
                    ),
                    sessionMaxSize = 3
                ),
                SessionData(
                    sessionName = "키보드",
                    sessionParticipantList = listOf(
                        UserEntity(
                            id = "2345",
                            username = "LULU",
                            profileImage = "http://file3.instiz.net/data/cached_img/upload/2018/05/11/16/45c97753ecfd78eca2a0418d70f8ebf2.png",
                            introduction = "고양이 완전 조아해요"
                        ),
                        UserEntity(
                            id = "2345",
                            username = "YoungK",
                            profileImage = "",
                            introduction = "안녕하세요 DAY6 영케이 입니당"
                        ),
                        UserEntity(
                            id = "2345",
                            username = "MUX",
                            profileImage = "https://files.porsche.com/filestore/news/korea/ko/2018-04-28/headimage1/4793193a-4f9a-11e8-bbc5-0019999cd470/porsche-%ED%8F%AC%EB%A5%B4%EC%89%90AG%2C-2018%EB%85%84%EB%8F%84-1%EB%B6%84%EA%B8%B0-%EB%A7%A4%EC%B6%9C-%EB%B0%8F-%EC%98%81%EC%97%85-%EC%9D%B4%EC%9D%B5-%EC%A6%9D%EB%8C%80.jpg",
                            introduction = ""
                        ),
                        UserEntity(
                            id = "2345",
                            username = "크앙",
                            profileImage = "https://mblogthumb-phinf.pstatic.net/20140330_226/oulimmm_1396149063970IN3XW_JPEG/nord_8.jpg?type=w2",
                            introduction = ""
                        ),
                        UserEntity(
                            id = "2345",
                            username = "맨땅에헤딩",
                            profileImage = "",
                            introduction = ""
                        ),
                    ),
                    sessionMaxSize = 7
                )
            ),
            like = 38,
            view = 59
        ),
        CoverEntity(
            id = 1,
            coverName = "K-콜드플레이",
            originalSong = "Coldplay - Fix You",
            imageUrl = "https://images.unsplash.com/photo-1470229722913-7c0e2dbbafd3?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1950&q=80",
            introduction = "본격 띵곡 신성모독",
            sessionDataList = listOf(
                SessionData(
                    sessionName = "드럼",
                    sessionParticipantList = listOf(
                        UserEntity(
                            id = "1234",
                            username = "부기",
                            profileImage = "",
                            introduction = ""
                        )
                    ),
                    sessionMaxSize = 1
                ),
                SessionData(
                    sessionName = "일렉기타",
                    sessionParticipantList = listOf(
                        UserEntity(
                            id = "2345",
                            username = "Xenon2",
                            profileImage = "",
                            introduction = ""
                        )
                    ),
                    sessionMaxSize = 1
                ),
                SessionData(
                    sessionName = "키보드",
                    sessionParticipantList = listOf(
                        UserEntity(
                            id = "2345",
                            username = "LEON",
                            profileImage = "",
                            introduction = ""
                        )
                    ),
                    sessionMaxSize = 1
                )
            ),
            like = 15,
            view = 31
        ),
        CoverEntity(
            id = 2,
            coverName = "기타 맛깔나게 치시는 분 구함",
            originalSong = "DAY6 - Deep in love",
            imageUrl = "https://images.unsplash.com/photo-1452722464566-db26e74a995b?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1950&q=80",
            introduction = "초보 방 입니당",
            sessionDataList = listOf(
                SessionData(
                    sessionName = "일렉기타",
                    sessionParticipantList = listOf(
                        UserEntity(
                            id = "1234",
                            username = "H43RO",
                            profileImage = "https://avatars.githubusercontent.com/u/30336663?v=4",
                            introduction = "취미로 기타 뜯고 놀아요"
                        ),
                        UserEntity(
                            id = "1234",
                            username = "원필쓰",
                            profileImage = "https://img.theqoo.net/img/kJYkg.jpg",
                            introduction = "안녕하세요 DAY6 원필입니다!"
                        )
                    ),
                    sessionMaxSize = 2
                ),
                SessionData(
                    sessionName = "키보드",
                    sessionParticipantList = listOf(
                        UserEntity(
                            id = "2345",
                            username = "LULU",
                            profileImage = "http://file3.instiz.net/data/cached_img/upload/2018/05/11/16/45c97753ecfd78eca2a0418d70f8ebf2.png",
                            introduction = "고양이 완전 조아해요"
                        ),
                        UserEntity(
                            id = "2345",
                            username = "YoungK",
                            profileImage = "",
                            introduction = "안녕하세요 DAY6 영케이 입니당"
                        ),
                        UserEntity(
                            id = "2345",
                            username = "MUX",
                            profileImage = "https://files.porsche.com/filestore/news/korea/ko/2018-04-28/headimage1/4793193a-4f9a-11e8-bbc5-0019999cd470/porsche-%ED%8F%AC%EB%A5%B4%EC%89%90AG%2C-2018%EB%85%84%EB%8F%84-1%EB%B6%84%EA%B8%B0-%EB%A7%A4%EC%B6%9C-%EB%B0%8F-%EC%98%81%EC%97%85-%EC%9D%B4%EC%9D%B5-%EC%A6%9D%EB%8C%80.jpg",
                            introduction = ""
                        ),
                        UserEntity(
                            id = "2345",
                            username = "크앙",
                            profileImage = "https://mblogthumb-phinf.pstatic.net/20140330_226/oulimmm_1396149063970IN3XW_JPEG/nord_8.jpg?type=w2",
                            introduction = ""
                        ),
                        UserEntity(
                            id = "2345",
                            username = "맨땅에헤딩",
                            profileImage = "",
                            introduction = ""
                        ),
                    ),
                    sessionMaxSize = 8
                )
            ),
            like = 38,
            view = 59
        ),
    )

}