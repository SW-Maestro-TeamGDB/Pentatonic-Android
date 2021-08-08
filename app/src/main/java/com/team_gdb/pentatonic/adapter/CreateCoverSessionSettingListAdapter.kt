
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.team_gdb.pentatonic.data.model.SessionSettingEntity
import com.team_gdb.pentatonic.databinding.ItemSessionSettingListBinding

class CreateCoverSessionSettingListAdapter(val itemLongClick: (SessionSettingEntity) -> Unit) :
    RecyclerView.Adapter<CreateCoverSessionSettingListAdapter.ViewHolder>() {

    private var sessionSettingList: List<SessionSettingEntity> = emptyList()  // 세션 악기 아이템 리스트 정보

    /**
     * 레이아웃 바인딩 통한 ViewHolder 생성 후 반환
     *
     * @param parent    부모 ViewGroup
     * @param viewType  리사이클러 뷰 뷰타입
     * @return          생성된 ViewHolder
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemSessionSettingListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(sessionSettingList[position])
    }

    override fun getItemCount(): Int {
        return sessionSettingList.size
    }

    inner class ViewHolder(
        private val binding: ItemSessionSettingListBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(entity: SessionSettingEntity) {
            Glide.with(binding.root)
                .load(entity.sessionSetting.icon)
                .override(80, 80)
                .into(binding.sessionIconImage)

            binding.sessionNameTextView.text = entity.sessionSetting.sessionName
            binding.countTextView.text = entity.count.toString()
        }
    }

    fun setItem(entities: List<SessionSettingEntity>) {
        this.sessionSettingList = entities
        notifyDataSetChanged()
    }
}