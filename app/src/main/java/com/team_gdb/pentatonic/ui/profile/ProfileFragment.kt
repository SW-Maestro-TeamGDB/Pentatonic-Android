package com.team_gdb.pentatonic.ui.profile

import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.team_gdb.pentatonic.R
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.team_gdb.pentatonic.base.BaseFragment
import com.team_gdb.pentatonic.data.model.CoverItem
import com.team_gdb.pentatonic.data.model.User
import com.team_gdb.pentatonic.databinding.FragmentProfileBinding
import com.team_gdb.pentatonic.ui.band_cover.BandCoverFragmentArgs

class ProfileFragment : BaseFragment<FragmentProfileBinding, ProfileViewModel>() {

    override val layoutResourceId: Int
        get() = R.layout.fragment_profile
    override val viewModel: ProfileViewModel by viewModel()

    private val userItemArgs: ProfileFragmentArgs by navArgs()
    private val userItem: User by lazy {
        userItemArgs.userItem
    }

    override fun initStartView() {
        binding.viewModel = this.viewModel
    }

    override fun initDataBinding() {
    }

    override fun initAfterBinding() {
        binding.usernameTextView.text = userItem.username
        binding.userIntroductionTextView.text = userItem.introduction
        if (userItem.profileImage.isNotBlank()){
            Glide.with(this)
                .load(userItem.profileImage)
                .override(100, 100)
                .into(binding.userProfileImage)
        }
    }
}