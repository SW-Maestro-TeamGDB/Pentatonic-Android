package com.team_gdb.pentatonic.ui.my_page

import androidx.lifecycle.MutableLiveData
import com.apollographql.apollo.api.Input
import com.team_gdb.pentatonic.GetUserInfoQuery
import com.team_gdb.pentatonic.base.BaseViewModel
import com.team_gdb.pentatonic.network.applySchedulers
import com.team_gdb.pentatonic.repository.my_page.MyPageRepository
import com.team_gdb.pentatonic.util.Event
import com.team_gdb.pentatonic.util.PlayAnimation.playFailureAlert
import io.reactivex.rxjava3.kotlin.subscribeBy
import timber.log.Timber


/**
 * MyPageFragment, LibraryFragment 이 SharedViewModel() 형태로 LiveData 공유
 * - 유저의 프로필 정보와, 라이브러리 데이터를 갖게 됨
 *
 * @property repository : GetUserInfo() 쿼리 수행
 */
class MyPageViewModel(val repository: MyPageRepository) : BaseViewModel() {

    val userName: MutableLiveData<String> = MutableLiveData()
    val userFollowerCount: MutableLiveData<String> = MutableLiveData()
    val userFollowingCount: MutableLiveData<String> = MutableLiveData()
    val userIntroduce: MutableLiveData<String> = MutableLiveData()
    val userProfileImage: MutableLiveData<String> = MutableLiveData()

    val facebookUrl: MutableLiveData<String> = MutableLiveData()
    val instagramUrl: MutableLiveData<String> = MutableLiveData()
    val twitterUrl: MutableLiveData<String> = MutableLiveData()
    val kakaoUrl: MutableLiveData<String> = MutableLiveData()

    val userSocialInfo: MutableLiveData<GetUserInfoQuery.Social> = MutableLiveData()
    val hasFacebook: MutableLiveData<Boolean> = MutableLiveData()
    val hasInstagram: MutableLiveData<Boolean> = MutableLiveData()
    val hasTwitter: MutableLiveData<Boolean> = MutableLiveData()
    val hasKakao: MutableLiveData<Boolean> = MutableLiveData()

    val isPrimeUser: MutableLiveData<Boolean> = MutableLiveData()

    val libraryList: MutableLiveData<List<GetUserInfoQuery.Library>> =
        MutableLiveData()  // 라이브러리 리스트 정보

    val coverHistoryList: MutableLiveData<List<GetUserInfoQuery.Band>> =
        MutableLiveData()  // 커버 (밴드 참여) 히스토리 정보

    val positionRankingList: MutableLiveData<List<GetUserInfoQuery.Position?>> =
        MutableLiveData()  // 포지션 정보

    val selectedCoverID: MutableLiveData<String> = MutableLiveData()  // 선택한 커버 ID 저장
    val coverNameField: MutableLiveData<String> = MutableLiveData()  // 변경될 커버 이름

    val completeEditCoverName: MutableLiveData<Event<Boolean>> =
        MutableLiveData()  // 라이브러리 이름 변경 완료 여부
    val completeEditCoverNameMutation: MutableLiveData<Event<Boolean>> =
        MutableLiveData()  // 라이브러리 이름 변경 뮤테이션 호출 완료 여부
    val completeCoverDelete: MutableLiveData<Event<Boolean>> = MutableLiveData()  // 라이브러리 삭제 성공 여부
    val completeUpdateProfile: MutableLiveData<Event<Boolean>> =
        MutableLiveData()  // 사용자 프로필 수정 성공 여부

    /**
     * 사용자의 정보를 마이페이지에 적용하고, 라이브러리 정보도 가져옴
     * - LibraryFragment 에서 해당 데이터 옵저빙 함
     *
     * @param id : 사용자 식별을 위한 ID
     */
    fun getUserInfo(id: String) {
        val disposable = repository.getUserInfo(id)
            .applySchedulers()
            .subscribeBy(
                onError = {
                },
                onNext = {
                    if (!it.hasErrors()) {
                        userName.postValue(it.data?.getUserInfo?.username.toString())
                        userFollowerCount.postValue(it.data?.getUserInfo?.followerCount.toString())
                        userFollowingCount.postValue(it.data?.getUserInfo?.followingCount.toString())
                        userIntroduce.postValue(it.data?.getUserInfo?.introduce.toString())
                        userProfileImage.postValue(it.data?.getUserInfo?.profileURI)

                        userSocialInfo.postValue(it.data?.getUserInfo?.social)

                        facebookUrl.postValue(it.data?.getUserInfo?.social?.facebook)
                        instagramUrl.postValue(it.data?.getUserInfo?.social?.instagram)
                        twitterUrl.postValue(it.data?.getUserInfo?.social?.twitter)
                        kakaoUrl.postValue(it.data?.getUserInfo?.social?.kakao)

                        hasFacebook.postValue(!it.data?.getUserInfo?.social?.facebook.isNullOrBlank())
                        hasInstagram.postValue(!it.data?.getUserInfo?.social?.instagram.isNullOrBlank())
                        hasTwitter.postValue(!it.data?.getUserInfo?.social?.twitter.isNullOrBlank())
                        hasKakao.postValue(!it.data?.getUserInfo?.social?.kakao.isNullOrBlank())

                        isPrimeUser.postValue(it.data?.getUserInfo?.prime)

                        // LibraryFragment 에서 참조할 데이터 Library List postValue()
                        libraryList.postValue(it.data?.getUserInfo?.library)

                        // 해당 사용자가 참여한 커버 히스토리 ViewModel
                        coverHistoryList.postValue(it.data?.getUserInfo?.band)

                        // 해당 사용자의 포지션 랭킹 정보
                        positionRankingList.postValue(it.data?.getUserInfo?.position)
                    } else {
                        it.errors?.forEach {
                            Timber.e(it.message)
                        }
                    }
                },
                onComplete = {
                    /* no-op */
                }
            )
        addDisposable(disposable)
    }

    fun editCover() {
        val disposable =
            repository.editCover(selectedCoverID.value.toString(), coverNameField.value.toString())
                .applySchedulers()
                .subscribeBy(
                    onError = {
                        Timber.i(it)
                    },
                    onSuccess = {
                        if (it.hasErrors()) completeEditCoverNameMutation.postValue(Event(false))
                        else completeEditCoverNameMutation.postValue(Event(true))
                    }
                )
        addDisposable(disposable)
    }


    /**
     * 라이브러리에서 특정 커버를 삭제함
     *
     * @param coverId : 삭제할 커버의 아이디
     */
    fun deleteCover(coverId: String) {
        val disposable =
            repository.deleteCover(coverId)
                .applySchedulers()
                .subscribeBy(
                    onError = {
                        Timber.i(it)
                    },
                    onSuccess = {
                        if (it.hasErrors()) completeCoverDelete.postValue(Event(false))
                        else completeCoverDelete.postValue(Event(true))
                    }
                )
        addDisposable(disposable)
    }

    /**
     * 사용자 프로필 이미지 업로드
     */
    fun uploadImageFile(filePath: String) {
        val disposable = repository.uploadImageFile(filePath)
            .applySchedulers()
            .subscribeBy(
                onError = {
                    Timber.i(it)
                },
                onSuccess = {
                    Timber.d(it.toString())
                    if (!it.hasErrors()) {
                        Timber.d(it.data?.uploadImageFile)
                        userProfileImage.postValue(it.data?.uploadImageFile)
                    }
                }
            )
        addDisposable(disposable)
    }

    fun updateUserProfile(previousUsername: String) {
        val disposable = repository.updateProfileMutation(
            previousUsername = previousUsername,
            username = userName.value!!,
            profileURI = userProfileImage.value ?: "",
            introduce = userIntroduce.value!!,
            facebookUrl = if (facebookUrl.value.isNullOrBlank()) {
                null
            } else {
                facebookUrl.value
            },
            instagramUrl = if (instagramUrl.value.isNullOrBlank()) {
                null
            } else {
                instagramUrl.value
            },
            twitterUrl = if (twitterUrl.value.isNullOrBlank()) {
                null
            } else {
                twitterUrl.value
            },
            kakaoUrl = if (kakaoUrl.value.isNullOrBlank()) {
                null
            } else {
                kakaoUrl.value
            },
        ).applySchedulers()
            .subscribeBy(
                onError = {
                    Timber.e(it)
                    completeUpdateProfile.postValue(Event(false))
                },
                onSuccess = {
                    Timber.d(it.data?.changeProfile?.id)
                    if (!it.hasErrors()) {
                        completeUpdateProfile.postValue(Event(true))
                    } else {
                        it.errors?.forEach {
                            Timber.e(it.message)
                        }
                        completeUpdateProfile.postValue(Event(false))
                    }
                }
            )
        addDisposable(disposable)
    }

}