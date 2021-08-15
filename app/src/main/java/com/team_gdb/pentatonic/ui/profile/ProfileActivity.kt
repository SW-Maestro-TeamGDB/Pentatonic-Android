package com.team_gdb.pentatonic.ui.profile

import com.bumptech.glide.Glide
import com.team_gdb.pentatonic.R
import com.team_gdb.pentatonic.base.BaseActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.team_gdb.pentatonic.data.model.UserEntity
import com.team_gdb.pentatonic.databinding.ActivityProfileBinding
import com.team_gdb.pentatonic.ui.band_cover.BandCoverActivity.Companion.USER_ENTITY

class ProfileActivity : BaseActivity<ActivityProfileBinding, ProfileViewModel>() {

    override val layoutResourceId: Int
        get() = R.layout.activity_profile
    override val viewModel: ProfileViewModel by viewModel()

    private val userEntityItem: UserEntity by lazy {
        intent.getSerializableExtra(USER_ENTITY) as UserEntity
    }

    override fun initStartView() {
        binding.viewModel = this.viewModel
        binding.usernameTextView.text = userEntityItem.username
        binding.userIntroductionTextView.text = userEntityItem.introduction
        if (userEntityItem.profileImage.isNotBlank()){
            Glide.with(this)
                .load(userEntityItem.profileImage)
                .override(100, 100)
                .into(binding.userProfileImage)
        }
    }

    override fun initDataBinding() {
    }

    override fun initAfterBinding() {

    }
}