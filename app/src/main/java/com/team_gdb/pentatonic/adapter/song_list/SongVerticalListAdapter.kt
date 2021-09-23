package com.team_gdb.pentatonic.adapter.song_list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.team_gdb.pentatonic.R
import com.team_gdb.pentatonic.data.model.SongEntity
import com.team_gdb.pentatonic.databinding.ItemVerticalSongListBinding

/**
 * 펜타토닉이 제공하는 곡 정보를 보여주는 리사이클러뷰 어댑터 (세로 버전)
 * - 보통 커버할 곡을 선택하는 페이지에서 사용
 *
 * @property itemClick  커버 곡 선택 동작 수행
 */
class SongVerticalListAdapter(val itemClick: (SongEntity) -> Unit) :
    RecyclerView.Adapter<SongVerticalListAdapter.ViewHolder>() {

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
            ItemVerticalSongListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(songEntityList[position])
    }

    override fun getItemCount(): Int {
        return songEntityList.size
    }

    inner class ViewHolder(
        private val binding: ItemVerticalSongListBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(entity: SongEntity) {
            // 해당 곡의 앨범 자켓 이미지
            Glide.with(binding.root)
                .load(entity.albumJacketImage)
                .placeholder(R.drawable.placeholder_cover_bg)
                .override(480, 272)
                .into(binding.albumJacketImage)

            // 해당 곡의 제목과 가수명
            binding.songNameTextView.text = entity.songName
            binding.songArtistTextView.text = entity.artistName

            binding.songLevel.rating = entity.songLevel.toFloat()

            if (entity.isWeeklyChallenge) {
                binding.weeklyChallengeBadge.visibility = View.VISIBLE
            }
            // 클릭하면, Bottom Sheet Dialog 모달을 보여줌으로써
            // 사용자로 하여금 곡 선택 의사결정을 유도함
            // e.g. "이 곡을 선택하시겠습니까?"
            binding.root.setOnClickListener {
                itemClick(entity)
            }
        }
    }

    fun setItem(entities: List<SongEntity>) {
        this.songEntityList = entities
        notifyDataSetChanged()
    }
}