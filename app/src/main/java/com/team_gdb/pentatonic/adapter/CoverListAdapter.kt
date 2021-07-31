package com.team_gdb.pentatonic.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.team_gdb.pentatonic.data.model.CoverItem
import com.team_gdb.pentatonic.databinding.ItemCoverListBinding

class CoverListAdapter(val itemClick: (CoverItem) -> Unit) :
    RecyclerView.Adapter<CoverListAdapter.ViewHolder>() {

    private var coverItems: List<CoverItem> = emptyList()  // Cover 아이템 리스트 정보

    /**
     * 레이아웃 바인딩 통한 ViewHolder 생성 후 반환
     *
     * @param parent : 부모 ViewGroup
     * @param viewType 리사이클러 뷰 뷰타입
     * @return : 생성된 ViewHolder
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemCoverListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(coverItems[position])
    }

    override fun getItemCount(): Int {
        return coverItems.size
    }

    inner class ViewHolder(
        private val binding: ItemCoverListBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CoverItem) {

            binding.root.setOnClickListener {
                itemClick(item)
            }
        }
    }

    fun setItem(items: List<CoverItem>) {
        this.coverItems = items
        notifyDataSetChanged()
    }
}