package com.team_gdb.pentatonic.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.team_gdb.pentatonic.data.model.User
import com.team_gdb.pentatonic.databinding.ItemSessionParticipantListBinding

class SessionParticipantListAdapter : RecyclerView.Adapter<SessionParticipantListAdapter.ViewHolder>() {

    private var participantList: List<User> = emptyList()  // 세션 참가자들 목록

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

        fun bind(item: User) {
            binding.usernameTextView.text = item.username

            if (item.profileImage.isNotBlank()) {
                Glide.with(binding.root)
                    .load(item.profileImage)
                    .into(binding.userProfileImage)
            }
        }
    }

    fun setItem(items: List<User>) {
        this.participantList = items
        notifyDataSetChanged()
    }
}