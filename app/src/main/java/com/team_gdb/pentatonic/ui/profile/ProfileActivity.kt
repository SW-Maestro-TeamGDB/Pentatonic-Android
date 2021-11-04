package com.team_gdb.pentatonic.ui.profile

import android.content.Intent
import android.net.Uri
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.team_gdb.pentatonic.R
import com.team_gdb.pentatonic.adapter.cover_list.CoverHistoryListAdapter
import com.team_gdb.pentatonic.adapter.position.PositionRankingListAdapter
import com.team_gdb.pentatonic.base.BaseActivity
import com.team_gdb.pentatonic.base.BaseApplication
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.team_gdb.pentatonic.databinding.ActivityProfileBinding
import com.team_gdb.pentatonic.ui.cover_view.band_cover.BandCoverActivity
import com.team_gdb.pentatonic.ui.cover_view.solo_cover.SoloCoverActivity
import com.team_gdb.pentatonic.ui.lounge.LoungeFragment
import com.team_gdb.pentatonic.util.PlayAnimation.playFailureAlert
import com.team_gdb.pentatonic.util.PlayAnimation.playSuccessAlert

class ProfileActivity : BaseActivity<ActivityProfileBinding, ProfileViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.activity_profile
    override val viewModel: ProfileViewModel by viewModel()

    private lateinit var coverHistoryListAdapter: CoverHistoryListAdapter
    private lateinit var positionRankingAdapter: PositionRankingListAdapter

    private val userID: String by lazy {
        intent.getSerializableExtra(USER_ID) as String
    }

    override fun initStartView() {
        binding.viewModel = this.viewModel
        binding.lifecycleOwner = this

        // 해당 사용자의 ID 를 기반으로 상세 정보 쿼리
        viewModel.getUserInfo(userID)

        if (userID == BaseApplication.prefs.userId) {
            binding.followButton.visibility = View.GONE
        }

        binding.titleBar.titleTextView.text = "프로필"

        coverHistoryListAdapter = CoverHistoryListAdapter { coverId, isSoloCover ->
            val intent = if (isSoloCover) {
                Intent(this, SoloCoverActivity::class.java)
            } else {
                Intent(this, BandCoverActivity::class.java)
            }
            intent.putExtra(LoungeFragment.COVER_ID, coverId)
            startActivity(intent)
        }

        binding.coverHistoryList.apply {
            this.layoutManager = LinearLayoutManager(context).apply {
                this.orientation = LinearLayoutManager.HORIZONTAL
            }
            this.adapter = coverHistoryListAdapter
            this.setHasFixedSize(true)
        }

        positionRankingAdapter = PositionRankingListAdapter()

        binding.positionRankingList.apply {
            this.layoutManager = LinearLayoutManager(context).apply {
                this.orientation = LinearLayoutManager.HORIZONTAL
            }
            this.adapter = positionRankingAdapter
            this.setHasFixedSize(true)
        }
    }

    override fun initDataBinding() {
        viewModel.userData.observe(this) {
            Glide.with(this)
                .load(it.profileURI)
                .override(100, 100)
                .into(binding.userProfileImage)

            binding.usernameTextView.text = it.username
            binding.userIntroductionTextView.text = it.introduce
            binding.userFollowerCountTextView.text = it.followerCount.toString()
            binding.userFollowingCountTextView.text = it.followingCount.toString()

            if (it.prime) {
                binding.primeBadgeView.visibility = View.VISIBLE
                binding.primeUserTextView.visibility = View.VISIBLE
            }
            if (!it.social?.facebook.isNullOrBlank()) {
                val url = it.social?.facebook
                binding.facebookButton.visibility = View.VISIBLE
                binding.facebookButton.setOnClickListener {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    startActivity(intent)
                }
            }
            if (!it.social?.instagram.isNullOrBlank()) {
                val url = it.social?.instagram
                binding.instagramButton.visibility = View.VISIBLE
                binding.instagramButton.setOnClickListener {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    startActivity(intent)
                }
            }
            if (!it.social?.twitter.isNullOrBlank()) {
                val url = it.social?.twitter
                binding.twitterButton.visibility = View.VISIBLE
                binding.twitterButton.setOnClickListener {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    startActivity(intent)
                }
            }
            if (!it.social?.kakao.isNullOrBlank()) {
                val url = it.social?.kakao
                binding.kakaoButton.visibility = View.VISIBLE
                binding.kakaoButton.setOnClickListener {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    startActivity(intent)
                }
            }

            binding.followButton.isChecked = it.followingStatus == false
        }
        viewModel.coverHistoryList.observe(this) {
            coverHistoryListAdapter.setItem(it)
        }

        viewModel.positionRankingList.observe(this) {
            it?.let {
                positionRankingAdapter.setItem(it)
            }
        }
    }

    override fun initAfterBinding() {
        binding.titleBar.backButton.setOnClickListener {
            finish()
        }

        binding.followButton.setOnClickListener {
            binding.followButton.toggle()
            viewModel.followUser(userID)
            if (binding.followButton.isChecked) {
                playSuccessAlert(this, "해당 유저를 팔로우했습니다")
            } else {
                playFailureAlert(this, "해당 유저를 팔로우 취소했습니다")
            }
        }
    }

    companion object {
        const val USER_ID = "USER_ID"
    }
}