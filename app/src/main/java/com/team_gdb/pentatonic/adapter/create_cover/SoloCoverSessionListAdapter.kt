package com.team_gdb.pentatonic.adapter.create_cover

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.team_gdb.pentatonic.databinding.ItemSelectSessionListBinding
import com.team_gdb.pentatonic.data.session.SessionSetting

/**
 * 솔로 커버 모드에서 세션을 선택하기 위한 리스트
 *
 * @property itemClick  아이템 클릭되었을 때, 해당 세션을 리스트에 추가 (람다로 클릭리스너 지정)
 */
class SoloCoverSessionListAdapter(override val itemClick: (SessionSetting) -> Unit) :
    SelectSessionListAdapter(itemClick) {

    private var sessionSettingList: List<SessionSetting> = emptyList()  // 세션 악기 아이템 리스트 정보

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

    override fun setItem(entities: List<SessionSetting>) {
        this.sessionSettingList = entities
        notifyDataSetChanged()
    }
}