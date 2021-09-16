package com.team_gdb.pentatonic.ui.session_select

import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.team_gdb.pentatonic.R
import com.team_gdb.pentatonic.adapter.select_session.SelectSessionListAdapter
import com.team_gdb.pentatonic.base.BaseActivity
import com.team_gdb.pentatonic.data.model.CoverEntity
import com.team_gdb.pentatonic.databinding.ActivitySessionSelectBinding
import com.team_gdb.pentatonic.ui.band_cover.BandCoverActivity
import com.team_gdb.pentatonic.ui.band_cover.BandCoverViewModel
import com.team_gdb.pentatonic.ui.band_cover.LibrarySelectBottomSheetDialog
import com.team_gdb.pentatonic.ui.lounge.LoungeFragment.Companion.COVER_ENTITY
import com.team_gdb.pentatonic.ui.profile.ProfileActivity

import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class SessionSelectActivity : BaseActivity<ActivitySessionSelectBinding, BandCoverViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.activity_session_select
    override val viewModel: BandCoverViewModel by viewModel()

    private val coverEntity: CoverEntity by lazy {
        intent.getSerializableExtra(COVER_ENTITY) as CoverEntity
    }
    private lateinit var sessionListAdapter: SelectSessionListAdapter


    @RequiresApi(Build.VERSION_CODES.N)
    override fun initStartView() {
        binding.viewModel = this.viewModel
        binding.lifecycleOwner = this

        sessionListAdapter = SelectSessionListAdapter { sessionData, userEntity ->
            viewModel.addSession(sessionName = sessionData.sessionName, userName = userEntity.username)
        }
        binding.sessionList.apply {
            this.layoutManager = LinearLayoutManager(context)
            this.adapter = sessionListAdapter
            this.setHasFixedSize(true)
        }
    }

    override fun initDataBinding() {
        viewModel.selectedSessionLiveData.observe(this) {
            Timber.d(it.toString())
        }
    }

    override fun initAfterBinding() {
        binding.backButton.setOnClickListener {
            finish()
        }
        sessionListAdapter.setItem(coverEntity.sessionDataList)
    }
}