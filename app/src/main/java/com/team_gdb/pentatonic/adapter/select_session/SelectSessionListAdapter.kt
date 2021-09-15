package com.team_gdb.pentatonic.adapter.select_session

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.team_gdb.pentatonic.data.model.SessionData
import com.team_gdb.pentatonic.data.model.UserEntity
import com.team_gdb.pentatonic.databinding.ItemSelectSessionListBinding


/**
 * 커버를 구성하고 있는 세션 (보컬, 일렉기타, 드럼 등) 구성 리사이클러뷰 어댑터
 * - 각 세션 내에는, 해당 세션에 참가하고 있는 '참가자 목록'이 포함됨
 * - 따라서 하위에 SessionParticipantListAdapter 포함
 *
 * @property itemClick               // 사용자 프로필 이미지 눌렀을 때 해당 사용자 프로필 페이지로 이동하는 동작
 * @property participantButtonClick  // 해당 세션의 '참가하기' 버튼이 눌렸을 때의 동작
 */
class SelectSessionListAdapter(
    val itemClick: (UserEntity) -> Unit,
    val participantButtonClick: (SessionData) -> Unit
) : RecyclerView.Adapter<SelectSessionListAdapter.ViewHolder>() {

    private var sessionDataList: List<SessionData> = emptyList()  // 커버를 구성하고 있는 세션의 리스트

    /**
     * 레이아웃 바인딩 통한 ViewHolder 생성 후 반환
     *
     * @param parent    부모 ViewGroup
     * @param viewType  리사이클러 뷰 뷰타입
     * @return          생성된 ViewHolder
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemSelectSessionListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(sessionDataList[position])
    }

    override fun getItemCount(): Int {
        return sessionDataList.size
    }

    inner class ViewHolder(
        private val binding: ItemSelectSessionListBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: SessionData) {
            binding.sessionNameTextView.text = item.sessionName

            // 해당 세션을 구성하는 참가자들을 보여주기 위한 리사이클러뷰 구성
            val adapter = SelectSessionParticipantListAdapter {
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