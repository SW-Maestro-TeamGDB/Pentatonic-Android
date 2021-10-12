package com.team_gdb.pentatonic.ui.create_cover.session_setting

import androidx.recyclerview.widget.LinearLayoutManager
import com.team_gdb.pentatonic.R
import com.team_gdb.pentatonic.adapter.create_cover.SelectBandSessionListAdapter
import com.team_gdb.pentatonic.base.BaseBottomSheetDialogFragment
import com.team_gdb.pentatonic.data.model.SessionSettingEntity
import com.team_gdb.pentatonic.data.session.SessionList
import com.team_gdb.pentatonic.databinding.DialogSelectSessionBinding
import com.team_gdb.pentatonic.ui.create_cover.CreateCoverViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class SelectSessionBottomSheetDialog() :
    BaseBottomSheetDialogFragment<DialogSelectSessionBinding, CreateCoverViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.dialog_select_session
    override val viewModel: CreateCoverViewModel by sharedViewModel()

    private lateinit var soloSessionListAdapter: SelectBandSessionListAdapter

    override fun initStartView() {
        binding.viewModel = this.viewModel
    }

    override fun initDataBinding() {
    }

    override fun initAfterBinding() {
        soloSessionListAdapter = SelectBandSessionListAdapter { session ->
            val sessionConfigList = viewModel.coverSessionConfigList.value?.toMutableList()
            sessionConfigList?.add(SessionSettingEntity(session, 1))
            viewModel.coverSessionConfigList.value = sessionConfigList?.toList()
            dismiss()
        }

        binding.sessionList.apply {
            this.layoutManager = LinearLayoutManager(context)
            adapter = soloSessionListAdapter
            setHasFixedSize(true)
        }

        soloSessionListAdapter.setItem(SessionList.sessionList)
    }
}