package com.team_gdb.pentatonic.adapter.library

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.team_gdb.pentatonic.GetUserLibraryQuery
import com.team_gdb.pentatonic.R
import com.team_gdb.pentatonic.data.model.CoverEntity
import com.team_gdb.pentatonic.data.model.LibraryEntity
import com.team_gdb.pentatonic.databinding.ItemLibraryListBinding
import com.team_gdb.pentatonic.databinding.ItemLibrarySelectListBinding
import com.team_gdb.pentatonic.databinding.ItemVerticalCoverListBinding

/**
 * 밴드 커버에 참여함에 있어, 라이브러리 내의 어떤 녹음본으로 참여할지 선택할 수 있게 라이브러리 목록을 보여주는 리사이클러뷰 어댑터
 *
 * @property itemClick  사용자가 선택한(클릭한) 커버로 밴드에 참가하는 동작
 */
class LibrarySelectListAdapter(val itemClick: (LibraryEntity) -> Unit) :
    RecyclerView.Adapter<LibrarySelectListAdapter.ViewHolder>() {

    private var coverEntityList: List<GetUserLibraryQuery.Library> = emptyList()  // 커버 아이템 리스트 정보

    /**
     * 레이아웃 바인딩 통한 ViewHolder 생성 후 반환
     *
     * @param parent    부모 ViewGroup
     * @param viewType  리사이클러 뷰 뷰타입
     * @return          생성된 ViewHolder
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemLibrarySelectListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(coverEntityList[position])
    }

    override fun getItemCount(): Int {
        return coverEntityList.size
    }

    inner class ViewHolder(
        private val binding: ItemLibrarySelectListBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(entity: GetUserLibraryQuery.Library) {
            // 해당 커버의 이름
            binding.coverName.text = entity.name
            // TODO : Cover Date 필드 추가해줘야 함
        }
    }

    fun setItem(entities: List<GetUserLibraryQuery.Library>) {
        this.coverEntityList = entities
        notifyDataSetChanged()
    }
}