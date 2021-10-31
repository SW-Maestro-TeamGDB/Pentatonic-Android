package com.team_gdb.pentatonic.ui.cover_play

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.FrameLayout
import androidx.core.graphics.drawable.toBitmap
import com.afollestad.materialdialogs.utils.MDUtil.isLandscape
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.like.LikeButton
import com.like.OnLikeListener
import com.team_gdb.pentatonic.R
import com.team_gdb.pentatonic.base.BaseActivity
import com.team_gdb.pentatonic.databinding.ActivityCoverPlayBinding
import com.team_gdb.pentatonic.data.model.CoverPlayEntity
import com.team_gdb.pentatonic.media.ExoPlayerHelper
import com.team_gdb.pentatonic.media.ExoPlayerHelper.initPlayer
import com.team_gdb.pentatonic.ui.lounge.LoungeFragment.Companion.COVER_ENTITY
import com.team_gdb.pentatonic.util.PlayAnimation
import jp.wasabeef.blurry.Blurry
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber
import java.lang.Integer.parseInt

class CoverPlayActivity : BaseActivity<ActivityCoverPlayBinding, CoverPlayingViewModel>() {
    override val layoutResourceId: Int = R.layout.activity_cover_play
    override val viewModel: CoverPlayingViewModel by viewModel()

    val coverEntity: CoverPlayEntity by lazy {
        intent.getSerializableExtra(COVER_PLAY_ENTITY) as CoverPlayEntity
    }

    override fun initStartView() {
        binding.viewModel = this.viewModel
        binding.lifecycleOwner = this

        viewModel.setCoverEntity(coverEntity)

        binding.coverLikeButton.isLiked = coverEntity.likeStatus == true

        Glide.with(binding.root)
            .load(coverEntity.backgroundImgURL)
            .centerCrop()
            .placeholder(R.drawable.placeholder_cover_bg)
            .listener(glideLoadingListener)
            .into(binding.coverBackgroundImageView)

        initPlayer(coverEntity.coverURL) {
            Timber.d("Play Complete")
        }
        binding.playerView.player = ExoPlayerHelper.player

        binding.likeCountTextView.text = coverEntity.likeCount.toString()
        binding.viewCountTextView.text = coverEntity.viewCount.toString()
    }

    override fun initDataBinding() {
        viewModel.commentList.observe(this) {
            binding.commentCount.text = it.size.toString()
            binding.commentButton.setOnClickListener {
                // 댓글 리스트를 표시하는 BottomSheetDialog 표시
                val bottomSheetDialog = CommentBottomSheetDialog()
                bottomSheetDialog.arguments = Bundle().apply {
                    putSerializable(COVER_ENTITY, coverEntity)
                }
                bottomSheetDialog.show(supportFragmentManager, bottomSheetDialog.tag)
            }
        }
    }

    override fun initAfterBinding() {
        viewModel.getComment(coverEntity.coverID)

        binding.backButton.setOnClickListener {
            finish()
        }

        binding.coverLikeButton.setOnLikeListener(object : OnLikeListener {
            override fun liked(likeButton: LikeButton?) {
                viewModel.likeBand()
                PlayAnimation.playSuccessAlert(this@CoverPlayActivity, "좋아요가 반영되었습니다!")
                binding.likeCountTextView.text = (parseInt(binding.likeCountTextView.text.toString()) + 1).toString()
            }

            override fun unLiked(likeButton: LikeButton?) {
                viewModel.likeBand()
                PlayAnimation.playFailureAlert(this@CoverPlayActivity, "좋아요가 취소되었습니다")
                binding.likeCountTextView.text = (parseInt(binding.likeCountTextView.text.toString()) - 1).toString()
            }
        })
    }

    override fun onDestroy() {
        ExoPlayerHelper.stopPlaying()
        super.onDestroy()
    }

    /**
     * Glide 가 앨범 자켓 이미지를 로드 완료 했을때, 해당 이미지 리소스를 통해
     * Blurry 로 이미지 블러 처리를 수행해야 올바른 결과가 표시됨
     */
    private val glideLoadingListener = object : RequestListener<Drawable> {
        override fun onLoadFailed(
            e: GlideException?,
            model: Any?,
            target: Target<Drawable>?,
            isFirstResource: Boolean
        ): Boolean {
            return false
        }

        override fun onResourceReady(
            resource: Drawable?,
            model: Any?,
            target: Target<Drawable>?,
            dataSource: DataSource?,
            isFirstResource: Boolean
        ): Boolean {
            Blurry.with(applicationContext)
                .radius(5)
                .sampling(5)
                .async()
                .animate(500)
                .from(resource?.toBitmap())
                .into(binding.backgroundImageView)
            return false
        }
    }

    companion object {
        const val COVER_PLAY_ENTITY = "COVER_PLAY_ENTITY"
        const val BACKGROUND_IMG = "BACKGROUND_IMG"
    }
}
