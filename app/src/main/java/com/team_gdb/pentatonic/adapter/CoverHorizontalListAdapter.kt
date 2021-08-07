package com.team_gdb.pentatonic.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.team_gdb.pentatonic.R
import com.team_gdb.pentatonic.data.model.CoverEntity
import com.team_gdb.pentatonic.databinding.ItemHorizontalCoverListBinding
import com.team_gdb.pentatonic.databinding.ItemVerticalCoverListBinding

/**
 * 커버 목록을 보여주기 위한 리사이클러뷰 어댑터
 *
 * @property itemClick  해당 커버 정보 페이지로 이동할 수 있도록 어댑터 생성 시 클릭리스너 동작 전달
 */
class CoverHorizontalListAdapter(val itemClick: (CoverEntity) -> Unit) :
    RecyclerView.Adapter<CoverHorizontalListAdapter.ViewHolder>() {

    private var coverEntityList: List<CoverEntity> = emptyList()  // Cover 아이템 리스트 정보

    /**
     * 레이아웃 바인딩 통한 ViewHolder 생성 후 반환
     *
     * @param parent    부모 ViewGroup
     * @param viewType  리사이클러 뷰 뷰타입
     * @return          생성된 ViewHolder
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemHorizontalCoverListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(coverEntityList[position], position)
    }

    override fun getItemCount(): Int {
        return coverEntityList.size
    }

    inner class ViewHolder(
        private val binding: ItemHorizontalCoverListBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(entity: CoverEntity, position: Int) {
            // 리스트 첫 아이템의 경우에는 어느정도 마진을 줘야함
            if (position == 0) {
                val param = binding.coverItemLayout.layoutParams as ViewGroup.MarginLayoutParams
                param.setMargins(64, 0, 0, 0)
                binding.coverItemLayout.layoutParams = param
            }
            binding.coverNameTextView.text = entity.coverName
            binding.coverOriginalSongTextView.text = entity.originalSong

            Glide.with(binding.root)
                .load(entity.imageUrl)
                .placeholder(R.drawable.placeholder_cover_bg)
                .override(480, 272)
                .into(binding.coverImage)

            binding.coverSessionListTextView.text = "${entity.sessionList.size}명 참여중"

            binding.coverLikeTextView.text = entity.like.toString()
            binding.coverViewTextView.text = entity.view.toString()

            binding.root.setOnClickListener {
                itemClick(entity)
            }
        }
    }

    fun setItem(entities: List<CoverEntity>) {
        this.coverEntityList = entities
        notifyDataSetChanged()
    }
}