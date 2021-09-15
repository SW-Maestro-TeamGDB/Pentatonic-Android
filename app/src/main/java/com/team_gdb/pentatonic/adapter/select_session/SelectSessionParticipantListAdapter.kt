package com.team_gdb.pentatonic.adapter.select_session

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.team_gdb.pentatonic.R
import com.team_gdb.pentatonic.data.model.UserEntity
import com.team_gdb.pentatonic.databinding.ItemSelectSessionParticipantListBinding
import com.team_gdb.pentatonic.databinding.ItemSessionParticipantListBinding

/**
 * 각 세션의 참가자 목록을 보여주기 위한 리사이클러뷰 어댑터
 * - 세션 구성 리스트 (SessionConfigListAdapter) 에 종속됨
 *
 * @property itemClick  세션 참가자 각각의 프로필을 볼 수 있도록 하기 위해 프로필 조회 페이지로 이동하는 동작
 */
class SelectSessionParticipantListAdapter(val itemClick: (UserEntity) -> Unit) :
    RecyclerView.Adapter<SelectSessionParticipantListAdapter.ViewHolder>() {

    private var participantList: List<UserEntity> = emptyList()  // 각 세션의 참가자들 목록

    /**
     * 레이아웃 바인딩 통한 ViewHolder 생성 후 반환
     *
     * @param parent    부모 ViewGroup
     * @param viewType  리사이클러 뷰 뷰타입
     * @return          생성된 ViewHolder
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemSelectSessionParticipantListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(participantList[position], position)
    }

    override fun getItemCount(): Int {
        return participantList.size
    }

    inner class ViewHolder(
        private val binding: ItemSelectSessionParticipantListBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: UserEntity, position: Int) {
            // 리스트 첫 아이템의 경우에는 어느정도 마진을 줘야함
            if (position == 0) {
                val param = binding.userLayout.layoutParams as ViewGroup.MarginLayoutParams
                param.setMargins(64, 0, 0, 0)
                binding.userLayout.layoutParams = param
            }

            // 해당 사용자의 프로필 사진
            Glide.with(binding.root)
                .load(item.profileImage)
                .placeholder(R.drawable.profile_image_placeholder)
                .override(80, 80)
                .into(binding.userProfileImage)

            // 해당 사용자의 닉네임
            binding.usernameTextView.text = item.username

            // 클릭시 해당 사용자의 프로필 페이지로 이동
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