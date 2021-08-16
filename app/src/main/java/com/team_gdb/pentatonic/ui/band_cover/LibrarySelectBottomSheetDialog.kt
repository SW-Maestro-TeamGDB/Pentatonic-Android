package com.team_gdb.pentatonic.ui.band_cover

import com.bumptech.glide.Glide
import com.team_gdb.pentatonic.R
import com.team_gdb.pentatonic.base.BaseBottomSheetDialogFragment
import com.team_gdb.pentatonic.databinding.DialogLibrarySelectBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class LibrarySelectBottomSheetDialog() :
    BaseBottomSheetDialogFragment<DialogLibrarySelectBinding, BandCoverViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.dialog_library_select
    override val viewModel: BandCoverViewModel by sharedViewModel()

    override fun initStartView() {
        binding.viewModel = this.viewModel
    }

    override fun initDataBinding() {
    }

    override fun initAfterBinding() {
        // 곡 정보를 모두 채워줌
//        Glide.with(binding.root)
//            .load(songEntity.albumJacketImage)
//            .placeholder(R.drawable.placeholder_cover_bg)
//            .override(270, 270)
//            .into(binding.albumJacketImage)
//        binding.songNameTextView.text = songEntity.songTitle
//        binding.songArtistTextView.text = songEntity.artistName
//
//        binding.confirmSongButton.setOnClickListener {
//            viewModel.selectedSong.postValue(songEntity)
//        }
//
//        binding.cancelButton.setOnClickListener {
//            dismiss()
//        }
    }
}