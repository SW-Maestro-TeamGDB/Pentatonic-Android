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
import android.graphics.Color
import android.graphics.PorterDuff
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.Glide
import com.team_gdb.pentatonic.data.model.SongEntity
import com.team_gdb.pentatonic.ui.select_song.SelectSongActivity
import com.team_gdb.pentatonic.util.Event
import org.koin.android.ext.android.bind
import timber.log.Timber
import java.sql.Time


class BasicInfoFormFragment : BaseFragment<FragmentBasicInfoFormBinding, CreateCoverViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.fragment_basic_info_form
    override val viewModel: CreateCoverViewModel by sharedViewModel()

    /**
     * SelectSongActivity 에서 선택한 곡에 대한 정보가 콜백 형태로 담기게 됨
     */
    private val selectSongActivityLauncher =
        registerForActivityResult(SelectSongResultContract()) {
            if (it is SongEntity) {
                viewModel.coverSong.postValue(it)
            }
        }


    override fun initStartView() {
        binding.viewModel = this.viewModel
    }

    override fun initDataBinding() {
        viewModel.coverSong.observe(this){
            if (it is SongEntity){
                binding.beforeSelectSongTextView.visibility = View.GONE
                Glide.with(binding.root)
                    .load(it.albumJacketImage)
                    .placeholder(R.drawable.placeholder_cover_bg)
                    .override(480, 272)
                    .into(binding.selectedSongAlbumJacketImage)
                binding.selectedSongAlbumJacketImage.setColorFilter(Color.parseColor("#80000000"))

                binding.afterSelectSongLayout.visibility = View.VISIBLE
                binding.selectedSongNameTextView.text = it.name
                binding.selectedSongArtistTextView.text = it.artist
            }
        }
        viewModel.coverBasicInfoValidation.observe(this) {
            // Basic Information Form Validation 성립하지 않는 경우
            if (!it.peekContent()) {
                if (viewModel.coverName.value.isNullOrBlank()) {
                    binding.coverNameEditText.error = "필수 항목입니다"
                }
                if (viewModel.coverSong.value == null) {
                    binding.selectSongTitleTextView.setTextColor(
                        ContextCompat.getColor(requireContext(), R.color.red)
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