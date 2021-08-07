package com.team_gdb.pentatonic.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.team_gdb.pentatonic.R
import com.team_gdb.pentatonic.data.model.SongEntity
import com.team_gdb.pentatonic.databinding.ItemHorizontalCoverListBinding
import com.team_gdb.pentatonic.databinding.ItemHorizontalSongListBinding

class SongHorizontalListAdapter(val itemClick: (SongEntity) -> Unit) :
    RecyclerView.Adapter<SongHorizontalListAdapter.ViewHolder>() {

    private var songEntityList: List<SongEntity> = emptyList()  // Cover 아이템 리스트 정보

    /**
     * 레이아웃 바인딩 통한 ViewHolder 생성 후 반환
     *
     * @param parent    부모 ViewGroup
     * @param viewType  리사이클러 뷰 뷰타입
     * @return          생성된 ViewHolder
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemHorizontalSongListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(songEntityList[position], position)
    }

    override fun getItemCount(): Int {
        return songEntityList.size
    }

    inner class ViewHolder(
        private val binding: ItemHorizontalSongListBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(entity: SongEntity, position: Int) {
            // 리스트 첫 아이템의 경우에는 어느정도 마진을 줘야함
            if (position == 0){
                val param = binding.songItemCard.layoutParams as ViewGroup.MarginLayoutParams
                param.setMargins(64, 0, 0, 0)
                binding.songItemCard.layoutParams = param
            }
            Glide.with(binding.root)
                .load(entity.albumJacketImage)
                .placeholder(R.drawable.placeholder_cover_bg)
                .override(480, 272)
                .into(binding.albumJacketImage)

            binding.songNameTextView.text = entity.name
            binding.songArtistTextView.text = entity.artist
        }
    }

    fun setItem(entities: List<SongEntity>) {
        this.songEntityList = entities
        notifyDataSetChanged()
    }
}