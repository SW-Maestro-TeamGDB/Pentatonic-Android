package com.team_gdb.pentatonic.ui.create_cover.session_setting

import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.team_gdb.pentatonic.adapter.create_cover.CoverSessionSettingListAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.team_gdb.pentatonic.R
import com.team_gdb.pentatonic.base.BaseFragment
import com.team_gdb.pentatonic.data.model.SessionSettingEntity
import com.team_gdb.pentatonic.databinding.FragmentSessionSettingBinding
import com.team_gdb.pentatonic.ui.create_cover.CreateCoverViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class SessionSettingFragment :
    BaseFragment<FragmentSessionSettingBinding, CreateCoverViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.fragment_session_setting

    override val viewModel: CreateCoverViewModel by sharedViewModel()

    private lateinit var sessionSettingListAdapter: CoverSessionSettingListAdapter

    override fun initStartView() {
        binding.viewModel = this.viewModel
        viewModel.coverSessionConfigList.value = listOf()  // ViewModel List 초기화
    }

    override fun initDataBinding() {
        viewModel.coverSessionConfigList.observe(this) {
            it?.let {
                sessionSettingListAdapter.setItem(it)
            }
        }
    }

    override fun initAfterBinding() {
        sessionSettingListAdapter = CoverSessionSettingListAdapter {
            deleteDialog(it)
        }

        binding.sessionConfigList.apply {
            this.layoutManager = LinearLayoutManager(context)
            adapter = sessionSettingListAdapter
            setHasFixedSize(true)
        }

        binding.addSessionButton.setOnClickListener {
            val bottomSheetDialogFragment = SelectSessionBottomSheetDialog()
            bottomSheetDialogFragment.show(
                requireActivity().supportFragmentManager,
                bottomSheetDialogFragment.tag
            )
        }

        binding.completeSessionSettingButton.setOnClickListener {

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
            getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(requireContext(), R.color.main_regular))
            getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
        }
    }
}