package com.team_gdb.pentatonic.adapter.cover_list

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.team_gdb.pentatonic.databinding.ItemAdBannerViewpagerBinding


/**
 * Artist 탭 광고 배너 이미지 뷰페이저 어댑터
 */
class AdBannerViewPagerAdapter :
    RecyclerView.Adapter<AdBannerViewPagerAdapter.ViewHolder>() {

    private var adImageList: List<Drawable?> = emptyList()  // Cover 아이템 리스트 정보

    /**
     * 레이아웃 바인딩 통한 ViewHolder 생성 후 반환
     *
     * @param parent    부모 ViewGroup
     * @param viewType  리사이클러 뷰 뷰타입
     * @return          생성된 ViewHolder
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemAdBannerViewpagerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(adImageList[position])
    }

    override fun getItemCount(): Int {
        return adImageList.size
    }

    inner class ViewHolder(
        private val binding: ItemAdBannerViewpagerBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(image: Drawable?) {
            Glide.with(binding.root)
                .load(image)
                .centerCrop()
                .into(binding.adImageView)
        }
    }

    fun setItem(entities: List<Drawable?>) {
        this.adImageList = entities
        notifyDataSetChanged()
    }
}