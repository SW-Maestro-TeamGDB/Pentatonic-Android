package com.team_gdb.pentatonic.ui.create_cover

import CreateCoverSessionSettingListAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.team_gdb.pentatonic.R
import com.team_gdb.pentatonic.TestData
import com.team_gdb.pentatonic.adapter.SelectSessionListAdapter
import com.team_gdb.pentatonic.base.BaseFragment
import com.team_gdb.pentatonic.databinding.FragmentSessionSettingBinding
import com.team_gdb.pentatonic.ui.select_song.SongConfirmBottomSheetDialog
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class SessionSettingFragment :
    BaseFragment<FragmentSessionSettingBinding, CreateCoverViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.fragment_session_setting

    override val viewModel: CreateCoverViewModel by sharedViewModel()

    private lateinit var sessionSettingListAdapter: CreateCoverSessionSettingListAdapter

    override fun initStartView() {
        binding.viewModel = this.viewModel
    }

    override fun initDataBinding() {
        viewModel.coverSessionConfigList.observe(this) {
            sessionSettingListAdapter.setItem(it)
        }
    }

    override fun initAfterBinding() {
        sessionSettingListAdapter = CreateCoverSessionSettingListAdapter {
        }

        binding.sessionConfigList.apply {
            this.layoutManager = LinearLayoutManager(context)
            adapter = sessionSettingListAdapter
            setHasFixedSize(true)
        }

        binding.addSessionButton.setOnClickListener {
            val bottomSheetDialogFragment = SelectSessionBottomSheetDialog()
            bottomSheetDialogFragment.show(
                requireActivity().supportFragmentManager,
                bottomSheetDialogFragment.tag
            )
        }
    }
}