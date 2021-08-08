package com.team_gdb.pentatonic.adapter.cover_view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.team_gdb.pentatonic.data.model.SessionData
import com.team_gdb.pentatonic.data.model.UserEntity
import com.team_gdb.pentatonic.databinding.ItemSessionListBinding


class SessionConfigListAdapter(val itemClick: (UserEntity) -> Unit) :
    RecyclerView.Adapter<SessionConfigListAdapter.ViewHolder>() {

    private var sessionDataList: List<SessionData> = emptyList()

    /**
     * 레이아웃 바인딩 통한 ViewHolder 생성 후 반환
     *
     * @param parent    부모 ViewGroup
     * @param viewType  리사이클러 뷰 뷰타입
     * @return          생성된 ViewHolder
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemSessionListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(sessionDataList[position])
    }

    override fun getItemCount(): Int {
        return sessionDataList.size
    }

    inner class ViewHolder(
        private val binding: ItemSessionListBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: SessionData) {
            binding.sessionNameTextView.text = item.sessionName
            val adapter = SessionParticipantListAdapter {
                itemClick(it)
            }
            adapter.setItem(items = item.sessionParticipantList)
            binding.sessionParticipantList.apply {
                this.layoutManager = LinearLayoutManager(context).apply {
                    this.orientation = LinearLayoutManager.HORIZONTAL
                }
                this.adapter = adapter
                this.setHasFixedSize(true)
            }
        }
    }

    fun setItem(items: List<SessionData>) {
        this.sessionDataList = items
        notifyDataSetChanged()
    }
}