package com.team_gdb.pentatonic.ui.select_song

import com.bumptech.glide.Glide
import com.team_gdb.pentatonic.R
import com.team_gdb.pentatonic.base.BaseBottomSheetDialogFragment
import com.team_gdb.pentatonic.data.model.SongEntity
import com.team_gdb.pentatonic.databinding.DialogInputFreeSongInfoBinding
import com.team_gdb.pentatonic.databinding.DialogSongConfirmBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class RegisterFreeSongBottomSheetDialog() :
    BaseBottomSheetDialogFragment<DialogInputFreeSongInfoBinding, SelectSongViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.dialog_input_free_song_info
    override val viewModel: SelectSongViewModel by sharedViewModel()

    override fun initStartView() {
        binding.viewModel = this.viewModel
        binding.lifecycleOwner = this
    }

    override fun initDataBinding() {
        viewModel.isValidForm.observe(this) {
            binding.confirmButton.isEnabled = it
        }
    }

    override fun initAfterBinding() {
        binding.confirmButton.setOnClickListener {
            // 자유곡 정보 작성 완료 시 동작
            val songEntity = SongEntity(
                songId = "",
                songUrl = "",
                songName = viewModel.freeSongName.value!!,
                songLevel = 2,
                artistName = viewModel.freeSongArtist.value!!,
                albumJacketImage = "",
                albumName = viewModel.freeSongName.value!!,
                albumReleaseDate = "",
                songGenre = "",
                isWeeklyChallenge = false,
                isFreeSong = true,
                duration = 0.0
            )
            viewModel.selectedSong.postValue(songEntity)
        }
    }
}