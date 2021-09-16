package com.team_gdb.pentatonic.adapter.cover_view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.team_gdb.pentatonic.GetBandCoverInfoQuery
import com.team_gdb.pentatonic.R
import com.team_gdb.pentatonic.data.model.SessionData
import com.team_gdb.pentatonic.data.model.UserEntity
import com.team_gdb.pentatonic.data.session.SessionSetting
import com.team_gdb.pentatonic.databinding.ItemSessionListBinding
import com.team_gdb.pentatonic.ui.record.RecordGuideBottomSheetDialog


/**
 * 커버를 구성하고 있는 세션 (보컬, 일렉기타, 드럼 등) 구성 리사이클러뷰 어댑터
 * - 각 세션 내에는, 해당 세션에 참가하고 있는 '참가자 목록'이 포함됨
 * - 따라서 하위에 SessionParticipantListAdapter 포함
 *
 * @property itemClick               // 사용자 프로필 이미지 눌렀을 때 해당 사용자 프로필 페이지로 이동하는 동작
 * @property participantButtonClick  // 해당 세션의 '참가하기' 버튼이 눌렸을 때의 동작
 */
class SessionConfigListAdapter(
    val itemClick: (String) -> Unit,
    val participantButtonClick: (GetBandCoverInfoQuery.Session) -> Unit
) : RecyclerView.Adapter<SessionConfigListAdapter.ViewHolder>() {

    private var sessionDataList: List<GetBandCoverInfoQuery.Session?> =
        emptyList()  // 커버를 구성하고 있는 세션의 리스트

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
        sessionDataList[position]?.let { holder.bind(it) }
    }

    override fun getItemCount(): Int {
        return sessionDataList.size
    }

    inner class ViewHolder(
        private val binding: ItemSessionListBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: GetBandCoverInfoQuery.Session) {
            // 세션명과 참가 현황 (현재 참가 인원 / 최대 참가 가능 인원)
            binding.sessionNameTextView.text =
                SessionSetting.valueOf(item.position.toString()).sessionName
            binding.sessionPeopleTextView.text =
                "${item.cover?.size}/${item.maxMember}"

            // 만약 해당 세션에 더이상 참가할 수 없다면 '참가하기' 버튼 비활성화
            if (item.maxMember == item.cover?.size) {
                binding.participateButton.run {
                    isEnabled = false
                    background = ContextCompat.getDrawable(
                        context,
                        R.drawable.custom_radius_background_gray_sharpen
                    )
                }
            } else {  // 참가할 수 있다면, 커버 참가 동작 수행
                binding.participateButton.setOnClickListener {
                    participantButtonClick(item)
                }
            }

            // 해당 세션을 구성하는 참가자들을 보여주기 위한 리사이클러뷰 구성
            val adapter = SessionParticipantListAdapter {
                itemClick(it)
            }
            adapter.setItem(items = item.cover)

            binding.sessionParticipantList.apply {
                this.layoutManager = LinearLayoutManager(context).apply {
                    this.orientation = LinearLayoutManager.HORIZONTAL
                }
                this.adapter = adapter
                this.setHasFixedSize(true)
            }
        }
    }

    fun setItem(items: List<GetBandCoverInfoQuery.Session?>) {
        this.sessionDataList = items
        notifyDataSetChanged()
    }
}