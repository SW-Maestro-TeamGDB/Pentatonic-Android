package com.team_gdb.pentatonic.ui.create_record

import android.content.Intent
import androidx.recyclerview.widget.LinearLayoutManager
import com.team_gdb.pentatonic.R
import com.team_gdb.pentatonic.adapter.create_cover.SoloCoverSessionListAdapter
import com.team_gdb.pentatonic.base.BaseFragment
import com.team_gdb.pentatonic.data.model.SessionSettingEntity
import com.team_gdb.pentatonic.data.session.SessionList
import com.team_gdb.pentatonic.databinding.FragmentCoverSessionSettingBinding
import com.team_gdb.pentatonic.ui.create_cover.CreateCoverActivity.Companion.CREATED_COVER_ENTITY
import com.team_gdb.pentatonic.ui.create_cover.CreateCoverViewModel
import com.team_gdb.pentatonic.ui.record.RecordActivity
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class CoverSessionSettingFragment :
    BaseFragment<FragmentCoverSessionSettingBinding, CreateRecordViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.fragment_cover_session_setting

    override val viewModel: CreateRecordViewModel by sharedViewModel()

    private lateinit var sessionListAdapter: SoloCoverSessionListAdapter

    override fun initStartView() {
        binding.viewModel = this.viewModel
        sessionListAdapter = SoloCoverSessionListAdapter {
            viewModel.coverSessionConfig.postValue(it)
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
            val intent = Intent(context, RecordActivity::class.java)
            intent.putExtra(CREATED_COVER_ENTITY, viewModel.createdCoverEntity)
            startActivity(intent)
        }
    }
}