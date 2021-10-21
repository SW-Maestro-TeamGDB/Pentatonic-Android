package com.team_gdb.pentatonic.adapter.select_session

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.team_gdb.pentatonic.GetSongInstrumentQuery
import com.team_gdb.pentatonic.data.session.Session
import com.team_gdb.pentatonic.databinding.ItemSelectInstListBinding

/**
 * 솔로 커버 재생시 함께 재생할 MR 구성을 선택하는 리스트 어댑터
 *
 * @property itemClick : 세션 선택
 */
class SelectInstListAdapter(
    val itemClick: (GetSongInstrumentQuery.Instrument, String) -> Unit
) : RecyclerView.Adapter<SelectInstListAdapter.ViewHolder>() {

    private val selectedInst: MutableSet<GetSongInstrumentQuery.Instrument> = mutableSetOf()
    private var sessionDataList: List<GetSongInstrumentQuery.Instrument> =
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
            ItemSelectInstListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        sessionDataList[position].let { holder.bind(it) }
    }

    override fun getItemCount(): Int {
        return sessionDataList.size
    }

    inner class ViewHolder(
        private val binding: ItemSelectInstListBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: GetSongInstrumentQuery.Instrument) {
            binding.sessionNameTextView.text =
                Session.valueOf(item.position.name).sessionName

            // 선택된 아이템이라면 하이라이팅 처리
            if (selectedInst.contains(item)) {
                setItemHighlighting()
            } else {  // 선택된 아이템이 아니라면 기본 처리
                setItemBasic()
            }
            // 해당 세션 클릭시, ViewModel 에 선택 정보 저장하는 동작
            // - coverURL 은 고유하므로, { 세션명 to coverURL } 형태로 저장
            binding.root.setOnClickListener {
                if (selectedInst.contains(item)) {
                    selectedInst.remove(item)
                } else {
                    selectedInst.add(item)
                }
                notifyDataSetChanged()
                itemClick(item, item.instURI)
            }
        }

        /**
         * 아이템을 하이라이팅 하는 함수
         */
        private fun setItemHighlighting() {
            binding.itemCardLayout.setCardBackgroundColor(Color.LTGRAY)
            binding.sessionNameTextView.setTextColor(Color.WHITE)
            binding.sessionImage.setColorFilter(Color.argb(255, 255, 255, 255))
        }

        /**
         * 아이템에 기본 스타일 적용하는 함수
         */
        private fun setItemBasic() {
            binding.itemCardLayout.setCardBackgroundColor(Color.WHITE)
            binding.sessionNameTextView.setTextColor(Color.BLACK)
            binding.sessionImage.setColorFilter(Color.argb(255, 0, 0, 0))
        }
    }


    fun setItem(items: List<GetSongInstrumentQuery.Instrument>) {
        this.sessionDataList = items
        notifyDataSetChanged()
    }
}