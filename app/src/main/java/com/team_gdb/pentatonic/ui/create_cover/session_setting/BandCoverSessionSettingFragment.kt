package com.team_gdb.pentatonic.ui.create_cover.session_setting

import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.team_gdb.pentatonic.adapter.create_cover.CoverSessionSettingListAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.team_gdb.pentatonic.R
import com.team_gdb.pentatonic.base.BaseFragment
import com.team_gdb.pentatonic.data.model.SessionSettingEntity
import com.team_gdb.pentatonic.databinding.FragmentBandCoverSessionSettingBinding
import com.team_gdb.pentatonic.ui.create_cover.CreateCoverViewModel
import com.team_gdb.pentatonic.util.PlayAnimation.playErrorAnimationOnEditText
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class BandCoverSessionSettingFragment :
    BaseFragment<FragmentBandCoverSessionSettingBinding, CreateCoverViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.fragment_band_cover_session_setting

    override val viewModel: CreateCoverViewModel by sharedViewModel()

    private lateinit var sessionSettingListAdapter: CoverSessionSettingListAdapter

    override fun initStartView() {
        binding.viewModel = this.viewModel
        viewModel.coverSessionConfigList.value = listOf()  // ViewModel List 초기화
        sessionSettingListAdapter = CoverSessionSettingListAdapter {
            deleteDialog(it)
        }
        binding.sessionConfigList.apply {
            this.layoutManager = LinearLayoutManager(context)
            adapter = sessionSettingListAdapter
            setHasFixedSize(true)
        }
    }

    override fun initDataBinding() {
        viewModel.coverSessionConfigList.observe(this) {
            it?.let {
                sessionSettingListAdapter.setItem(it)
                if (it.isEmpty()) {
                    binding.addSessionButton.text = "+ 자신의 세션 선택하기"
                } else {
                    binding.addSessionButton.text = "+ 세션 생성하기"
                }
            }
        }
    }

    override fun initAfterBinding() {
        binding.addSessionButton.setOnClickListener {
            val bottomSheetDialogFragment = SelectSessionBottomSheetDialog()
            bottomSheetDialogFragment.show(
                requireActivity().supportFragmentManager,
                bottomSheetDialogFragment.tag
            )
        }

        binding.completeSessionSettingButton.setOnClickListener {
            if (sessionSettingListAdapter.itemCount == 0) {
                playErrorAnimationOnEditText(binding.addSessionButton)
                Toast.makeText(context, "세션은 1개 이상 꼭 포함되어야 합니다!", Toast.LENGTH_LONG).apply {
                    setMargin(0F, 0.1F)
                    show()
                }
            } else {
                viewModel.completeCreateCover()
            }
        }
    }

    /**
     * 특정 세션 아이템 길게 눌렀을 때 동작하는 다이얼로그
     * - 확인 시 세션 아이템을 삭제함
     */
    private fun deleteDialog(item: SessionSettingEntity) {
        val builder = AlertDialog.Builder(requireContext())
        builder.apply {
            this.setMessage("해당 세션을 삭제하시겠습니까?")
            this.setNegativeButton("아니요") { _, _ -> }
            this.setPositiveButton("네") { _, _ ->
                val sessionConfigList = viewModel.coverSessionConfigList.value?.toMutableList()
                sessionConfigList?.remove(item)
                viewModel.coverSessionConfigList.value = sessionConfigList?.toList()
            }
        }
        builder.show().run {
            getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.main_regular
                )
            )
            getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.black
                )
            )
        }
    }
}