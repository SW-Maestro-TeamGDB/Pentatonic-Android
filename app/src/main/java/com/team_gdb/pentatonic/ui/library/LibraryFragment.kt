package com.team_gdb.pentatonic.ui.library

import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.tapadoo.alerter.Alerter
import com.team_gdb.pentatonic.R
import com.team_gdb.pentatonic.TestData
import com.team_gdb.pentatonic.TestLibraryData
import com.team_gdb.pentatonic.adapter.cover_list.CoverVerticalListAdapter
import com.team_gdb.pentatonic.adapter.library.LibraryListAdapter
import com.team_gdb.pentatonic.base.BaseFragment
import com.team_gdb.pentatonic.data.model.LibraryEntity
import com.team_gdb.pentatonic.data.model.SessionSettingEntity
import com.team_gdb.pentatonic.databinding.FragmentLibraryBinding
import com.team_gdb.pentatonic.ui.lounge.LoungeFragmentDirections
import com.team_gdb.pentatonic.ui.my_page.MyPageViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class LibraryFragment : BaseFragment<FragmentLibraryBinding, MyPageViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.fragment_library
    override val viewModel: MyPageViewModel by sharedViewModel()

    private lateinit var libraryListAdapter: LibraryListAdapter

    override fun initStartView() {
        binding.viewModel = this.viewModel
        binding.titleBar.titleTextView.text = "라이브러리"

        libraryListAdapter = LibraryListAdapter(
            {
//            findNavController().navigate(
                // TOOD : 라이브러리 정보 페이지
//            )
            },
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

        viewModel.coverDeleteComplete.observe(this) {
            if (it.getContentIfNotHandled() == true) {
                Alerter.create(requireActivity())
                    .setText("커버 삭제 완료!")
                    .setIcon(R.drawable.ic_acoustic_guitar)
                    .setBackgroundColorRes(R.color.main_regular)
                    .setIconColorFilter(0)
                    .setIconSize(R.dimen.custom_icon_size)
                    .show()

                // 사용자 정보 리프레시
                viewModel.getUserInfo("h2is1234")
            } else if (!it.peekContent()) {
                Alerter.create(requireActivity())
                    .setText("오류가 발생했습니다. 다시 시도해주세요.")
                    .setIcon(R.drawable.ic_acoustic_guitar)
                    .setBackgroundColorRes(R.color.red)
                    .setIconColorFilter(0)
                    .setIconSize(R.dimen.custom_icon_size)
                    .show()
            }
        }
    }

    override fun initAfterBinding() {
        binding.titleBar.backButton.setOnClickListener {

        }
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
}