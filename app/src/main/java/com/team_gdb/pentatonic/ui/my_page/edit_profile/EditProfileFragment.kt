package com.team_gdb.pentatonic.ui.my_page.edit_profile

import android.content.Intent
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.materialdialogs.MaterialDialog
import com.bumptech.glide.Glide
import com.team_gdb.pentatonic.R
import com.team_gdb.pentatonic.adapter.cover_list.CoverHistoryListAdapter
import com.team_gdb.pentatonic.adapter.cover_list.TrendingCoverListAdapter
import com.team_gdb.pentatonic.adapter.position.PositionRankingListAdapter
import com.team_gdb.pentatonic.base.BaseApplication
import com.team_gdb.pentatonic.base.BaseFragment
import com.team_gdb.pentatonic.databinding.FragmentEditProfileBinding
import com.team_gdb.pentatonic.databinding.FragmentMyPageBinding
import com.team_gdb.pentatonic.ui.cover_view.band_cover.BandCoverActivity
import com.team_gdb.pentatonic.ui.login.LoginActivity
import com.team_gdb.pentatonic.ui.lounge.LoungeFragment.Companion.COVER_ENTITY
import com.team_gdb.pentatonic.ui.lounge.LoungeFragment.Companion.COVER_ID
import com.team_gdb.pentatonic.ui.my_page.MyPageViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class EditProfileFragment : BaseFragment<FragmentEditProfileBinding, MyPageViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.fragment_edit_profile
    override val viewModel: MyPageViewModel by sharedViewModel()

    override fun initStartView() {
        binding.viewModel = this.viewModel
        binding.lifecycleOwner = this
    }

    override fun initDataBinding() {
        viewModel.userProfileImage.observe(this) {
            Glide.with(this)
                .load(it)
                .placeholder(R.drawable.profile_image_placeholder)
                .override(100, 100)
                .into(binding.userProfileImage)
        }

        viewModel.userName.observe(this) {
            binding.editCompleteButton.isEnabled = !it.isNullOrBlank()
        }
    }

    override fun initAfterBinding() {

    }

}