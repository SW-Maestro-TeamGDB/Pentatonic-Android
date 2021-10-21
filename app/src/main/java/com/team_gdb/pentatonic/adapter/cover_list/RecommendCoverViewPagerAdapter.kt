package com.team_gdb.pentatonic.adapter.cover_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.team_gdb.pentatonic.GetRecommendBandListQuery
import com.team_gdb.pentatonic.data.model.CoverEntity
import com.team_gdb.pentatonic.databinding.ItemRisingCoverViewpagerBinding

/**
 * 커버 목록을 보여주기 위한 뷰 페이저
 * - 스튜디오 상단에 표시됨 (인기가 상승하고 있는 커버 표시)
 *
 * @property itemClick  해당 커버 정보 페이지로 이동하는 동작
 */
class RecommendCoverViewPagerAdapter(val itemClick: (String) -> Unit) :
    RecyclerView.Adapter<RecommendCoverViewPagerAdapter.ViewHolder>() {

    private var coverEntityList: List<GetRecommendBandListQuery.GetRecommendBand> = emptyList()  // Cover 아이템 리스트 정보

    /**
     * 레이아웃 바인딩 통한 ViewHolder 생성 후 반환
     *
     * @param parent    부모 ViewGroup
     * @param viewType  리사이클러 뷰 뷰타입
     * @return          생성된 ViewHolder
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemRisingCoverViewpagerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(coverEntityList[position])
    }

    override fun getItemCount(): Int {
        return coverEntityList.size
    }

    inner class ViewHolder(
        private val binding: ItemRisingCoverViewpagerBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(entity: GetRecommendBandListQuery.GetRecommendBand) {
            // 커버 대표 이미지
            Glide.with(binding.root)
                .load(entity.backGroundURI)
                .override(480, 272)
                .into(binding.coverImage)

            // 커버명과 원곡명
            binding.coverNameTextView.text = entity.name
            binding.coverOriginalSongTextView.text = "${entity.song.name} - ${entity.song.artist}"

            val participantCount = entity.session?.sumOf {
                it?.cover?.size ?: 0
            }
            // 커버를 구성중인 인원수
            binding.coverSessionListTextView.text = "$participantCount"

            // 좋아요수와 조회수
            binding.coverLikeTextView.text = entity.likeCount.toString()
            binding.coverViewTextView.text = entity.viewCount.toString()

            // 해당 커버를 클릭하면, 커버 페이지로 이동
            binding.root.setOnClickListener {
                itemClick(entity.bandId)
            }
        }
    }

    fun setItem(entities: List<GetRecommendBandListQuery.GetRecommendBand>) {
        this.coverEntityList = entities
        notifyDataSetChanged()
    }
}