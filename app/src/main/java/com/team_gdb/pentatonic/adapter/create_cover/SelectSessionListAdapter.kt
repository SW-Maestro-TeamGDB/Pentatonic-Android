package com.team_gdb.pentatonic.adapter.create_cover

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.team_gdb.pentatonic.databinding.ItemSelectSessionListBinding
import com.team_gdb.pentatonic.ui.create_cover.session_setting.SessionSetting

/**
 * 세션 추가하기 버튼을 눌렀을 때 추가할 수 있는 세션 목록을 보여줌
 *
 * @property itemClick  아이템 클릭되었을 때, 해당 세션을 리스트에 추가 (람다로 클릭리스너 지정)
 */
class SelectSessionListAdapter(val itemClick: (SessionSetting) -> Unit) :
    RecyclerView.Adapter<SelectSessionListAdapter.ViewHolder>() {

    private var sessionSettingList: List<SessionSetting> = emptyList()  // 세션 악기 아이템 리스트 정보

    /**
     * 레이아웃 바인딩 통한 ViewHolder 생성 후 반환
     *
     * @param parent    부모 ViewGroup
     * @param viewType  리사이클러 뷰 뷰타입
     * @return          생성된 ViewHolder
     */
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

    fun setItem(entities: List<SessionSetting>) {
        this.sessionSettingList = entities
        notifyDataSetChanged()
    }
}