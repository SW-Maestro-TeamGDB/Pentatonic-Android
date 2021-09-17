package com.team_gdb.pentatonic.adapter.select_session

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.team_gdb.pentatonic.GetBandCoverInfoQuery
import com.team_gdb.pentatonic.R
import com.team_gdb.pentatonic.data.model.UserEntity
import com.team_gdb.pentatonic.databinding.ItemSelectSessionParticipantListBinding
import timber.log.Timber

/**
 * 각 세션의 참가자 목록을 보여주기 위한 리사이클러뷰 어댑터
 * - 세션 구성 리스트 (SessionConfigListAdapter) 에 종속됨
 *
 * @property itemClick  밴드에 해당 세션을 포함시키는 동작
 */
class SelectSessionParticipantListAdapter(val itemClick: (GetBandCoverInfoQuery.CoverBy) -> Unit) :
    RecyclerView.Adapter<SelectSessionParticipantListAdapter.ViewHolder>() {

    private var participantList: List<GetBandCoverInfoQuery.Cover>? = emptyList()  // 각 세션의 참가자들 목록
    var selectedSession: Int = -1  // 선택된 아이템 포지션 -> 이를 활용하여 ViewHolder 바인딩 시 하이라이팅 여부 나눔

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
        holder.bind(participantList!![position], position)
    }

    override fun getItemCount(): Int {
        return participantList!!.size
    }

    inner class ViewHolder(
        private val binding: ItemSelectSessionParticipantListBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(entity: GetBandCoverInfoQuery.Cover, position: Int) {
            // 리스트 첫 아이템의 경우에는 어느정도 마진을 줘야함
            if (position == 0) {
                val param = binding.itemRootLayout.layoutParams as ViewGroup.MarginLayoutParams
                param.setMargins(64, 0, 0, 0)
                binding.itemRootLayout.layoutParams = param
            }

            // 해당 사용자의 프로필 사진
            Glide.with(binding.root)
                .load(entity.coverBy.profileURI)
                .placeholder(R.drawable.profile_image_placeholder)
                .override(80, 80)
                .into(binding.userProfileImage)

            // 선택된 아이템이 아니라면 기본 스타일로 표현
            if (selectedSession != adapterPosition) {
                setItemBasic()
            } else {  // 선택된 아이템이라면, 하이라이팅 처리
                setItemHighlighting()
            }

            // 해당 사용자의 닉네임
            binding.usernameTextView.text = entity.coverBy.username

            // 해당 세션 클릭시, ViewModel 에 선택 정보 저장하는 동작
            binding.root.setOnClickListener {
                if (selectedSession != adapterPosition) {
                    selectedSession = adapterPosition
                    notifyDataSetChanged()
                    itemClick(entity.coverBy)
                } else if(selectedSession == adapterPosition) {
                    selectedSession = -1
                    itemClick(entity.coverBy)
                    notifyDataSetChanged()
                }
            }
        }

        /**
         * 아이템을 하이라이팅 하는 함수
         */
        private fun setItemHighlighting() {
            binding.itemCardLayout.setCardBackgroundColor(Color.LTGRAY)
            binding.usernameTextView.setTextColor(Color.WHITE)
        }

        /**
         * 아이템에 기본 스타일 적용하는 함수
         */
        private fun setItemBasic() {
            binding.itemCardLayout.setCardBackgroundColor(Color.WHITE)
            binding.usernameTextView.setTextColor(Color.BLACK)
        }
    }

    fun setItem(items: List<GetBandCoverInfoQuery.Cover>?) {
        this.participantList = items!!
        notifyDataSetChanged()
    }
}