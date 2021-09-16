package com.team_gdb.pentatonic.ui.session_select

import android.os.Build
import androidx.annotation.RequiresApi
import com.team_gdb.pentatonic.R
import com.team_gdb.pentatonic.adapter.select_session.SelectSessionListAdapter
import com.team_gdb.pentatonic.base.BaseBottomSheetDialogFragment
import com.team_gdb.pentatonic.databinding.DialogBandSessionSelectBinding
import com.team_gdb.pentatonic.ui.band_cover.BandCoverViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

import timber.log.Timber

class SessionSelectBottomSheetDialog : BaseBottomSheetDialogFragment<DialogBandSessionSelectBinding, BandCoverViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.dialog_band_session_select
    override val viewModel: BandCoverViewModel by sharedViewModel()

    private lateinit var sessionListAdapter: SelectSessionListAdapter


    @RequiresApi(Build.VERSION_CODES.N)
    override fun initStartView() {
        binding.viewModel = this.viewModel

//        sessionListAdapter = SelectSessionListAdapter { sessionData, userEntity ->
//            viewModel.addSession(sessionName = sessionData.sessionName, userName = userEntity.username)
//        }
//        binding.sessionList.apply {
//            this.layoutManager = LinearLayoutManager(context)
//            this.adapter = sessionListAdapter
//            this.setHasFixedSize(true)
//        }
    }

    override fun initDataBinding() {
        viewModel.bandInfo.observe(this) {
            Timber.d(it.toString())
        }
        viewModel.selectedSessionLiveData.observe(this) {
            Timber.d(it.toString())
        }
    }

    override fun initAfterBinding() {
//        sessionListAdapter.setItem(coverEntity.sessionDataList)
    }
}