package com.team_gdb.pentatonic.ui.create_cover

import androidx.core.content.ContextCompat
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.team_gdb.pentatonic.R
import com.team_gdb.pentatonic.base.BaseFragment
import com.team_gdb.pentatonic.databinding.FragmentBasicInfoFormBinding
import com.team_gdb.pentatonic.util.PlayAnimation
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.team_gdb.pentatonic.ui.select_song.SelectSongActivity
import timber.log.Timber
import java.sql.Time


class BasicInfoFormFragment : BaseFragment<FragmentBasicInfoFormBinding, CreateCoverViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.fragment_basic_info_form
    override val viewModel: CreateCoverViewModel by sharedViewModel()

    private val selectSongActivityLauncher =
        registerForActivityResult(SelectSongResultContract()) {
            Timber.d("제에발 여기 좀 보세요!")
            Timber.d(it.toString())
        }


    override fun initStartView() {
        binding.viewModel = this.viewModel
    }

    override fun initDataBinding() {
        viewModel?.coverBasicInfoValidation?.observe(this) {
            // Basic Information Form Validation 성립하는 경우
            if (!it.peekContent()) {
                if (viewModel.coverName.value.isNullOrBlank()) {
                    binding.coverNameEditText.error = "필수 항목입니다"
                }
                if (viewModel.coverSong.value == null) {
                    binding.selectSongTitleTextView.setTextColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.red
                        )
                    )
                }
                PlayAnimation.playErrorAnimation(binding.formLayout)
            }
        }
    }

    override fun initAfterBinding() {
        binding.selectSongButton.setOnClickListener {
            val intent = Intent(activity, SelectSongActivity::class.java)
            selectSongActivityLauncher.launch(intent)
        }
    }
}