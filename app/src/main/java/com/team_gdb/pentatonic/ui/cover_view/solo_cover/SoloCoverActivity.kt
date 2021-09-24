package com.team_gdb.pentatonic.ui.cover_view.solo_cover


import android.content.Intent
import android.view.View
import android.widget.PopupMenu
import com.afollestad.materialdialogs.MaterialDialog
import com.bumptech.glide.Glide
import com.team_gdb.pentatonic.GetBandCoverInfoQuery
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.team_gdb.pentatonic.R
import com.team_gdb.pentatonic.base.BaseActivity
import com.team_gdb.pentatonic.base.BaseApplication
import com.team_gdb.pentatonic.data.session.SessionData
import com.team_gdb.pentatonic.data.session.SessionSetting
import com.team_gdb.pentatonic.databinding.ActivitySoloCoverBinding
import com.team_gdb.pentatonic.ui.cover_view.CoverViewViewModel
import com.team_gdb.pentatonic.ui.cover_play.CoverPlayActivity
import com.team_gdb.pentatonic.ui.lounge.LoungeFragment.Companion.COVER_ID
import com.team_gdb.pentatonic.ui.profile.ProfileActivity
import timber.log.Timber

class SoloCoverActivity : BaseActivity<ActivitySoloCoverBinding, CoverViewViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.activity_solo_cover
    override val viewModel: CoverViewViewModel by viewModel()

    private val coverID: String by lazy {
        intent.getStringExtra(COVER_ID) as String
    }

    override fun initStartView() {
        binding.viewModel = this.viewModel

        viewModel.getBandInfoQuery(coverID)
    }

    override fun initDataBinding() {
        viewModel.bandInfo.observe(this) {
            Timber.d(it.toString())
            applyBandInfoOnView(it)
        }
    }

    override fun initAfterBinding() {
        binding.coverPlayButton.setOnClickListener {
            val intent = Intent(this, CoverPlayActivity::class.java)
//            intent.putExtra(COVER_ENTITY, coverEntity)
            startActivity(intent)
        }

        binding.userProfileLayout.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
//            intent.putExtra(USER_ID, coverEntity.sessionDataList[0].sessionParticipantList[0])
            startActivity(intent)
        }

    }

    private fun applyBandInfoOnView(bandInfo: GetBandCoverInfoQuery.GetBand) {
        binding.coverNameTextView.text = bandInfo.name
        binding.coverIntroductionTextView.text = bandInfo.introduce

        binding.coverLikeButton.isLiked = bandInfo.likeStatus == true

        binding.userNameTextView.text = bandInfo.creator.username
//        binding.userIntroductionTextView.text = bandInfo.creator.introduce

        Glide.with(this)
            .load(bandInfo.backGroundURI)
            .placeholder(R.drawable.placeholder_cover_bg)
            .override(480, 272)
            .into(binding.coverImage)

        Glide.with(this)
            .load(bandInfo.creator.profileURI)
            .override(200, 200)
            .into(binding.userProfileImage)

        Glide.with(this)
            .load(SessionSetting.valueOf(bandInfo.session?.get(0)!!.position.rawValue).icon)
            .override(480, 272)
            .into(binding.coverSessionImageView)

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

        binding.coverPlayButton.setOnClickListener {
            // TODO : 솔로커버 재생
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
}