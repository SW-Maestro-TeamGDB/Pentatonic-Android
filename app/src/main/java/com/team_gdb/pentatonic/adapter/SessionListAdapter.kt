package com.team_gdb.pentatonic.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.team_gdb.pentatonic.data.model.Session
import com.team_gdb.pentatonic.databinding.ItemSessionListBinding


class SessionListAdapter() :
    RecyclerView.Adapter<SessionListAdapter.ViewHolder>() {

    private var sessionList: List<Session> = emptyList()  // 커버에 존재하는 Session 목록 리스트 정보 (일렉기타, 드럼 등)

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
        holder.bind(sessionList[position])
    }

    override fun getItemCount(): Int {
        return sessionList.size
    }

    inner class ViewHolder(
        private val binding: ItemSessionListBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Session) {
            binding.sessionNameTextView.text = item.sessionName
            val adapter = SessionParticipantListAdapter()
            adapter.setItem(items = item.sessionParticipantList)
            binding.sessionParticipantList.apply {
                this.adapter = adapter
                this.layoutManager = LinearLayoutManager(context).apply {
                    this.orientation = LinearLayoutManager.HORIZONTAL
                }
                this.setHasFixedSize(true)
            }
        }
    }

    fun setItem(items: List<Session>) {
        this.sessionList = items
        notifyDataSetChanged()
    }
}