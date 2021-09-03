package com.team_gdb.pentatonic.ui.library

import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.nikhilpanju.recyclerviewenhanced.RecyclerTouchListener
import com.team_gdb.pentatonic.R
import com.team_gdb.pentatonic.adapter.library.LibraryListAdapter
import com.team_gdb.pentatonic.base.BaseFragment
import com.team_gdb.pentatonic.data.model.LibraryEntity
import com.team_gdb.pentatonic.databinding.FragmentLibraryBinding
import com.team_gdb.pentatonic.ui.my_page.MyPageViewModel
import com.team_gdb.pentatonic.util.PlayAnimation.playFailureAlert
import com.team_gdb.pentatonic.util.PlayAnimation.playSuccessAlert
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class LibraryFragment : BaseFragment<FragmentLibraryBinding, MyPageViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.fragment_library
    override val viewModel: MyPageViewModel by sharedViewModel()

    private lateinit var libraryListAdapter: LibraryListAdapter
    private lateinit var recyclerViewTouchListener: RecyclerTouchListener

    override fun initStartView() {
        binding.viewModel = this.viewModel
        binding.titleBar.titleTextView.text = "라이브러리"

        libraryListAdapter = LibraryListAdapter(
            {   // 라이브러리 클릭
                // TODO()
            },  // 라이브러리 편집
            {
                viewModel.coverNameField.postValue(it.coverName)
                editDialog(it.id)
            },  // 라이브러리 삭제
            {
                deleteDialog(it)
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
                    id = it.coverId.toString(),
                    imageUrl = it.song.songImg,
                    introduction = it.song.releaseDate.toString(),
                    originalSong = it.song.name
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

        }
        recyclerViewTouchListener = RecyclerTouchListener(activity, binding.libraryList).apply {
            setSwipeOptionViews(R.id.editButton, R.id.deleteButton)
            setSwipeable(
                R.id.foregroundCard,
                R.id.backgroundCard
            ) { viewID, position ->

            }
        }
        binding.libraryList.addOnItemTouchListener(recyclerViewTouchListener)

    }

    /**
     * 커버 삭제 확인 다이얼로그
     *
     * @param coverId : 삭제할 커버 ID
     */
    private fun deleteDialog(coverId: String) {
        val builder = AlertDialog.Builder(requireContext())
        builder.apply {
            this.setMessage("해당 커버를 삭제하시겠습니까?")
            this.setNegativeButton("아니요") { _, _ -> }
            this.setPositiveButton("네") { _, _ ->
                viewModel.deleteCover(coverId)
            }
        }
        builder.show().run {
            getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.main_regular
                )
            )
            getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.black
                )
            )
        }
    }


    private fun editDialog(coverId: String) {
        // 선택한 커버 ID ViewModel 에 저장
        viewModel.selectedCoverID.postValue(coverId)

        // 커버 이름 변경하는 모달 시트 띄움 (이후 커버 이름 변경 완료 이벤트 관찰)
        val bottomSheetDialog = EditCoverNameBottomSheetDialog()
        bottomSheetDialog.show(parentFragmentManager, bottomSheetDialog.tag)
    }
}