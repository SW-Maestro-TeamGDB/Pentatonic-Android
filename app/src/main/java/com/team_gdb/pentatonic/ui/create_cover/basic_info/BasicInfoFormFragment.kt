package com.team_gdb.pentatonic.ui.create_cover.basic_info

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import androidx.core.content.ContextCompat
import com.team_gdb.pentatonic.R
import com.team_gdb.pentatonic.base.BaseFragment
import com.team_gdb.pentatonic.databinding.FragmentBasicInfoFormBinding
import com.team_gdb.pentatonic.util.PlayAnimation
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import com.team_gdb.pentatonic.data.model.SongEntity
import com.team_gdb.pentatonic.ui.create_cover.CreateCoverViewModel
import com.team_gdb.pentatonic.ui.select_song.SelectSongActivity
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import timber.log.Timber
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import kotlin.random.Random


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
        viewModel.coverSong.observe(this) {
            if (it is SongEntity) {
                binding.beforeSelectSongTextView.visibility = View.GONE
                Glide.with(binding.root)
                    .load(it.albumJacketImage)
                    .placeholder(R.drawable.placeholder_cover_bg)
                    .override(480, 272)
                    .into(binding.selectedSongAlbumJacketImage)
                binding.selectedSongAlbumJacketImage.setColorFilter(Color.parseColor("#80000000"))

                binding.afterSelectSongLayout.visibility = View.VISIBLE
                binding.selectedSongNameTextView.text = it.songName
                binding.selectedSongArtistTextView.text = it.artistName
            }
        }
        viewModel.coverBasicInfoValidationEvent.observe(this) {
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
                PlayAnimation.playErrorAnimationOnEditText(binding.formLayout)
            }
        }
    }

    override fun initAfterBinding() {
        binding.selectSongButton.setOnClickListener {
            val intent = Intent(activity, SelectSongActivity::class.java)
            selectSongActivityLauncher.launch(intent)
        }

        binding.selectBackgroundImageButton.setOnClickListener {
            CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setActivityTitle("이미지 추가")
                .setCropShape(CropImageView.CropShape.RECTANGLE)
                .setCropMenuCropButtonTitle("완료")
                .setRequestedSize(1280, 1280)
                .start(requireContext(), this)
        }

        viewModel.coverBackgroundImage.observe(this) {
            Glide.with(binding.root)
                .load(it)
                .placeholder(R.drawable.placeholder_cover_bg)
                .into(binding.selectedBackgroundImage)
            binding.beforeSelectImageTextView.visibility = View.GONE
        }
    }

    /**
     * 사용자가 이미지 선택을 완료하면 실행됨
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // 업로드를 위한 사진이 선택 및 편집되면 Uri 형태로 결과가 반환됨
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result = CropImage.getActivityResult(data)

            if (resultCode == Activity.RESULT_OK) {
                val resultUri = result.uri

                Timber.e(resultUri.toString())
                viewModel.coverBackgroundImage.postValue(resultUri)

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Timber.e("이미지 선택 오류")
            }
        }
    }

}