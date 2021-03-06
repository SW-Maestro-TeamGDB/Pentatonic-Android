package com.team_gdb.pentatonic.adapter.create_cover

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.team_gdb.pentatonic.data.session.Session
import com.team_gdb.pentatonic.databinding.ItemSessionConfigListBinding

/**
 * 세션 추가하기 버튼을 눌렀을 때, BottomSheetDialog 로 추가할 수 있는 세션을 보여주는 리사이클러뷰 어댑터
 *
 * @property itemClick  아이템 클릭되었을 때, 해당 세션을 리스트에 추가하는 동작
 */
class SelectBandSessionListAdapter(val itemClick: (Session) -> Unit) :
    RecyclerView.Adapter<SelectBandSessionListAdapter.ViewHolder>() {

    var sessionSettingList: List<Session> = emptyList()  // 세션 악기 아이템 리스트 정보

    /**
     * 레이아웃 바인딩 통한 ViewHolder 생성 후 반환
     *
     * @param parent    부모 ViewGroup
     * @param viewType  리사이클러 뷰 뷰타입
     * @return          생성된 ViewHolder
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemSessionConfigListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(sessionSettingList[position])
    }

    override fun getItemCount(): Int {
        return sessionSettingList.size
    }

    inner class ViewHolder(
        private val binding: ItemSessionConfigListBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(entity: Session) {
            // 해당 세션을 대표하는 이미지
            Glide.with(binding.root)
                .load(entity.icon)
                .override(80, 80)
                .into(binding.sessionIconImage)

            // 해당 세션의 이름
            binding.sessionNameTextView.text = entity.sessionName

            // 해당 세션 선택 시, 커버 구성 목록에 이를 추가하는 동작
            binding.root.setOnClickListener {
                itemClick(entity)
            }
        }
    }

    fun setItem(entities: List<Session>) {
        this.sessionSettingList = entities
        notifyDataSetChanged()
    }
}