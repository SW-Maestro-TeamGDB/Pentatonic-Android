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

        // 밴드 참여 성공 여부를 담는 이벤트 옵저빙
        viewModel.joinBandEvent.observe(this) {
            if (it.getContentIfNotHandled() == true) {
                playSuccessAlert(this, "밴드 참여가 완료되었습니다!")
                viewModel.getBandInfoQuery(coverID)
            } else playFailureAlert(this, "밴드 참여 도중 오류가 발생했습니다")
        }

        // 밴드 삭제 성공 여부를 담는 이벤트 옵저빙
        viewModel.deleteBandEvent.observe(this) {
            if (it.getContentIfNotHandled() == true) {
                Toast.makeText(this, "밴드가 삭제되었습니다!", Toast.LENGTH_LONG).show()
                finish()
            }
        }
    }

    override fun initAfterBinding() {
        // 커버 생성 후 넘어온 상황이라면 커버 생성 완료 애니메이션 재생
        if (intent.getStringExtra(CREATE_COVER) != null) {
            playSuccessAlert(this, "커버가 정상적으로 생성되었습니다!")
        }

        binding.backButton.setOnClickListener {
            finish()
        }

        binding.coverLikeButton.setOnLikeListener(object : OnLikeListener {
            override fun liked(likeButton: LikeButton?) {
                viewModel.likeBand()
                playSuccessAlert(this@BandCoverActivity, "좋아요가 반영되었습니다!")
            }

            override fun unLiked(likeButton: LikeButton?) {
                viewModel.likeBand()
                playFailureAlert(this@BandCoverActivity, "좋아요가 취소되었습니다")
            }
        })
    }

    private fun applyBandInfoOnView(bandInfo: GetBandCoverInfoQuery.GetBand) {
        binding.coverNameTextView.text = bandInfo.name
        binding.coverIntroductionTextView.text = bandInfo.introduce

        binding.coverLikeButton.isLiked = bandInfo.likeStatus == true

        Glide.with(this)
            .load(bandInfo.backGroundURI)
            .placeholder(R.drawable.placeholder_cover_bg)
            .override(480, 272)
            .into(binding.coverImage)

        binding.coverLikeTextView.text = bandInfo.likeCount.toString()
        binding.coverViewTextView.text = "35"  // TODO : API 미구현


        // 사용자가 해당 밴드 방장일 경우 편집, 삭제 버튼 활성화
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
                        showDeleteDialog()
                    }
                }
                false
            }
            pop.show()
        }

        sessionListAdapter = SessionConfigListAdapter(
            bandInfo.creator.id, {
                val intent = Intent(this, ProfileActivity::class.java)
                intent.putExtra(USER_ID, it)
                startActivity(intent)
            }, {
                val bottomSheetDialog = LibrarySelectBottomSheetDialog()
                bottomSheetDialog.arguments = Bundle().apply {
                    putString(SESSION_TYPE, it.position.rawValue)
                }
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

    private fun showDeleteDialog() {
        MaterialDialog(this).show {
            title(R.string.band_delete_notice_title)
            message(R.string.band_delete_notice_content)
            positiveButton(R.string.yes_text) {
                viewModel.deleteBand()
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