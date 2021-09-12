package com.team_gdb.pentatonic.ui.solo_cover


import com.bumptech.glide.Glide
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.team_gdb.pentatonic.R
import com.team_gdb.pentatonic.base.BaseActivity
import com.team_gdb.pentatonic.data.model.CoverEntity
import com.team_gdb.pentatonic.databinding.ActivitySoloCoverBinding
import com.team_gdb.pentatonic.ui.lounge.LoungeFragment

class SoloCoverActivity : BaseActivity<ActivitySoloCoverBinding, SoloCoverViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.activity_solo_cover
    override val viewModel: SoloCoverViewModel by viewModel()

    private val coverEntity: CoverEntity by lazy {
        intent.getSerializableExtra(LoungeFragment.COVER_ENTITY) as CoverEntity
    }

    override fun initStartView() {
        binding.viewModel = this.viewModel
        binding.coverNameTextView.text = coverEntity.coverName
        binding.coverIntroductionTextView.text = coverEntity.introduction

        Glide.with(this)
            .load(coverEntity.imageUrl)
            .placeholder(R.drawable.placeholder_cover_bg)
            .override(480, 272)
            .into(binding.coverImage)

        binding.coverLikeTextView.text = coverEntity.like.toString()
        binding.coverViewTextView.text = coverEntity.view.toString()
    }

    override fun initDataBinding() {
    }

    override fun initAfterBinding() {
    }
}