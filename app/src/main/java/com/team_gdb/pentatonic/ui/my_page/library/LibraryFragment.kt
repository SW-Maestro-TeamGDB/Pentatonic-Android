package com.team_gdb.pentatonic.ui.my_page.library

import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.materialdialogs.MaterialDialog
import com.nikhilpanju.recyclerviewenhanced.RecyclerTouchListener
import com.team_gdb.pentatonic.R
import com.team_gdb.pentatonic.adapter.library.LibraryListAdapter
import com.team_gdb.pentatonic.base.BaseFragment
import com.team_gdb.pentatonic.data.model.LibraryEntity
import com.team_gdb.pentatonic.databinding.FragmentLibraryBinding
import com.team_gdb.pentatonic.media.ExoPlayerHelper.initPlayer
import com.team_gdb.pentatonic.media.ExoPlayerHelper.pausePlaying
import com.team_gdb.pentatonic.media.ExoPlayerHelper.startPlaying
import com.team_gdb.pentatonic.media.ExoPlayerHelper.stopPlaying
import com.team_gdb.pentatonic.ui.my_page.MyPageViewModel
import com.team_gdb.pentatonic.util.PlayAnimation.playFailureAlert
import com.team_gdb.pentatonic.util.PlayAnimation.playSuccessAlert
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import timber.log.Timber

class LibraryFragment : BaseFragment<FragmentLibraryBinding, MyPageViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.fragment_library
    override val viewModel: MyPageViewModel by sharedViewModel()

    private lateinit var libraryListAdapter: LibraryListAdapter
    private lateinit var itemSwipeListener: RecyclerTouchListener

    override fun initStartView() {
        binding.viewModel = this.viewModel
        binding.titleBar.titleTextView.text = "라이브러리"

        libraryListAdapter = LibraryListAdapter(
            {   // 라이브러리 재생
                Timber.d("커버 URL : ${it.coverUrl}")
                initPlayer(it.coverUrl) {
                    pausePlaying()
                }
                startPlaying()
            }, // 라이브러리 재생 중지
            {
                pausePlaying()
            }, // 라이브러리 편집
            {
                viewModel.coverNameField.postValue(it.coverName)
                showEditDialog(it.id)
            },  // 라이브러리 삭제
            {
                showDeleteDialog(it)
            }
        )
        binding.libraryList.apply {
            this.layoutManager = LinearLayoutManager(context)
            this.adapter = libraryListAdapter
            this.setHasFixedSize(true)
        }
    }

    override fun initDataBinding() {
        viewModel.libraryList.observe(this) {
            val list = it.map {
                LibraryEntity(
                    coverName = it.name,
                    coverSession = it.position.rawValue,
                    coverUrl = it.coverURI,
                    id = it.coverId,
                    imageUrl = it.song.songImg ?: "",
                    coverDate = it.date,
                    originalSong = it.song,
                    coverBy = it.coverBy.id,
                    coverDuration = it.duration
                )
            }
            libraryListAdapter.setItem(list)
        }

        viewModel.completeCoverDelete.observe(this) {
            if (it.getContentIfNotHandled() == true) {
                playSuccessAlert(requireActivity(), "커버 삭제 완료!")
                // 사용자 정보 리프레시
                viewModel.getUserInfo("h2is1234")
            } else if (!it.peekContent()) {
                playFailureAlert(requireActivity(), "오류가 발생했습니다. 다시 시도해주세요.")
            }
        }

        viewModel.completeEditCoverName.observe(this) {
            if (it.getContentIfNotHandled() == true) {
                viewModel.editCover()
            }
        }

        viewModel.completeEditCoverNameMutation.observe(this) {
            if (it.getContentIfNotHandled() == true) {
                playSuccessAlert(requireActivity(), "커버 제목 편집 완료!")
                // 사용자 정보 리프레시
                viewModel.getUserInfo("h2is1234")
            } else if (!it.peekContent()) {
                playFailureAlert(requireActivity(), "오류가 발생했습니다. 다시 시도해주세요.")
            }
        }
    }

    override fun initAfterBinding() {
        binding.titleBar.backButton.setOnClickListener {
            findNavController().popBackStack(R.id.navigation_library, true)
        }
        itemSwipeListener = RecyclerTouchListener(activity, binding.libraryList).apply {
            setSwipeable(
                R.id.foregroundCard,
                R.id.backgroundCard
            ) { viewID, position ->

            }
        }
        binding.libraryList.addOnItemTouchListener(itemSwipeListener)

    }

    /**
     * 커버 삭제 확인 다이얼로그
     *
     * @param coverId : 삭제할 커버 ID
     */
    private fun showDeleteDialog(coverId: String) {
        MaterialDialog(requireContext()).show {
            title(R.string.cover_delete_notice_title)
            message(R.string.cover_delete_notice_content)
            positiveButton(R.string.yes_text) {
                viewModel.deleteCover(coverId)
            }
            negativeButton(R.string.no_text) {
                /* no-op */
            }
        }
    }


    private fun showEditDialog(coverId: String) {
        // 선택한 커버 ID ViewModel 에 저장
        viewModel.selectedCoverID.postValue(coverId)

        // 커버 이름 변경하는 모달 시트 띄움 (이후 커버 이름 변경 완료 이벤트 관찰)
        val bottomSheetDialog = EditCoverNameBottomSheetDialog()
        bottomSheetDialog.show(parentFragmentManager, bottomSheetDialog.tag)
    }

    override fun onDestroy() {
        // 음악 재생 중단
        stopPlaying()
        super.onDestroy()
    }
}