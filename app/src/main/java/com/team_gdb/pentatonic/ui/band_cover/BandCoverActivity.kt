package com.team_gdb.pentatonic.ui.band_cover

import android.content.Intent
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.team_gdb.pentatonic.GetBandCoverInfoQuery
import com.team_gdb.pentatonic.R
import com.team_gdb.pentatonic.adapter.cover_view.SessionConfigListAdapter
import com.team_gdb.pentatonic.base.BaseActivity
import com.team_gdb.pentatonic.data.model.CoverEntity
import com.team_gdb.pentatonic.data.model.CoverPlayEntity
import com.team_gdb.pentatonic.databinding.ActivityBandCoverBinding
import com.team_gdb.pentatonic.ui.cover_play.CoverPlayActivity
import com.team_gdb.pentatonic.ui.cover_play.CoverPlayActivity.Companion.COVER_PLAY_ENTITY
import com.team_gdb.pentatonic.ui.lounge.LoungeFragment.Companion.COVER_ENTITY
import com.team_gdb.pentatonic.ui.lounge.LoungeFragment.Companion.COVER_ID
import com.team_gdb.pentatonic.ui.profile.ProfileActivity
import com.team_gdb.pentatonic.ui.profile.ProfileActivity.Companion.USER_ID
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class BandCoverActivity : BaseActivity<ActivityBandCoverBinding, BandCoverViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.activity_band_cover
    override val viewModel: BandCoverViewModel by viewModel()

    private val coverID: String by lazy {
        intent.getStringExtra(COVER_ID) as String
    }

    private lateinit var sessionListAdapter: SessionConfigListAdapter

    override fun initStartView() {
        binding.viewModel = this.viewModel

        viewModel.getBandInfoQuery(coverID)
    }

    override fun initDataBinding() {
        viewModel.bandInfo.observe(this) {
            Timber.d(it.toString())
            applyBandInfoOnView(it)
        }

        // 사용자 요청에 의해 병합된 커버 URL 옵저빙
        viewModel.mergedCoverURL.observe(this) {
            val coverEntity = CoverPlayEntity(
                coverID = viewModel.bandInfo.value!!.bandId,
                coverName = viewModel.bandInfo.value!!.name,
                backgroundImgURL = viewModel.bandInfo.value!!.backGroundURI,
                coverIntroduction = viewModel.bandInfo.value!!.introduce,
                likeCount = viewModel.bandInfo.value!!.likeCount,
                viewCount = 34,
                coverURL = it
            )
            val intent = Intent(this, CoverPlayActivity::class.java)
            intent.putExtra(COVER_PLAY_ENTITY, coverEntity)
            startActivity(intent)
        }
    }

    override fun initAfterBinding() {
        binding.backButton.setOnClickListener {
            finish()
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
            intent.putExtra(USER_ID, it)
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

        binding.bandPlayButton.setOnClickListener {
            val bottomSheetDialog = SessionSelectBottomSheetDialog()
            bottomSheetDialog.show(supportFragmentManager, bottomSheetDialog.tag)
        }
    }
}