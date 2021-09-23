package com.team_gdb.pentatonic.adapter.library

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.team_gdb.pentatonic.R
import com.team_gdb.pentatonic.data.model.LibraryEntity
import com.team_gdb.pentatonic.data.session.SessionSetting
import com.team_gdb.pentatonic.databinding.ItemLibraryListBinding
import com.team_gdb.pentatonic.util.formatTo
import com.team_gdb.pentatonic.util.toDate

/**
 * 라이브러리에 저장된 커버 목록을 보여주기 위한 리사이클러뷰 어댑터
 *
 * @property itemPlayClick    해당 커버 재생하는 동작
 * @property itemEditClick    해당 커버 정보 수정하는 동작
 * @property itemDeleteClick  해당 커버 삭제하는 동작
 */
class LibraryListAdapter(
    val itemPlayClick: (LibraryEntity) -> Unit,
    val itemEditClick: (LibraryEntity) -> Unit,
    val itemDeleteClick: (String) -> Unit
) :
    RecyclerView.Adapter<LibraryListAdapter.ViewHolder>() {

    private var coverEntityList: List<LibraryEntity> = emptyList()  // 커버 아이템 리스트 정보

    /**
     * 레이아웃 바인딩 통한 ViewHolder 생성 후 반환
     *
     * @param parent    부모 ViewGroup
     * @param viewType  리사이클러 뷰 뷰타입
     * @return          생성된 ViewHolder
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemLibraryListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(coverEntityList[position])
    }

    override fun getItemCount(): Int {
        return coverEntityList.size
    }

    inner class ViewHolder(
        private val binding: ItemLibraryListBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(entity: LibraryEntity) {
            binding.coverNameTextView.text = entity.coverName
            binding.coverOriginalSongTextView.text = entity.originalSong.name
            binding.coverSessionAndDateTextView.text =
                "${SessionSetting.valueOf(entity.coverSession).sessionName} 커버 | ${entity.coverDate.toDate().formatTo("yyyy-MM-dd HH:mm")}"

            Glide.with(binding.root)
                .load(entity.imageUrl)
                .placeholder(R.drawable.placeholder_cover_bg)
                .override(480, 272)
                .into(binding.coverImage)

            binding.playButton.setOnClickListener {
                itemPlayClick(entity)
            }

            binding.editButton.setOnClickListener {
                itemEditClick(entity)
            }

            binding.deleteButton.setOnClickListener {
                itemDeleteClick(entity.id)
            }
        }
    }

    fun setItem(entities: List<LibraryEntity>) {
        this.coverEntityList = entities
        notifyDataSetChanged()
    }
}