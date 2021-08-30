package com.team_gdb.pentatonic.ui.library

import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.team_gdb.pentatonic.R
import com.team_gdb.pentatonic.TestData
import com.team_gdb.pentatonic.TestLibraryData
import com.team_gdb.pentatonic.adapter.cover_list.CoverVerticalListAdapter
import com.team_gdb.pentatonic.adapter.library.LibraryListAdapter
import com.team_gdb.pentatonic.base.BaseFragment
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
            it.forEach {
                Timber.d(it.toString())
            }
        }
    }

    override fun initAfterBinding() {
        binding.titleBar.backButton.setOnClickListener {

        }
        viewModel.libraryList.value?.forEach {
            Timber.d(it.name)
        }
        libraryListAdapter.setItem(TestLibraryData.TEST_LIBRARY_DATA)
    }
}