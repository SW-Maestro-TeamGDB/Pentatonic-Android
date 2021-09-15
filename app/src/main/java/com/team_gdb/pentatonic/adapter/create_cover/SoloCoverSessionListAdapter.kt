package com.team_gdb.pentatonic.adapter.create_cover

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.team_gdb.pentatonic.databinding.ItemSelectSessionListBinding
import com.team_gdb.pentatonic.data.session.SessionSetting
import com.team_gdb.pentatonic.databinding.ItemSessionConfigListBinding

/**
 * 솔로 커버 모드에서 세션을 선택하기 위한 리스트
 * - 밴드 커버와 달리 인원 지정이 필요 없어, 세션을 추가하는 방식이 아닌 선택하는 방식으로 구성
 *
 * @property itemClick  아이템 클릭되었을 때, 사용자의 선택 정보를 ViewModel 에 저장하는 동작
 */
class SoloCoverSessionListAdapter(val itemClick: (SessionSetting) -> Unit) :
    RecyclerView.Adapter<SoloCoverSessionListAdapter.ViewHolder>() {
    var sessionSettingList: List<SessionSetting> = emptyList()  // 세션 악기 아이템 리스트 정보
    var selectedSession: Int = -1  // 선택된 아이템 포지션 -> 이를 활용하여 ViewHolder 바인딩 시 하이라이팅 여부 나눔

    override fun getItemCount(): Int {
        return sessionSettingList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemSessionConfigListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(sessionSettingList[position])
    }

    inner class ViewHolder(
        private val binding: ItemSessionConfigListBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(entity: SessionSetting) {
            // 해당 세션을 대표하는 이미지
            Glide.with(binding.root)
                .load(entity.icon)
                .override(80, 80)
                .into(binding.sessionIconImage)

            // 선택된 아이템이 아니라면 기본 스타일로 표현
            if (selectedSession != adapterPosition) {
                setItemBasic()
            } else {  // 선택된 아이템이라면, 하이라이팅 처리
                setItemHighlighting()
            }

            // 해당 세션의 이름
            binding.sessionNameTextView.text = entity.sessionName

            // 해당 세션 클릭시, ViewModel 에 선택 정보 저장하는 동작
            binding.root.setOnClickListener {
                if (selectedSession != adapterPosition) {
                    selectedSession = adapterPosition
                    notifyDataSetChanged()
                    itemClick(entity)
                }
            }
        }

        /**
         * 아이템을 하이라이팅 하는 함수
         */
        private fun setItemHighlighting() {
            binding.itemRootLayout.setCardBackgroundColor(Color.GRAY)
            binding.sessionIconImage.setColorFilter(Color.WHITE)
            binding.sessionNameTextView.setTextColor(Color.WHITE)
        }

        /**
         * 아이템에 기본 스타일 적용하는 함수
         */
        private fun setItemBasic() {
            binding.itemRootLayout.setCardBackgroundColor(Color.parseColor("#EEEEEE"))
            binding.sessionIconImage.setColorFilter(Color.BLACK)
            binding.sessionNameTextView.setTextColor(Color.BLACK)
        }
    }

    fun setItem(entities: List<SessionSetting>) {
        this.sessionSettingList = entities
        notifyDataSetChanged()
    }
}