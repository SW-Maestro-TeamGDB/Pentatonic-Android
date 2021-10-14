package com.team_gdb.pentatonic.adapter.ranking

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.team_gdb.pentatonic.GetRankedUserListQuery
import com.team_gdb.pentatonic.R
import com.team_gdb.pentatonic.databinding.ItemArtistListBinding

/**
 * 아티스트 랭킹을 보여주기 위한 리사이클러뷰 어댑터
 *
 * @property itemClick  해당 커버 정보 페이지로 이동하는 동작
 */
class ArtistRankingListAdapter(val isDetailView: Boolean, val itemClick: (String) -> Unit) :
    RecyclerView.Adapter<ArtistRankingListAdapter.ViewHolder>() {

    private var userEntityList: List<GetRankedUserListQuery.GetRankedUser> =
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
            ItemArtistListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(userEntityList[position])
    }

    override fun getItemCount(): Int {
        return if (isDetailView) {  // 상세보기 페이지일 경우
            userEntityList.size
        } else {  // 아티스트 탭에서의 리스트일 경우
            if (userEntityList.size > 3) 3 else userEntityList.size
        }
    }

    inner class ViewHolder(
        private val binding: ItemArtistListBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(entity: GetRankedUserListQuery.GetRankedUser) {
            // 커버 대표 이미지
            Glide.with(binding.root)
                .load(entity.profileURI)
                .placeholder(R.drawable.placeholder_cover_bg)
                .override(480, 272)
                .into(binding.artistProfileImage)

            // 아티스트명
            binding.artistNameTextView.text = entity.username

            // 아티스트 소개
            if (entity.introduce.isNotBlank()){
                binding.artistIntroductionTextView.text = entity.introduce
            } else {
                binding.artistIntroductionTextView.visibility = View.GONE
            }

            binding.followerCountTextView.text = entity.followerCount.toString()

            // 해당 커버를 클릭하면, 커버 페이지로 이동
            binding.root.setOnClickListener {
                itemClick(entity.id)
            }
        }
    }

    fun setItem(entities: List<GetRankedUserListQuery.GetRankedUser>) {
        this.userEntityList = entities
        notifyDataSetChanged()
    }
}