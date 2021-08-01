package com.team_gdb.pentatonic.ui.profile

import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.team_gdb.pentatonic.R
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.team_gdb.pentatonic.base.BaseFragment
import com.team_gdb.pentatonic.data.model.UserEntity
import com.team_gdb.pentatonic.databinding.FragmentProfileBinding

class ProfileFragment : BaseFragment<FragmentProfileBinding, ProfileViewModel>() {

    override val layoutResourceId: Int
        get() = R.layout.fragment_profile
    override val viewModel: ProfileViewModel by viewModel()

    private val userItemArgs: ProfileFragmentArgs by navArgs()
    private val userEntityItem: UserEntity by lazy {
        userItemArgs.userEntity
    }

    override fun initStartView() {
        binding.viewModel = this.viewModel
    }

    override fun initDataBinding() {
    }

    override fun initAfterBinding() {
        binding.usernameTextView.text = userEntityItem.username
        binding.userIntroductionTextView.text = userEntityItem.introduction
        if (userEntityItem.profileImage.isNotBlank()){
            Glide.with(this)
                .load(userEntityItem.profileImage)
                .override(100, 100)
                .into(binding.userProfileImage)
        }
    }
}