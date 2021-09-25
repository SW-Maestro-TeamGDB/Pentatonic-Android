package com.team_gdb.pentatonic.ui.cover_play

import android.app.Activity
import android.content.Context
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.team_gdb.pentatonic.R
import com.team_gdb.pentatonic.adapter.comment.CommentListAdapter
import com.team_gdb.pentatonic.base.BaseBottomSheetDialogFragment
import com.team_gdb.pentatonic.data.model.CoverEntity
import com.team_gdb.pentatonic.data.model.CoverPlayEntity
import com.team_gdb.pentatonic.databinding.DialogCommentBinding
import com.team_gdb.pentatonic.ui.cover_play.CoverPlayActivity.Companion.BACKGROUND_IMG
import com.team_gdb.pentatonic.ui.lounge.LoungeFragment.Companion.COVER_ENTITY
import com.team_gdb.pentatonic.util.PlayAnimation.playSuccessAlert
import jp.wasabeef.blurry.Blurry
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.ContextCompat.getSystemService
import com.afollestad.materialdialogs.MaterialDialog
import com.team_gdb.pentatonic.GetCoverCommentQuery
import com.team_gdb.pentatonic.base.BaseApplication


class CommentBottomSheetDialog() :
    BaseBottomSheetDialogFragment<DialogCommentBinding, CoverPlayingViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.dialog_comment
    override val viewModel: CoverPlayingViewModel by sharedViewModel()

    private val coverEntity: CoverPlayEntity by lazy {
        arguments?.getSerializable(COVER_ENTITY) as CoverPlayEntity
    }

    private lateinit var commentListAdapter: CommentListAdapter

    override fun initStartView() {
        binding.viewModel = this.viewModel
        binding.lifecycleOwner = this

        commentListAdapter = CommentListAdapter(
            { commentId, content ->
                // 댓글 수정
                viewModel.updateComment(commentId, content)
            },
            { commentId ->
                // 댓글 삭제
                showDeleteCommentDialog(commentId)
            }
        )

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

        viewModel.createCommentComplete.observe(this) {
            if (it.getContentIfNotHandled() == true) {
                playSuccessAlert(activity as Activity, "댓글 작성이 완료되었습니다!")
                viewModel.getComment(coverEntity.coverID)
            } else {
                playSuccessAlert(activity as Activity, "댓글 작성 도중에 오류가 발생했습니다")
            }
        }

        viewModel.updateCommentComplete.observe(this) {
            if (it.getContentIfNotHandled() == true) {
                playSuccessAlert(activity as Activity, "댓글 수정이 완료되었습니다!")
                viewModel.getComment(coverEntity.coverID)
            } else {
                playSuccessAlert(activity as Activity, "댓글 수정 도중에 오류가 발생했습니다")
            }
        }

        viewModel.deleteCommentComplete.observe(this) {
            if (it.getContentIfNotHandled() == true) {
                playSuccessAlert(activity as Activity, "댓글 삭제가 완료되었습니다!")
                viewModel.getComment(coverEntity.coverID)
            } else {
                playSuccessAlert(activity as Activity, "댓글 삭제 도중에 오류가 발생했습니다")
            }
        }
    }

    override fun initAfterBinding() {
        Glide.with(binding.root)
            .load(coverEntity.backgroundImgURL)
            .centerCrop()
            .placeholder(R.drawable.placeholder_cover_bg)
            .listener(glideLoadingListener)
            .into(binding.commentBackgroundImageView)

        binding.closeButton.setOnClickListener {
            dismiss()
        }

        binding.confirmCommentButton.setOnClickListener {
            viewModel.createComment(coverEntity.coverID)
        }
    }

    private fun showDeleteCommentDialog(commentId: String) {
        MaterialDialog(requireContext()).show {
            message(R.string.comment_delete_notice_content)
            positiveButton(R.string.yes_text) {
                viewModel.deleteComment(commentId)
            }
            negativeButton(R.string.no_text) {
                /* no-op */
            }
        }
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