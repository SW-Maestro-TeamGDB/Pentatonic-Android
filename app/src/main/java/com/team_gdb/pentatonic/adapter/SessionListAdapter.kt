package com.team_gdb.pentatonic.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.team_gdb.pentatonic.data.model.CoverItem
import com.team_gdb.pentatonic.data.model.Session
import com.team_gdb.pentatonic.data.model.User
import com.team_gdb.pentatonic.databinding.ItemSessionListBinding

/**
 * 세션 목록 (기타, 드럼 등) 을 보여주기 위한 리사이클러뷰 어댑터
 *
 * @property itemClick  세션 참가자 각각의 프로필을 볼 수 있도록 하기 위해 프로필 조회 페이지로 이동하는 클릭리스너 전달 (내부 리사이클러뷰 어댑터에 전달)
 */
class SessionListAdapter(val itemClick: (User) -> Unit) :
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
            val adapter = SessionParticipantListAdapter {
                itemClick(it)
            }
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