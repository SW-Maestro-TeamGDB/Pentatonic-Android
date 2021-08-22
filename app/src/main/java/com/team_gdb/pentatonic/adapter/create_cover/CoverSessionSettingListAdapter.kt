package com.team_gdb.pentatonic.adapter.create_cover

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.team_gdb.pentatonic.data.model.SessionSettingEntity
import com.team_gdb.pentatonic.databinding.ItemSessionSettingListBinding
import com.team_gdb.pentatonic.util.PlayAnimation
import com.team_gdb.pentatonic.util.PlayAnimation.playErrorAnimation

/**
 * 커버를 생성할 때, 해당 커버에 어떤 세션들이 참가할 수 있는지를 설정하기 위한 리사이클러뷰 어댑터
 * - 세션 추가하기를 통해 세션을 추가할 수 있고 (SessionSettingEntity), 최대 참가 인원 지정 가능
 *
 * @property itemLongClick  // 세션 아이템 길게 눌렀을 때, 해당 세션을 제거하는 다이얼로그 띄우는 동작
 */
class CoverSessionSettingListAdapter(val itemLongClick: (SessionSettingEntity) -> Unit) :
    RecyclerView.Adapter<CoverSessionSettingListAdapter.ViewHolder>() {

    private var sessionSettingList: List<SessionSettingEntity> = emptyList()  // 세션 악기 아이템 리스트 정보

    /**
     * 레이아웃 바인딩 통한 ViewHolder 생성 후 반환
     *
     * @param parent    부모 ViewGroup
     * @param viewType  리사이클러 뷰 뷰타입
     * @return          생성된 ViewHolder
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemSessionSettingListBinding.inflate(
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
        private val binding: ItemSessionSettingListBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(entity: SessionSettingEntity) {
            // 해당 세션 (보컬 및 악기) 대표 이미지
            Glide.with(binding.root)
                .load(entity.sessionSetting.icon)
                .override(80, 80)
                .into(binding.sessionIconImage)

            // 해당 세션 이름과 최대 참가 가능 인원
            binding.sessionNameTextView.text = entity.sessionSetting.sessionName
            binding.countTextView.text = entity.count.toString()

            // 인원 증가 (+) 버튼 눌렀을 때
            binding.addButton.setOnClickListener {
                entity.count += 1
                binding.countTextView.text = entity.count.toString()
            }

            // 인원 감소 (-) 버튼 눌렀을 때
            binding.minueButton.setOnClickListener {
                if (entity.count != 1) {  // 1 미만의 수 입력 방지
                    entity.count -= 1
                    binding.countTextView.text = entity.count.toString()
                } else {  // 오류 애니메이션 구동
                    playErrorAnimation(binding.sessionSettingItemLayout)
                }
            }

            // 해당 세션 아이템을 길게 누르면 세션 제거 다이얼로그 띄움
            binding.root.setOnLongClickListener {
                itemLongClick(entity)
                true
            }
        }
    }

    fun setItem(entities: List<SessionSettingEntity>) {
        this.sessionSettingList = entities
        notifyDataSetChanged()
    }
}