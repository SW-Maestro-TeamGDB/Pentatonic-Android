package com.team_gdb.pentatonic.adapter.cover_list

import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.team_gdb.pentatonic.GetTrendBandsQuery
import com.team_gdb.pentatonic.GetUserInfoQuery
import com.team_gdb.pentatonic.R
import com.team_gdb.pentatonic.base.BaseApplication
import com.team_gdb.pentatonic.base.BaseApplication.Companion.applicationContext
import com.team_gdb.pentatonic.databinding.ItemHorizontalCoverListBinding

/**
 * 커버 목록을 보여주기 위한 리사이클러뷰 어댑터
 * - 가로로 스크롤되는 커버 리스트
 *
 * @property itemClick  해당 커버 정보 페이지로 이동하는 동작
 */
class CoverHistoryListAdapter(val itemClick: (String, Boolean) -> Unit) :
    RecyclerView.Adapter<CoverHistoryListAdapter.ViewHolder>() {

    private var coverEntityList: List<GetUserInfoQuery.Band> =
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

        fun bind(entity: GetUserInfoQuery.Band, position: Int) {
            // 리스트 첫 아이템의 경우에는 어느정도 마진을 줘야함
            if (position == 0) {
                val param = binding.coverItemLayout.layoutParams as ViewGroup.MarginLayoutParams
                param.setMargins(64, 0, 0, 0)
                binding.coverItemLayout.layoutParams = param
            }

            // 커버 대표 이미지
            Glide.with(binding.root)
                .load(entity.backGroundURI)
                .placeholder(R.drawable.placeholder_cover_bg)
                .override(480, 272)
                .into(binding.coverImage)

            // 커버명과 원곡명
            binding.coverNameTextView.text = entity.name
            binding.coverOriginalSongTextView.text = "${entity.song.name} - ${entity.song.artist}"

            val participantCount = entity.session?.sumOf {
                it?.cover?.size ?: 0
            }

            // 커버를 구성중인 인원수
            binding.coverSessionListTextView.run {
                if (entity.isSoloBand) {
                    this.visibility = View.GONE
                    binding.soloCoverTextView.visibility = View.VISIBLE
                } else {
                    this.text = "${participantCount}명 참여중"
                }
            }

            // 좋아요수와 조회수
            binding.coverLikeTextView.text = entity.likeCount.toString()
            binding.coverViewTextView.text = entity.viewCount.toString()

            // 해당 커버를 클릭하면, 커버 페이지로 이동
            binding.root.setOnClickListener {
                itemClick(entity.bandId, entity.isSoloBand)
            }
        }
    }

    fun setItem(entities: List<GetUserInfoQuery.Band>) {
        this.coverEntityList = entities
        notifyDataSetChanged()
    }
}