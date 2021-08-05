package com.team_gdb.pentatonic.ui.select_song

import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.team_gdb.pentatonic.R
import com.team_gdb.pentatonic.base.BaseBottomSheetDialogFragment
import com.team_gdb.pentatonic.data.model.SongEntity
import com.team_gdb.pentatonic.databinding.DialogSongConfirmBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import timber.log.Timber

class SongConfirmBottomSheetDialog(val songEntity: SongEntity): BaseBottomSheetDialogFragment<DialogSongConfirmBinding, SelectSongViewModel>()  {
    override val layoutResourceId: Int
        get() = R.layout.dialog_song_confirm
    override val viewModel: SelectSongViewModel by sharedViewModel()

    override fun initStartView() {
        binding.viewModel = this.viewModel
    }

    override fun initDataBinding() {
    }

    override fun initAfterBinding() {
        // 곡 정보를 모두 채워줌
        if (songEntity.albumJacketImage.isNotBlank()) {
            Glide.with(binding.root)
                .load(songEntity.albumJacketImage)
                .override(480, 272)
                .into(binding.albumJacketImage)
        } else {
            Glide.with(binding.root)
                .load(R.drawable.placeholder_cover_bg)
                .override(480, 272)
                .into(binding.albumJacketImage)
        }

        binding.songNameTextView.text = songEntity.name
        binding.songArtistTextView.text = songEntity.artist

        // TODO : ActivityResult 로 SongEntity 넘겨주는 로직 필요함 (혹은 Entity 의 고유 ID)
        binding.confirmSongButton.setOnClickListener {
            dismiss()
        }
    }
}