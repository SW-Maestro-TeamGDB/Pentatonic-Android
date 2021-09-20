package com.team_gdb.pentatonic.adapter.select_session

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.team_gdb.pentatonic.GetBandCoverInfoQuery
import com.team_gdb.pentatonic.data.model.SessionData
import com.team_gdb.pentatonic.data.model.UserEntity
import com.team_gdb.pentatonic.data.session.SessionSetting
import com.team_gdb.pentatonic.databinding.ItemSelectSessionListBinding
import timber.log.Timber


/**
 * 밴드 커버를 구성할 세션을 선택하기 위한 리스트
 *
 * @property itemClick    사용자 프로필 이미지 눌렀을 때, 해당 사용자를 밴드에 참여시키는 동작
 */
class SelectSessionListAdapter(
    val itemClick: (GetBandCoverInfoQuery.Session, String) -> Unit
) : RecyclerView.Adapter<SelectSessionListAdapter.ViewHolder>() {

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
            ItemSelectSessionListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        sessionDataList[position]?.let { holder.bind(it) }
    }

    override fun getItemCount(): Int {
        return sessionDataList.size
    }

    inner class ViewHolder(
        private val binding: ItemSelectSessionListBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: GetBandCoverInfoQuery.Session) {
            binding.sessionNameTextView.text =
                SessionSetting.valueOf(item.position.name).sessionName

            // 해당 세션을 구성하는 참가자들을 보여주기 위한 리사이클러뷰 구성
            val adapter = SelectSessionParticipantListAdapter {
                itemClick(item, it)
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

    fun setItem(items: List<GetBandCoverInfoQuery.Session?>?) {
        this.sessionDataList = items!!
        notifyDataSetChanged()
    }
}