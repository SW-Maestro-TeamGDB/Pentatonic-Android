package com.team_gdb.pentatonic.adapter.ranking

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.team_gdb.pentatonic.GetRankedBandListQuery
import com.team_gdb.pentatonic.R
import com.team_gdb.pentatonic.data.model.CoverEntity
import com.team_gdb.pentatonic.databinding.ItemBandRankingListBinding

/**
 * 커버 랭킹을 보여주기 위한 리사이클러뷰 어댑터
 *
 * @property itemClick  해당 커버 정보 페이지로 이동하는 동작
 */
class CoverRankingListAdapter(val isDetailView: Boolean, val itemClick: (String, Boolean) -> Unit) :
    RecyclerView.Adapter<CoverRankingListAdapter.ViewHolder>() {

    private var coverEntityList: List<GetRankedBandListQuery.GetRankedBand> =
        emptyList()  // Cover 아이템 리스트 정보

    /**
     * 레이아웃 바인딩 통한 ViewHolder 생성 후 반환
     *
     * @param parent    부모 ViewGroup
     * @param viewType  리사이클러 뷰 뷰타입
     * @return          생성된 ViewHolder
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemBandRankingListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(coverEntityList[position])
    }

    override fun getItemCount(): Int {
        return if (isDetailView) {  // 상세보기 페이지일 경우
            coverEntityList.size
        } else {  // 아티스트 탭에서의 리스트일 경우
            if (coverEntityList.size > 3) 3 else coverEntityList.size
        }
    }

    inner class ViewHolder(
        private val binding: ItemBandRankingListBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(entity: GetRankedBandListQuery.GetRankedBand) {
            // 커버 대표 이미지
            Glide.with(binding.root)
                .load(entity.backGroundURI)
                .placeholder(R.drawable.placeholder_cover_bg)
                .override(480, 272)
                .into(binding.coverImage)

            // 커버명과 원곡명
            binding.coverNameTextView.text = entity.name
            binding.coverOriginalSongTextView.text = "${entity.song.artist} - ${entity.song.name}"

            // 좋아요수
            binding.coverLikeTextView.text = entity.likeCount.toString()

            // 해당 커버를 클릭하면, 커버 페이지로 이동
            binding.root.setOnClickListener {
                itemClick(entity.bandId, entity.isSoloBand)
            }
        }
    }

    fun setItem(entities: List<GetRankedBandListQuery.GetRankedBand>) {
        this.coverEntityList = entities
        notifyDataSetChanged()
    }
}