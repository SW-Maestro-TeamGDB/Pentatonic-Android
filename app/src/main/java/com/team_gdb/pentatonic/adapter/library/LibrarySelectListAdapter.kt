package com.team_gdb.pentatonic.adapter.library

import android.graphics.Color
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
class LibrarySelectListAdapter(val itemClick: (String) -> Unit) :
    RecyclerView.Adapter<LibrarySelectListAdapter.ViewHolder>() {

    private var coverEntityList: List<GetUserLibraryQuery.Library> = emptyList()  // 커버 아이템 리스트 정보
    var selectedSession: Int = -1  // 선택된 아이템 포지션 -> 이를 활용하여 ViewHolder 바인딩 시 하이라이팅 여부 나눔

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
            // 해당 커버의 이름과 날짜
            binding.coverName.text = entity.name
            binding.coverDate.text = entity.date

            // 선택된 아이템이 아니라면 기본 스타일로 표현
            if (selectedSession != adapterPosition) {
                setItemBasic()
            } else {  // 선택된 아이템이라면, 하이라이팅 처리
                setItemHighlighting()
            }

            // 해당 라이브러리 클릭시, ViewModel 에 선택 정보 저장하는 동작
            binding.root.setOnClickListener {
                if (selectedSession != adapterPosition) {
                    selectedSession = adapterPosition
                    itemClick(entity.coverId)
                } else if (selectedSession == adapterPosition) {
                    selectedSession = -1
                    itemClick("")  // 만약 선택 해제 시 비어있는 값 전달
                }
                notifyDataSetChanged()
            }
        }

        /**
         * 아이템을 하이라이팅 하는 함수
         */
        private fun setItemHighlighting() {
            binding.itemCardLayout.setCardBackgroundColor(Color.LTGRAY)
            binding.coverName.setTextColor(Color.WHITE)
            binding.coverDate.setTextColor(Color.WHITE)
        }

        /**
         * 아이템에 기본 스타일 적용하는 함수
         */
        private fun setItemBasic() {
            binding.itemCardLayout.setCardBackgroundColor(Color.WHITE)
            binding.coverName.setTextColor(Color.BLACK)
            binding.coverDate.setTextColor(Color.BLACK)
        }
    }

    fun setItem(entities: List<GetUserLibraryQuery.Library>) {
        this.coverEntityList = entities
        notifyDataSetChanged()
    }
}