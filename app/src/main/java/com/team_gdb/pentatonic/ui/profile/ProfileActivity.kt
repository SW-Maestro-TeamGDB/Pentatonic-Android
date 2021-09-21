package com.team_gdb.pentatonic.ui.profile

import com.bumptech.glide.Glide
import com.team_gdb.pentatonic.R
import com.team_gdb.pentatonic.base.BaseActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.team_gdb.pentatonic.databinding.ActivityProfileBinding
import timber.log.Timber

class ProfileActivity : BaseActivity<ActivityProfileBinding, ProfileViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.activity_profile
    override val viewModel: ProfileViewModel by viewModel()

    private val userID: String by lazy {
        intent.getSerializableExtra(USER_ID) as String
    }

    override fun initStartView() {
        binding.viewModel = this.viewModel
        binding.lifecycleOwner = this
    }

    override fun initDataBinding() {
        viewModel.userData.observe(this) {
            binding.usernameTextView.text = it.username
            binding.userIntroductionTextView.text = it.introduce
            Glide.with(this)
                .load(it.profileURI)
                .override(100, 100)
                .into(binding.userProfileImage)
        }
    }

    override fun initAfterBinding() {
        // 해당 사용자의 ID 를 기반으로 상세 정보 쿼리
        viewModel.getUserInfo(userID)
    }

    companion object {
        const val USER_ID = "USER_ID"
    }
}