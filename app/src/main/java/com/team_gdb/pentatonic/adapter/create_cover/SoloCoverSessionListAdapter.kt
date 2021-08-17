package com.team_gdb.pentatonic.adapter.create_cover

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
class SoloCoverSessionListAdapter(override val itemClick: (SessionSetting) -> Unit) :
    SelectSessionListAdapter(itemClick) {

    override fun getItemCount(): Int {
        return sessionSettingList.size
    }

    inner class ViewHolder(
        private val binding: ItemSelectSessionListBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(entity: SessionSetting) {
            Glide.with(binding.root)
                .load(entity.icon)
                .override(80, 80)
                .into(binding.sessionIconImage)

            binding.sessionNameTextView.text = entity.sessionName
            binding.root.setOnClickListener {
                itemClick(entity)
            }
        }
    }
}