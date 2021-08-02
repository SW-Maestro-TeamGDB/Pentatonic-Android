package com.team_gdb.pentatonic

import com.team_gdb.pentatonic.data.model.CoverEntity
import com.team_gdb.pentatonic.data.model.Session
import com.team_gdb.pentatonic.data.model.SongEntity
import com.team_gdb.pentatonic.data.model.UserEntity

object TestData {
    val TEST_BAND_COVER_LIST: List<CoverEntity> = listOf(
        CoverEntity(
            id = 0,
            coverName = "데이식스 담당 찐따",
            originalSong = "DAY6 - 반드시 웃는다",
            imageUrl = "https://images.unsplash.com/photo-1526478806334-5fd488fcaabc?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1090&q=80",
            introduction = "그저 데식을 사랑하는..",
            sessionList = listOf(
                Session(
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
                    )
                ),
                Session(
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
                    )
                )
            ),
            like = 38,
            view = 59
        ),
        CoverEntity(
            id = 1,
            coverName = "무지성 합주",
            originalSong = "Coldplay - Fix You",
            imageUrl = "https://images.unsplash.com/photo-1499364615650-ec38552f4f34?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1866&q=80",
            introduction = "본격 띵곡 신성모독",
            sessionList = listOf(
                Session(
                    sessionName = "드럼",
                    sessionParticipantList = listOf(
                        UserEntity(
                            id = "1234",
                            username = "부기",
                            profileImage = "",
                            introduction = ""
                        )
                    )
                ),
                Session(
                    sessionName = "일렉기타",
                    sessionParticipantList = listOf(
                        UserEntity(
                            id = "2345",
                            username = "Xenon2",
                            profileImage = "",
                            introduction = ""
                        )
                    )
                ),
                Session(
                    sessionName = "키보드",
                    sessionParticipantList = listOf(
                        UserEntity(
                            id = "2345",
                            username = "LEON",
                            profileImage = "",
                            introduction = ""
                        )
                    )
                )
            ),
            like = 15,
            view = 31
        )
    )

    val TEST_SOLO_COVER_LIST: List<CoverEntity> = listOf(
        CoverEntity(
            id = 0,
            coverName = "요즘 보컬 연습중이에요",
            originalSong = "DAY6 - Not Fine",
            imageUrl = "https://img.wowtv.co.kr/wowtv_news/dnrs/20210705/2021070508054205981d3244b4fed182172186127.jpg",
            introduction = "못 불러도 눈감고 들어봐주세요..",
            sessionList = listOf(
                Session(
                    sessionName = "보컬",
                    sessionParticipantList = listOf(
                        UserEntity(
                            id = "1234",
                            username = "김원필",
                            profileImage = "",
                            introduction = ""
                        )
                    )
                )
            ),
            like = 38,
            view = 59
        ),
        CoverEntity(
            id = 1,
            coverName = "그저 평범한 일렉 솔로",
            originalSong = "Coldplay - Fix You",
            imageUrl = "https://images.unsplash.com/photo-1598518141892-06ba05f87bbe?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=932&q=80",
            introduction = "일렉 함 뜯어봤습니다",
            sessionList = listOf(
                Session(
                    sessionName = "일렉기타",
                    sessionParticipantList = listOf(
                        UserEntity(
                            id = "1234",
                            username = "H43RO",
                            profileImage = "",
                            introduction = ""
                        )
                    )
                )
            ),
            like = 15,
            view = 31
        )
    )

    val TEST_SONG_LIST: List<SongEntity> = listOf(
        SongEntity(
            name = "역대급",
            artist = "DAY6",
            albumJacketImage = "https://image.bugsm.co.kr/album/images/500/204070/20407021.jpg"
        ),
        SongEntity(
            name = "놓아 놓아 놓아",
            artist = "DAY6",
            albumJacketImage = "https://image.bugsm.co.kr/album/images/500/200284/20028430.jpg"
        ),
        SongEntity(
            name = "행복했던 날들이었다",
            artist = "DAY6",
            albumJacketImage = "https://image.bugsm.co.kr/album/images/500/202143/20214346.jpg"
        ),
        SongEntity(
            name = "예뻤어",
            artist = "DAY6",
            albumJacketImage = "https://image.bugsm.co.kr/album/images/500/200804/20080494.jpg"
        )
    )


}