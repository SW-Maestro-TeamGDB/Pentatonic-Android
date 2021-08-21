package com.team_gdb.pentatonic.adapter.create_cover

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.team_gdb.pentatonic.databinding.ItemSelectSessionListBinding
import com.team_gdb.pentatonic.data.session.SessionSetting

/**
 * 솔로 커버 모드에서 세션을 선택하기 위한 리스트
 * - 밴드 커버와 달리, 세션을 선택할 때 하이라이팅 기능이 필요하므로 밴드 커버에서의 세션 선택 리스트 어댑터를 상속받아 사용.
 *
 * @property itemClick  아이템 클릭되었을 때, 해당 세션을 리스트에 추가 (람다로 클릭리스너 지정)
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
            ItemSelectSessionListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(sessionSettingList[position])
    }

    inner class ViewHolder(
        private val binding: ItemSelectSessionListBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(entity: SessionSetting) {
            Glide.with(binding.root)
                .load(entity.icon)
                .override(80, 80)
                .into(binding.sessionIconImage)

            if (selectedSession != adapterPosition) {   // 선택된 아이템이 아니라면 기본 스타일로 표현
                setItemBasic()
            } else {  // 선택된 아이템이라면, 하이라이팅 처리
                setItemHighlighting()
            }

            binding.sessionNameTextView.text = entity.sessionName

            binding.root.setOnClickListener {
                if (selectedSession != adapterPosition) {
                    selectedSession = adapterPosition
                    notifyDataSetChanged()
                    itemClick(entity)
                }
            }
        }

        private fun setItemHighlighting() {
            binding.itemRootLayout.setCardBackgroundColor(Color.GRAY)
            binding.sessionIconImage.setColorFilter(Color.WHITE)
            binding.sessionNameTextView.setTextColor(Color.WHITE)
        }

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