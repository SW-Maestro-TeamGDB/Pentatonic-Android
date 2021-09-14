package com.team_gdb.pentatonic.ui.solo_cover


import android.content.Intent
import com.bumptech.glide.Glide
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.team_gdb.pentatonic.R
import com.team_gdb.pentatonic.base.BaseActivity
import com.team_gdb.pentatonic.data.model.CoverEntity
import com.team_gdb.pentatonic.databinding.ActivitySoloCoverBinding
import com.team_gdb.pentatonic.ui.cover_play.CoverPlayActivity
import com.team_gdb.pentatonic.ui.lounge.LoungeFragment
import com.team_gdb.pentatonic.ui.lounge.LoungeFragment.Companion.COVER_ENTITY

class SoloCoverActivity : BaseActivity<ActivitySoloCoverBinding, SoloCoverViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.activity_solo_cover
    override val viewModel: SoloCoverViewModel by viewModel()

    private val coverEntity: CoverEntity by lazy {
        intent.getSerializableExtra(COVER_ENTITY) as CoverEntity
    }

    override fun initStartView() {
        binding.viewModel = this.viewModel
        binding.coverNameTextView.text = coverEntity.coverName
        binding.coverIntroductionTextView.text = coverEntity.introduction

        binding.coverLikeTextView.text = coverEntity.like.toString()
        binding.coverViewTextView.text = coverEntity.view.toString()

        Glide.with(this)
            .load(coverEntity.imageUrl)
            .placeholder(R.drawable.placeholder_cover_bg)
            .override(480, 272)
            .into(binding.coverImage)

        // 솔로 커버는 세션 구성원이 1명인 밴드 커버로 취급됨. 따라서 아래와 같이 필드를 접근하면 됨.
        Glide.with(this)
            .load(coverEntity.sessionDataList[0].sessionParticipantList[0].introduction)
            .placeholder(R.drawable.profile_image_placeholder)
            .override(80, 80)
            .into(binding.userProfileImage)

        binding.userNameTextView.text =
            coverEntity.sessionDataList[0].sessionParticipantList[0].username
        binding.userIntroductionTextView.text =
            coverEntity.sessionDataList[0].sessionParticipantList[0].introduction

    }

    override fun initDataBinding() {
    }

    override fun initAfterBinding() {
        binding.coverPlayButton.setOnClickListener {
            val intent = Intent(this, CoverPlayActivity::class.java)
            intent.putExtra(COVER_ENTITY, coverEntity)
            startActivity(intent)
        }

    }
}