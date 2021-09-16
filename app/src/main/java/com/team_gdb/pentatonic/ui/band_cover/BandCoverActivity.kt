package com.team_gdb.pentatonic.ui.band_cover

import android.content.Intent
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.team_gdb.pentatonic.GetBandCoverInfoQuery
import com.team_gdb.pentatonic.R
import com.team_gdb.pentatonic.adapter.cover_view.SessionConfigListAdapter
import com.team_gdb.pentatonic.base.BaseActivity
import com.team_gdb.pentatonic.data.model.CoverEntity
import com.team_gdb.pentatonic.databinding.ActivityBandCoverBinding
import com.team_gdb.pentatonic.ui.lounge.LoungeFragment.Companion.COVER_ENTITY
import com.team_gdb.pentatonic.ui.profile.ProfileActivity
import com.team_gdb.pentatonic.ui.session_select.SessionSelectActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class BandCoverActivity : BaseActivity<ActivityBandCoverBinding, BandCoverViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.activity_band_cover
    override val viewModel: BandCoverViewModel by viewModel()

    private val coverEntity: CoverEntity by lazy {
        intent.getSerializableExtra(COVER_ENTITY) as CoverEntity
    }
    private lateinit var sessionListAdapter: SessionConfigListAdapter

    override fun initStartView() {
        binding.viewModel = this.viewModel

        viewModel.getBandInfoQuery("6141f76aa58e6e0014b27e69")

    }

    override fun initDataBinding() {
        viewModel.bandInfo.observe(this) {
            Timber.d(it.toString())
            applyBandInfoOnView(it)
        }
    }

    override fun initAfterBinding() {

        binding.backButton.setOnClickListener {
            finish()
        }

        binding.bandPlayButton.setOnClickListener {
            val intent = Intent(this, SessionSelectActivity::class.java)
            intent.putExtra(COVER_ENTITY, coverEntity)
            startActivity(intent)
        }
    }

    private fun applyBandInfoOnView(bandInfo: GetBandCoverInfoQuery.GetBand) {
        binding.coverNameTextView.text = bandInfo.name
        binding.coverIntroductionTextView.text = bandInfo.introduce

        Glide.with(this)
            .load(bandInfo.backGroundURI)
            .placeholder(R.drawable.placeholder_cover_bg)
            .override(480, 272)
            .into(binding.coverImage)

        binding.coverLikeTextView.text = bandInfo.likeCount.toString()
        binding.coverViewTextView.text = "35"  // TODO : API 미구현

        sessionListAdapter = SessionConfigListAdapter({
            val intent = Intent(this, ProfileActivity::class.java)
            intent.putExtra(USER_ENTITY, it)
            startActivity(intent)
        }, {
            val bottomSheetDialog = LibrarySelectBottomSheetDialog()
            bottomSheetDialog.show(supportFragmentManager, bottomSheetDialog.tag)
        })
        binding.sessionList.apply {
            this.layoutManager = LinearLayoutManager(context)
            this.adapter = sessionListAdapter
            this.setHasFixedSize(true)
        }

        bandInfo.session?.let {
            sessionListAdapter.setItem(it)
        }
    }

    companion object {
        const val USER_ENTITY = "USER_ENTITY"
    }
}