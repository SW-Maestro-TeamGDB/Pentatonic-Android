package com.team_gdb.pentatonic.ui.cover_view.band_cover

import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.team_gdb.pentatonic.R
import com.team_gdb.pentatonic.adapter.library.LibrarySelectListAdapter
import com.team_gdb.pentatonic.base.BaseApplication
import com.team_gdb.pentatonic.base.BaseBottomSheetDialogFragment
import com.team_gdb.pentatonic.data.model.SongEntity
import com.team_gdb.pentatonic.databinding.DialogLibrarySelectBinding
import com.team_gdb.pentatonic.type.GENRE_TYPE
import com.team_gdb.pentatonic.ui.cover_view.CoverViewViewModel
import com.team_gdb.pentatonic.ui.cover_view.band_cover.BandCoverActivity.Companion.SESSION_TYPE
import com.team_gdb.pentatonic.ui.create_record.CreateRecordActivity
import com.team_gdb.pentatonic.ui.create_record.CreateRecordActivity.Companion.IS_MY_FREE_SONG
import com.team_gdb.pentatonic.ui.studio.StudioFragment
import com.team_gdb.pentatonic.ui.studio.StudioFragment.Companion.SONG_ENTITY
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import timber.log.Timber

class LibrarySelectBottomSheetDialog() :
    BaseBottomSheetDialogFragment<DialogLibrarySelectBinding, CoverViewViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.dialog_library_select
    override val viewModel: CoverViewViewModel by sharedViewModel()

    private val selectedSession: String by lazy {
        arguments?.getString(SESSION_TYPE) as String
    }

    private lateinit var librarySelectListAdapter: LibrarySelectListAdapter

    override fun initStartView() {
        val offsetFromTop = 400
        (dialog as? BottomSheetDialog)?.behavior?.apply {
            isFitToContents = true
            expandedOffset = offsetFromTop
        }

        binding.viewModel = this.viewModel
        librarySelectListAdapter = LibrarySelectListAdapter {
            viewModel.selectedUserCoverID.postValue(it)
        }
        binding.libraryList.apply {
            this.layoutManager = LinearLayoutManager(context)
            this.adapter = librarySelectListAdapter
            this.setHasFixedSize(true)
        }
    }

    override fun initDataBinding() {
        // 유효한 라이브러리 필터링하여 리스트로 보여줌
        viewModel.libraryList.observe(this) {
            val data = it.filter { selectedSession == it.position.rawValue }
            if (data.isNullOrEmpty()) {
                binding.libraryList.visibility = View.GONE
                binding.recordButtonLayout.visibility = View.VISIBLE
            } else {
                binding.noDataImageView.visibility = View.GONE
                binding.buttonLayout.visibility = View.VISIBLE
                librarySelectListAdapter.setItem(data)
            }
        }

        // 사용자가 라이브러리를 선택했다면 참여하기 버튼 활성화
        // - 선택 해제 시 비어있는 String 을 전달하기 때문
        viewModel.selectedUserCoverID.observe(this) {
            binding.joinBandButton.isEnabled = it.isNotBlank()
        }
    }

    override fun initAfterBinding() {
        binding.closeButton.setOnClickListener {
            dismiss()
        }

        binding.joinBandButton.setOnClickListener {
            viewModel.joinBand(selectedSession)
            dismiss()
        }

        binding.recordButton.setOnClickListener {
            val songInfo = viewModel.bandInfo.value!!.song
            val songEntity = SongEntity(
                songId = songInfo.songId,
                songUrl = songInfo.songURI,
                songName = songInfo.name,
                songLevel = songInfo.level ?: 2,
                artistName = songInfo.artist,
                albumJacketImage = songInfo.songImg ?: "",
                albumName = songInfo.album ?: "",
                albumReleaseDate = songInfo.releaseDate ?: "",
                songGenre = (songInfo.genre ?: GENRE_TYPE.POP).rawValue,
                isWeeklyChallenge = songInfo.weeklyChallenge,
                isFreeSong = viewModel.bandInfo.value!!.isFreeBand,
                duration = songInfo.duration
            )
            val intent = Intent(requireContext(), CreateRecordActivity::class.java).apply {
                putExtra(SONG_ENTITY, songEntity)
                putExtra(IS_MY_FREE_SONG, false)
            }
            startActivity(intent)
            dismiss()
            activity?.finish()
        }

        // 사용자의 라이브러리 정보 쿼리
        BaseApplication.prefs.userId?.let { viewModel.getUserLibrary(it) }
    }
}