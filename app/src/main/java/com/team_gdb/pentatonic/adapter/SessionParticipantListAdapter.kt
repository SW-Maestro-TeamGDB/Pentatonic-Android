package com.team_gdb.pentatonic.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.team_gdb.pentatonic.data.model.UserEntity
import com.team_gdb.pentatonic.databinding.ItemSessionParticipantListBinding

/**
 * 세션 참가자 목록 (e.g. 해당 커버에 기타로 참가한 사람들) 을 보여주기 위한 리사이클러뷰 어댑터
 *
 * @property itemClick  세션 참가자 각각의 프로필을 볼 수 있도록 하기 위해 프로필 조회 페이지로 이동하는 클릭리스너 전달
 */
class SessionParticipantListAdapter(val itemClick: (UserEntity) -> Unit) :
    RecyclerView.Adapter<SessionParticipantListAdapter.ViewHolder>() {

    private var participantList: List<UserEntity> = emptyList()  // 세션 참가자들 목록

    /**
     * 레이아웃 바인딩 통한 ViewHolder 생성 후 반환
     *
     * @param parent    부모 ViewGroup
     * @param viewType  리사이클러 뷰 뷰타입
     * @return          생성된 ViewHolder
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemSessionParticipantListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(participantList[position])
    }

    override fun getItemCount(): Int {
        return participantList.size
    }

    inner class ViewHolder(
        private val binding: ItemSessionParticipantListBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: UserEntity) {
            binding.usernameTextView.text = item.username

            if (item.profileImage.isNotBlank()) {
                Glide.with(binding.root)
                    .load(item.profileImage)
                    .override(80, 80)
                    .into(binding.userProfileImage)
            }

            binding.root.setOnClickListener {
                itemClick(item)
            }
        }
    }

    fun setItem(items: List<UserEntity>) {
        this.participantList = items
        notifyDataSetChanged()
    }
}