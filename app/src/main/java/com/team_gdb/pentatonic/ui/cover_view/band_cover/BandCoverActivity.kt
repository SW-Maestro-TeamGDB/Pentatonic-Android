package com.team_gdb.pentatonic.ui.cover_view.band_cover

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.materialdialogs.MaterialDialog
import com.bumptech.glide.Glide
import com.like.LikeButton
import com.like.OnLikeListener
import com.team_gdb.pentatonic.GetBandCoverInfoQuery
import com.team_gdb.pentatonic.R
import com.team_gdb.pentatonic.adapter.cover_view.SessionConfigListAdapter
import com.team_gdb.pentatonic.base.BaseActivity
import com.team_gdb.pentatonic.base.BaseApplication
import com.team_gdb.pentatonic.data.model.CoverPlayEntity
import com.team_gdb.pentatonic.databinding.ActivityBandCoverBinding
import com.team_gdb.pentatonic.ui.cover_play.CoverPlayActivity
import com.team_gdb.pentatonic.ui.cover_play.CoverPlayActivity.Companion.COVER_PLAY_ENTITY
import com.team_gdb.pentatonic.ui.cover_view.CoverViewViewModel
import com.team_gdb.pentatonic.ui.lounge.LoungeFragment.Companion.COVER_ID
import com.team_gdb.pentatonic.ui.profile.ProfileActivity
import com.team_gdb.pentatonic.ui.profile.ProfileActivity.Companion.USER_ID
import com.team_gdb.pentatonic.ui.record_processing.RecordProcessingActivity.Companion.CREATE_COVER
import com.team_gdb.pentatonic.util.PlayAnimation.playFailureAlert
import com.team_gdb.pentatonic.util.PlayAnimation.playSuccessAlert
import es.dmoral.toasty.Toasty
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class BandCoverActivity : BaseActivity<ActivityBandCoverBinding, CoverViewViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.activity_band_cover
    override val viewModel: CoverViewViewModel by viewModel()

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

        // ????????? ????????? ?????? ????????? ?????? URL ?????????
        viewModel.mergedCoverURL.observe(this) {
            val coverEntity = CoverPlayEntity(
                coverID = viewModel.bandInfo.value!!.bandId,
                coverName = viewModel.bandInfo.value!!.name,
                backgroundImgURL = viewModel.bandInfo.value!!.backGroundURI,
                coverIntroduction = viewModel.bandInfo.value!!.introduce,
                likeCount = viewModel.bandInfo.value!!.likeCount,
                viewCount = viewModel.bandInfo.value!!.viewCount,
                likeStatus = viewModel.bandInfo.value!!.likeStatus!!,
                coverURL = it
            )
            val intent = Intent(this, CoverPlayActivity::class.java)
            intent.putExtra(COVER_PLAY_ENTITY, coverEntity)
            startActivity(intent)

            viewModel.clearSession()  // ?????? ?????? ?????? ?????????
        }

        // ?????? ?????? ?????? ????????? ?????? ????????? ?????????
        viewModel.joinBandEvent.observe(this) {
            if (it.getContentIfNotHandled() == true) {
                playSuccessAlert(this, "?????? ????????? ?????????????????????!")
                viewModel.getBandInfoQuery(coverID)
            } else playFailureAlert(this, "?????? ?????? ?????? ????????? ??????????????????")
        }

        // ?????? ?????? ?????? ????????? ?????? ????????? ?????????
        viewModel.deleteBandEvent.observe(this) {
            if (it.getContentIfNotHandled() == true) {
                Toast.makeText(this, "????????? ?????????????????????!", Toast.LENGTH_LONG).show()
                finish()
            }
        }

        // ?????? ?????? ?????? ????????? ?????? ????????? ?????????
        viewModel.deleteCoverEvent.observe(this) {
            if (it.getContentIfNotHandled() == true) {
                playSuccessAlert(this, "?????? ????????? ?????????????????????!")
                viewModel.getBandInfoQuery(coverID)
            } else playFailureAlert(this, "?????? ?????? ?????? ????????? ??????????????????")
        }
    }

    override fun initAfterBinding() {
        // ?????? ?????? ??? ????????? ??????????????? ?????? ?????? ?????? ??????????????? ??????
        if (intent.getStringExtra(CREATE_COVER) != null) {
            playSuccessAlert(this, "????????? ??????????????? ?????????????????????!")
        }

        binding.backButton.setOnClickListener {
            finish()
        }

        binding.coverLikeButton.setOnLikeListener(object : OnLikeListener {
            override fun liked(likeButton: LikeButton?) {
                viewModel.likeBand()
                playSuccessAlert(this@BandCoverActivity, "???????????? ?????????????????????!")
            }

            override fun unLiked(likeButton: LikeButton?) {
                viewModel.likeBand()
                playFailureAlert(this@BandCoverActivity, "???????????? ?????????????????????")
            }
        })
    }

    private fun applyBandInfoOnView(bandInfo: GetBandCoverInfoQuery.GetBand) {

        binding.coverSongInfoTextView.text = "${bandInfo.song.name} - ${bandInfo.song.artist}"
        binding.coverNameTextView.text = bandInfo.name
        binding.coverIntroductionTextView.text = bandInfo.introduce

        binding.coverLikeButton.isLiked = bandInfo.likeStatus == true

        Glide.with(this)
            .load(bandInfo.backGroundURI)
            .placeholder(R.drawable.placeholder_cover_bg)
            .override(480, 272)
            .into(binding.coverImage)

        binding.coverLikeTextView.text = bandInfo.likeCount.toString()
        binding.coverViewTextView.text = bandInfo.viewCount.toString()


        // ???????????? ?????? ?????? ????????? ?????? ??????, ?????? ?????? ?????????
        if (bandInfo.creator.id == BaseApplication.prefs.userId) {
            binding.moreButton.visibility = View.VISIBLE
        }
        binding.moreButton.setOnClickListener {
            var pop = PopupMenu(this, binding.moreButton)
            menuInflater.inflate(R.menu.band_creator_menu, pop.menu)
            pop.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.action_edit -> {

                    }
                    R.id.action_delete -> {
                        showBandDeleteDialog()
                    }
                }
                false
            }
            pop.show()
        }

        sessionListAdapter = SessionConfigListAdapter(
            // ?????? ????????? ?????? ?????? ?????????
            bandInfo.creator.id,
            {
                // ????????? ??????
                val intent = Intent(this, ProfileActivity::class.java)
                intent.putExtra(USER_ID, it)
                startActivity(intent)
            },
            {
                // ?????? ??????
                val bottomSheetDialog = LibrarySelectBottomSheetDialog()
                bottomSheetDialog.arguments = Bundle().apply {
                    putString(SESSION_TYPE, it.position.rawValue)
                }
                bottomSheetDialog.show(supportFragmentManager, bottomSheetDialog.tag)
            },
            { coverId, coverBy ->
                // ?????? ?????? ??? ?????????
                if ((bandInfo.creator.id == BaseApplication.prefs.userId) || (coverBy == BaseApplication.prefs.userId)) {
                    showCoverDeleteDialog(coverId)
                }
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

    private fun showCoverDeleteDialog(coverId: String) {
        MaterialDialog(this).show {
            message(R.string.cover_delete_notice_title)
            positiveButton(R.string.yes_text) {
                viewModel.leaveBand(coverId)
            }
            negativeButton(R.string.no_text) {
                /* no-op */
            }
        }
    }

    private fun showBandDeleteDialog() {
        MaterialDialog(this).show {
            title(R.string.cover_delete_notice_title)
            message(R.string.cover_delete_notice_content)
            positiveButton(R.string.yes_text) {
                viewModel.deleteBand()
                Toasty.success(context, "????????? ?????????????????????!", Toast.LENGTH_SHORT, true).show()
                finish()
            }
            negativeButton(R.string.no_text) {
                /* no-op */
            }
        }
    }

    companion object {
        const val SESSION_TYPE = "session_type"
    }
}