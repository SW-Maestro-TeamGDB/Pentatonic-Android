package com.team_gdb.pentatonic.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.team_gdb.pentatonic.data.model.Session
import com.team_gdb.pentatonic.databinding.ItemSessionListBinding


class SessionListAdapter() :
    RecyclerView.Adapter<SessionListAdapter.ViewHolder>() {

    private var sessionItems: List<Session> = emptyList()  // Cover 아이템 리스트 정보

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemSessionListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(sessionItems[position])
    }

    override fun getItemCount(): Int {
        return sessionItems.size
    }

    inner class ViewHolder(
        private val binding: ItemSessionListBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Session) {

        }
    }

    fun setItem(items: List<Session>) {
        this.sessionItems = items
        notifyDataSetChanged()
    }
}