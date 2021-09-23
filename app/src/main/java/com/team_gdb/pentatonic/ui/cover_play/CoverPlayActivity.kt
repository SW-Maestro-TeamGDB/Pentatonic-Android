package com.team_gdb.pentatonic.ui.cover_play

import android.graphics.drawable.Drawable
import androidx.core.graphics.drawable.toBitmap
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.team_gdb.pentatonic.R
import com.team_gdb.pentatonic.base.BaseActivity
import com.team_gdb.pentatonic.data.model.CoverEntity
import com.team_gdb.pentatonic.databinding.ActivityCoverPlayBinding
import com.team_gdb.pentatonic.ui.lounge.LoungeFragment.Companion.COVER_ENTITY
import com.team_gdb.pentatonic.custom_view.ButtonState
import com.team_gdb.pentatonic.data.model.CoverPlayEntity
import com.team_gdb.pentatonic.media.ExoPlayerHelper
import com.team_gdb.pentatonic.media.ExoPlayerHelper.initPlayer
import com.team_gdb.pentatonic.ui.band_cover.SessionSelectBottomSheetDialog
import jp.wasabeef.blurry.Blurry
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class CoverPlayActivity : BaseActivity<ActivityCoverPlayBinding, CoverPlayingViewModel>() {
    override val layoutResourceId: Int = R.layout.activity_cover_play
    override val viewModel: CoverPlayingViewModel by viewModel()

    val coverEntity: CoverPlayEntity by lazy {
        intent.getSerializableExtra(COVER_PLAY_ENTITY) as CoverPlayEntity
    }

    override fun initStartView() {
        binding.viewModel = this.viewModel
        binding.lifecycleOwner = this

        viewModel.coverEntity.postValue(coverEntity)

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
    }

    override fun initDataBinding() {
        viewModel.commentList.observe(this) {
            binding.commentCount.text = it.size.toString()
            binding.commentButton.setOnClickListener {
                // 댓글 리스트를 표시하는 BottomSheetDialog 표시
                val bottomSheetDialog = CommentBottomSheetDialog()
                bottomSheetDialog.show(supportFragmentManager, bottomSheetDialog.tag)
            }
        }
    }

    override fun initAfterBinding() {
        viewModel.getComment(coverEntity.coverID)
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
    }
}
