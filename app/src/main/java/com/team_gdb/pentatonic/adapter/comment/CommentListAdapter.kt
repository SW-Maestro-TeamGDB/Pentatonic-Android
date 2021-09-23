package com.team_gdb.pentatonic.adapter.comment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.team_gdb.pentatonic.GetCoverCommentQuery
import com.team_gdb.pentatonic.R
import com.team_gdb.pentatonic.data.session.SessionSetting
import com.team_gdb.pentatonic.databinding.ItemCommentListBinding
import com.team_gdb.pentatonic.databinding.ItemLibraryListBinding

class CommentListAdapter():
    RecyclerView.Adapter<CommentListAdapter.ViewHolder>() {

    private var commentEntityList: List<GetCoverCommentQuery.GetComment> = emptyList()  // 커버 아이템 리스트 정보

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
            binding.commentDateTextView.text = entity.createdAt
            binding.userNameTextView.text = entity.user.username
            binding.commentTextView.text = entity.content

            Glide.with(binding.root)
                .load(entity.user.profileURI)
                .centerCrop()
                .placeholder(R.drawable.profile_image_placeholder)
                .into(binding.userProfileImageView)
        }
    }

    fun setItem(entities: List<GetCoverCommentQuery.GetComment>) {
        this.commentEntityList = entities
        notifyDataSetChanged()
    }
}