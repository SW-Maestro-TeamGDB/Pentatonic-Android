package com.team_gdb.pentatonic.ui.library

import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.team_gdb.pentatonic.R
import com.team_gdb.pentatonic.TestData
import com.team_gdb.pentatonic.TestLibraryData
import com.team_gdb.pentatonic.adapter.cover_list.CoverVerticalListAdapter
import com.team_gdb.pentatonic.adapter.library.LibraryListAdapter
import com.team_gdb.pentatonic.base.BaseFragment
import com.team_gdb.pentatonic.data.model.LibraryEntity
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

        libraryListAdapter = LibraryListAdapter {
//            findNavController().navigate(
            // TOOD : 라이브러리 정보 페이지
//            )
        }
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
                    id = 0,
                    imageUrl = "",
                    introduction = "",
                    originalSong = it.songId.toString()
                )
            }
            libraryListAdapter.setItem(list)
        }
    }

    override fun initAfterBinding() {
        binding.titleBar.backButton.setOnClickListener {

        }
    }
}