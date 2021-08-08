package com.team_gdb.pentatonic.ui.create_cover

import androidx.recyclerview.widget.LinearLayoutManager
import com.team_gdb.pentatonic.R
import com.team_gdb.pentatonic.adapter.SelectSessionListAdapter
import com.team_gdb.pentatonic.base.BaseBottomSheetDialogFragment
import com.team_gdb.pentatonic.data.model.SongEntity
import com.team_gdb.pentatonic.databinding.DialogSelectSessionBinding
import com.team_gdb.pentatonic.ui.select_song.SelectSongViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class SelectSessionBottomSheetDialog(val songEntity: SongEntity) :
    BaseBottomSheetDialogFragment<DialogSelectSessionBinding, SelectSongViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.dialog_select_session
    override val viewModel: SelectSongViewModel by sharedViewModel()

    private lateinit var sessionListAdapter: SelectSessionListAdapter

    override fun initStartView() {
        binding.viewModel = this.viewModel
    }

    override fun initDataBinding() {
    }

    override fun initAfterBinding() {
        sessionListAdapter = SelectSessionListAdapter {
        }

        binding.sessionList.apply {
            this.layoutManager = LinearLayoutManager(context)
            adapter = sessionListAdapter
            setHasFixedSize(true)
        }

        sessionListAdapter.setItem(SessionData.sessionData)
    }
}