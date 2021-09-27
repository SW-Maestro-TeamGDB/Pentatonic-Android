package com.team_gdb.pentatonic.adapter.position

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.team_gdb.pentatonic.GetUserInfoQuery
import com.team_gdb.pentatonic.R
import com.team_gdb.pentatonic.data.session.SessionSetting
import com.team_gdb.pentatonic.databinding.ItemPositionRankingListBinding

class PositionRankingListAdapter(val itemClick: (String) -> Unit) :
    RecyclerView.Adapter<PositionRankingListAdapter.ViewHolder>() {

    private var positionRankingList: List<GetUserInfoQuery.Position> =
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
            ItemPositionRankingListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(positionRankingList[position], position)
    }

    override fun getItemCount(): Int {
        return positionRankingList.size
    }

    inner class ViewHolder(
        private val binding: ItemPositionRankingListBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(entity: GetUserInfoQuery.Position, position: Int) {
            // 리스트 첫 아이템의 경우에는 어느정도 마진을 줘야함
            if (position == 0) {
                val param = binding.coverItemLayout.layoutParams as ViewGroup.MarginLayoutParams
                param.setMargins(64, 0, 0, 0)
                binding.coverItemLayout.layoutParams = param
            }

            Glide.with(binding.root)
                .load(SessionSetting.valueOf(entity.position).icon)
                .placeholder(R.drawable.placeholder_cover_bg)
                .override(200, 200)
                .into(binding.positionImageView)

            binding.positionNameTextView.text = SessionSetting.valueOf(entity.position).sessionName
            binding.positionRankingTextView.text = "124위"
            binding.positionLevel.rating = 3.0F
        }
    }

    fun setItem(entities: List<GetUserInfoQuery.Position>) {
        this.positionRankingList = entities
        notifyDataSetChanged()
    }
}