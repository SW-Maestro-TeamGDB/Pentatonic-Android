package com.team_gdb.pentatonic.ui.my_page.edit_profile

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
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
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import es.dmoral.toasty.Toasty
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import timber.log.Timber
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import kotlin.random.Random

class EditProfileFragment : BaseFragment<FragmentEditProfileBinding, MyPageViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.fragment_edit_profile
    override val viewModel: MyPageViewModel by sharedViewModel()

    private val previousUsername: String by lazy {
        viewModel.userName.value!!
    }

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
            binding.editCompleteButton.isEnabled = it.length > 1
        }

        viewModel.completeUpdateProfile.observe(this) {
            if (it.getContentIfNotHandled() == true) {
                // 수정 페이지 종료
                Toasty.success(requireContext(), "프로필이 변경됐습니다!", Toast.LENGTH_SHORT, true).show()
                findNavController().popBackStack(R.id.navigation_edit_profile, true)
            }
        }
    }

    override fun initAfterBinding() {
        binding.userProfileImage.setOnClickListener {
            CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setCropShape(CropImageView.CropShape.RECTANGLE)
                .setActivityTitle("이미지 추가")
                .setCropMenuCropButtonTitle("완료")
                .setRequestedSize(1280, 1280)
                .start(requireContext(), this)
        }

        binding.editCompleteButton.setOnClickListener {
            viewModel.updateUserProfile(previousUsername)
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

                val bitmap =
                    MediaStore.Images.Media.getBitmap(context?.contentResolver, resultUri)
                val imageUri = bitmapToFile(bitmap!!) // Uri

                Timber.e(imageUri.toString())
                viewModel.uploadImageFile(imageUri.toString())
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Timber.e("이미지 선택 오류")
            }
        }
    }

    /**
     * Bitmap 이미지를 Local 에 저장하고, URI 를 반환함
     **/
    private fun bitmapToFile(bitmap: Bitmap): Uri {
        val wrapper = ContextWrapper(context)
        val randomNumber = Random.nextInt(0, 1000000000).toString()
        // Bitmap 파일 저장을 위한 File 객체
        var file = wrapper.getDir("Images", Context.MODE_PRIVATE)
        file = File(file, "item_${randomNumber}.jpg")
        try {
            // Bitmap 파일을 JPEG 형태로 압축해서 출력
            val stream: OutputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
            stream.flush()
            stream.close()
        } catch (e: IOException) {
            e.printStackTrace()
            Log.e("Error Saving Image", e.message!!)
        }
        return Uri.parse(file.absolutePath)
    }
}