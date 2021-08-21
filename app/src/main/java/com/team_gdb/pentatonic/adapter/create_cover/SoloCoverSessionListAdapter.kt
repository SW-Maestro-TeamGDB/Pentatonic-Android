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
    var selectedSession: Int = -1  // 선택된 아이템 포지션

    override fun getItemCount(): Int {
        return sessionSettingList.size
    }

    override fun onBindViewHolder(holder: SoloCoverSessionListAdapter.ViewHolder, position: Int) {
        holder.bind(sessionSettingList[position])
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

    inner class ViewHolder(
        private val binding: ItemSelectSessionListBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(entity: SessionSetting) {
            if (selectedSession != adapterPosition) {
                binding.itemRootLayout.setCardBackgroundColor(Color.parseColor("#EEEEEE"))
                binding.sessionIconImage.setColorFilter(Color.BLACK)
                binding.sessionNameTextView.setTextColor(Color.BLACK)
            } else{
                binding.itemRootLayout.setCardBackgroundColor(Color.GRAY)
                binding.sessionIconImage.setColorFilter(Color.WHITE)
                binding.sessionNameTextView.setTextColor(Color.WHITE)
            }
            Glide.with(binding.root)
                .load(entity.icon)
                .override(80, 80)
                .into(binding.sessionIconImage)

            binding.sessionNameTextView.text = entity.sessionName

            binding.root.setOnClickListener {
                selectedSession = adapterPosition
                notifyDataSetChanged()
                itemClick(entity)
            }

        }
    }

    fun setItem(entities: List<SessionSetting>) {
        this.sessionSettingList = entities
        notifyDataSetChanged()
    }
}