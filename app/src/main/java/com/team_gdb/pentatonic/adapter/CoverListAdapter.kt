package com.team_gdb.pentatonic.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.team_gdb.pentatonic.R
import com.team_gdb.pentatonic.data.model.CoverItem
import com.team_gdb.pentatonic.databinding.ItemCoverListBinding

class CoverListAdapter(val itemClick: (CoverItem) -> Unit) :
    RecyclerView.Adapter<CoverListAdapter.ViewHolder>() {

    private var coverItemList: List<CoverItem> = emptyList()  // Cover 아이템 리스트 정보

    /**
     * 레이아웃 바인딩 통한 ViewHolder 생성 후 반환
     *
     * @param parent    부모 ViewGroup
     * @param viewType  리사이클러 뷰 뷰타입
     * @return          생성된 ViewHolder
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemCoverListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(coverItemList[position])
    }

    override fun getItemCount(): Int {
        return coverItemList.size
    }

    inner class ViewHolder(
        private val binding: ItemCoverListBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CoverItem) {
            binding.coverNameTextView.text = item.coverName
            binding.coverOriginalSongTextView.text = item.originalSong

            if (item.imageUrl.isNotBlank()) {
                Glide.with(binding.root)
                    .load(item.imageUrl)
                    .into(binding.coverImage)
            } else {
                Glide.with(binding.root)
                    .load(R.drawable.placeholder_cover_bg)
                    .into(binding.coverImage)
            }

            val sessionSet = mutableSetOf<String>()
            var sessionListText = ""
            item.sessionList.forEach {
                sessionSet.add(it.sessionName)
            }
            sessionSet.forEach {
                sessionListText += "$it "
            }
            sessionListText += "참여중"
            binding.coverSessionListTextView.text = sessionListText

            binding.coverLikeTextView.text = item.like.toString()
            binding.coverViewTextView.text = item.view.toString()

            binding.root.setOnClickListener {
                itemClick(item)
            }
        }
    }

    fun setItem(items: List<CoverItem>) {
        this.coverItemList = items
        notifyDataSetChanged()
    }
}