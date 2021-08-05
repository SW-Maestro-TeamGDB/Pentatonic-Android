package com.team_gdb.pentatonic.ui.create_cover

import androidx.core.content.ContextCompat
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.team_gdb.pentatonic.R
import com.team_gdb.pentatonic.base.BaseFragment
import com.team_gdb.pentatonic.databinding.FragmentBasicInfoFormBinding
import com.team_gdb.pentatonic.util.PlayAnimation
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class BasicInfoFormFragment : BaseFragment<FragmentBasicInfoFormBinding, CreateCoverViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.fragment_basic_info_form
    override val viewModel: CreateCoverViewModel by sharedViewModel()

    override fun initStartView() {
        binding.viewModel = this.viewModel
    }

    override fun initDataBinding() {
        viewModel?.coverBasicInfoValidation?.observe(this) {
            // Basic Information Form Validation 성립하는 경우
            if (!it.peekContent()) {
                if (viewModel.coverName.value.isNullOrBlank()){
                    binding.coverNameEditText.error = "필수 항목입니다"
                }
                if (viewModel.coverSong.value == null) {
                    binding.selectSongTitleTextView.setTextColor(ContextCompat.getColor(requireContext(), R.color.red))
                }
                PlayAnimation.playErrorAnimation(binding.formLayout)
            }
        }
    }

    override fun initAfterBinding() {
    }
}