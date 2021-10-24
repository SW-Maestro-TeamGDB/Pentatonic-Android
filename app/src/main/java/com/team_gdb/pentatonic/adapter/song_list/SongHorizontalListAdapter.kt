package com.team_gdb.pentatonic.adapter.song_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.team_gdb.pentatonic.GetSongListQuery
import com.team_gdb.pentatonic.R
import com.team_gdb.pentatonic.data.model.SongEntity
import com.team_gdb.pentatonic.databinding.ItemHorizontalSongListBinding
import com.team_gdb.pentatonic.type.GENRE_TYPE

/**
 * 펜타토닉이 제공하는 곡 정보를 보여주는 리사이클러뷰 어댑터 (가로 버전)
 * - 보통 '스튜디오' 탭에서 곡을 추천해주는 메뉴에서 사용
 *
 * @property itemClick  해당 곡의 상세 정보 페이지 이동 동작 수행
 */
class SongHorizontalListAdapter(val itemClick: (SongEntity) -> Unit) :
    RecyclerView.Adapter<SongHorizontalListAdapter.ViewHolder>() {

    private var songEntityList: List<GetSongListQuery.QuerySong> = emptyList()  // Cover 아이템 리스트 정보

    /**
     * 레이아웃 바인딩 통한 ViewHolder 생성 후 반환
     *
     * @param parent    부모 ViewGroup
     * @param viewType  리사이클러 뷰 뷰타입
     * @return          생성된 ViewHolder
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemHorizontalSongListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
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

        fun bind(entity: GetSongListQuery.QuerySong, position: Int) {
            // 리스트 첫 아이템의 경우에는 어느정도 마진을 줘야함
            if (position == 0) {
                val param = binding.songItemCard.layoutParams as ViewGroup.MarginLayoutParams
                param.setMargins(64, 0, 0, 0)
                binding.songItemCard.layoutParams = param
            }

            // 해당 곡의 앨범 자켓 이미지
            Glide.with(binding.root)
                .load(entity.songImg)
                .placeholder(R.drawable.placeholder_cover_bg)
                .override(480, 272)
                .into(binding.albumJacketImage)

            // 해당 곡의 제목과 가수명
            binding.songNameTextView.text = entity.name
            binding.songArtistTextView.text = entity.artist

            // 해당 곡의 상세 정보 페이지로 이동
            binding.root.setOnClickListener {
                val songEntity = SongEntity(
                    songId = entity.songId,
                    songName = entity.name,
                    songGenre = (entity.genre ?: GENRE_TYPE.POP).name,
                    songUrl = entity.songURI,
                    songLevel = entity.level ?: 2,
                    isWeeklyChallenge = entity.weeklyChallenge,
                    artistName = entity.artist,
                    albumName = entity.album ?: "자유곡",
                    albumReleaseDate = entity.releaseDate ?: "자유곡",
                    albumJacketImage = entity.songImg ?: "",
                    isFreeSong = false,
                    duration = entity.duration
                )
                itemClick(songEntity)
            }
        }
    }

    fun setItem(entities: List<GetSongListQuery.QuerySong>) {
        this.songEntityList = entities
        notifyDataSetChanged()
    }
}