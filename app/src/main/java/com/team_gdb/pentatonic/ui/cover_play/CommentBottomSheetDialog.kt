package com.team_gdb.pentatonic.ui.cover_play

import android.graphics.drawable.Drawable
import androidx.core.graphics.drawable.toBitmap
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.team_gdb.pentatonic.R
import com.team_gdb.pentatonic.adapter.comment.CommentListAdapter
import com.team_gdb.pentatonic.adapter.create_cover.SelectBandSessionListAdapter
import com.team_gdb.pentatonic.base.BaseBottomSheetDialogFragment
import com.team_gdb.pentatonic.data.model.SessionSettingEntity
import com.team_gdb.pentatonic.data.session.SessionData
import com.team_gdb.pentatonic.databinding.DialogCommentBinding
import com.team_gdb.pentatonic.databinding.DialogSelectSessionBinding
import com.team_gdb.pentatonic.ui.band_cover.BandCoverActivity
import com.team_gdb.pentatonic.ui.band_cover.BandCoverViewModel
import com.team_gdb.pentatonic.ui.cover_play.CoverPlayActivity.Companion.BACKGROUND_IMG
import com.team_gdb.pentatonic.ui.create_cover.CreateCoverViewModel
import jp.wasabeef.blurry.Blurry
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class CommentBottomSheetDialog() :
    BaseBottomSheetDialogFragment<DialogCommentBinding, CoverPlayingViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.dialog_comment
    override val viewModel: CoverPlayingViewModel by sharedViewModel()

    private val backgroundImgURL: String by lazy {
        arguments?.getString(BACKGROUND_IMG) as String
    }

    private lateinit var commentListAdapter: CommentListAdapter

    override fun initStartView() {
        binding.viewModel = this.viewModel

        commentListAdapter = CommentListAdapter()

        binding.commentList.apply {
            this.layoutManager = LinearLayoutManager(context)
            adapter = commentListAdapter
            setHasFixedSize(true)
        }
    }

    override fun initDataBinding() {
        viewModel.commentList.observe(this) {
            commentListAdapter.setItem(it)
        }
    }

    override fun initAfterBinding() {
        Glide.with(binding.root)
            .load(backgroundImgURL)
            .centerCrop()
            .placeholder(R.drawable.placeholder_cover_bg)
            .listener(glideLoadingListener)
            .into(binding.commentBackgroundImageView)
    }

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
            Blurry.with(context)
                .radius(20)
                .sampling(10)
                .async()
                .animate(500)
                .from(resource?.toBitmap())
                .into(binding.commentBackgroundImageView)
            return false
        }
    }
}