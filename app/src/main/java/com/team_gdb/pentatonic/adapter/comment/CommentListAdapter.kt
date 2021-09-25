package com.team_gdb.pentatonic.adapter.comment

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.recyclerview.widget.RecyclerView
import com.afollestad.materialdialogs.MaterialDialog
import com.bumptech.glide.Glide
import com.team_gdb.pentatonic.GetCoverCommentQuery
import com.team_gdb.pentatonic.R
import com.team_gdb.pentatonic.base.BaseApplication
import com.team_gdb.pentatonic.base.BaseApplication.Companion.applicationContext
import com.team_gdb.pentatonic.databinding.ItemCommentListBinding
import com.team_gdb.pentatonic.util.formatTo
import com.team_gdb.pentatonic.util.toDate


class CommentListAdapter(
    val commentEdit: (String, String) -> Unit,
    val commentDelete: (String) -> Unit
) :
    RecyclerView.Adapter<CommentListAdapter.ViewHolder>() {

    private var commentEntityList: List<GetCoverCommentQuery.GetComment> =
        emptyList()  // 커버 아이템 리스트 정보

    /**
     * 레이아웃 바인딩 통한 ViewHolder 생성 후 반환
     *
     * @param parent    부모 ViewGroup
     * @param viewType  리사이클러 뷰 뷰타입
     * @return          생성된 ViewHolder
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemCommentListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(commentEntityList[position])
    }

    override fun getItemCount(): Int {
        return commentEntityList.size
    }

    inner class ViewHolder(
        private val binding: ItemCommentListBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(entity: GetCoverCommentQuery.GetComment) {
            binding.commentDateTextView.text = entity.createdAt.toDate().formatTo("yyyy-MM-dd")
            binding.userNameTextView.text = entity.user.username
            binding.commentTextView.text = entity.content

            Glide.with(binding.root)
                .load(entity.user.profileURI)
                .centerCrop()
                .placeholder(R.drawable.profile_image_placeholder)
                .into(binding.userProfileImageView)

            binding.commentTextView.setOnLongClickListener {
                // 사용자가 작성한 댓글의 경우 수정 UI 표시
                if (entity.user.id == BaseApplication.prefs.userId) {
                    showCommentEditLayout(entity)
                }
                true
            }

            binding.cancelButton.setOnClickListener {
                binding.commentTextView.visibility = View.VISIBLE
                binding.editCommentLayout.visibility = View.GONE
            }

            binding.updateCommentButton.setOnClickListener {
                commentEdit(entity.commentId, binding.commentEditText.text.toString())
                hideCommentEditLayout()
                binding.commentTextView.text = binding.commentEditText.text.toString()
            }

            binding.deleteButton.setOnClickListener {
                hideCommentEditLayout()
                commentDelete(entity.commentId)
            }
        }

        fun showCommentEditLayout(entity: GetCoverCommentQuery.GetComment) {
            binding.deleteButton.visibility = View.VISIBLE
            binding.commentTextView.visibility = View.GONE
            binding.editCommentLayout.visibility = View.VISIBLE
            binding.commentEditText.apply {
                setText(entity.content)
                requestFocus()
            }
            val imm =
                applicationContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.toggleSoftInput(
                InputMethodManager.SHOW_FORCED,
                InputMethodManager.HIDE_IMPLICIT_ONLY
            )
        }

        private fun hideCommentEditLayout() {
            binding.deleteButton.visibility = View.GONE
            binding.commentTextView.visibility = View.VISIBLE
            binding.editCommentLayout.visibility = View.GONE

            val imm =
                applicationContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(binding.commentEditText.windowToken, 0)
        }
    }


    fun setItem(entities: List<GetCoverCommentQuery.GetComment>) {
        this.commentEntityList = entities
        notifyDataSetChanged()
    }
}