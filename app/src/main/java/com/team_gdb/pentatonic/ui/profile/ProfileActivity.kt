package com.team_gdb.pentatonic.ui.profile

import android.content.Intent
import android.view.View
import androidx.core.content.ContentProviderCompat.requireContext
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
import com.team_gdb.pentatonic.ui.lounge.LoungeFragment
import com.team_gdb.pentatonic.util.PlayAnimation.playFailureAlert
import com.team_gdb.pentatonic.util.PlayAnimation.playSuccessAlert
import timber.log.Timber

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

        if (userID == BaseApplication.prefs.userId) {
            binding.followButton.visibility = View.GONE
        }

        coverHistoryListAdapter = CoverHistoryListAdapter {
            val intent = Intent(this, BandCoverActivity::class.java)
            intent.putExtra(LoungeFragment.COVER_ID, it)
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
        // 해당 사용자의 ID 를 기반으로 상세 정보 쿼리
        viewModel.getUserInfo(userID)

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