package com.team_gdb.pentatonic.ui.create_cover.session_setting

import android.content.Intent
import androidx.recyclerview.widget.LinearLayoutManager
import com.team_gdb.pentatonic.R
import com.team_gdb.pentatonic.adapter.create_cover.SoloCoverSessionListAdapter
import com.team_gdb.pentatonic.base.BaseFragment
import com.team_gdb.pentatonic.data.model.SessionSettingEntity
import com.team_gdb.pentatonic.data.session.SessionList
import com.team_gdb.pentatonic.databinding.FragmentSoloCoverSessionSettingBinding
import com.team_gdb.pentatonic.ui.create_cover.CreateCoverViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class SoloCoverSessionSettingFragment :
    BaseFragment<FragmentSoloCoverSessionSettingBinding, CreateCoverViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.fragment_solo_cover_session_setting

    override val viewModel: CreateCoverViewModel by sharedViewModel()

    private lateinit var sessionListAdapter: SoloCoverSessionListAdapter

    override fun initStartView() {
        binding.viewModel = this.viewModel
        sessionListAdapter = SoloCoverSessionListAdapter {
            viewModel.coverSessionConfigList.postValue(listOf(SessionSettingEntity(it, 1)))
            binding.completeSessionSettingButton.isEnabled = true
        }
        binding.sessionList.apply {
            this.layoutManager = LinearLayoutManager(context)
            adapter = sessionListAdapter
            setHasFixedSize(true)
        }
    }

    override fun initDataBinding() {
    }

    override fun initAfterBinding() {
        sessionListAdapter.setItem(SessionList.sessionList)

        binding.completeSessionSettingButton.setOnClickListener {
            viewModel.completeCreateCover()
        }
    }
}