package com.team_gdb.pentatonic.ui.cover_view.band_cover

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.team_gdb.pentatonic.R
import com.team_gdb.pentatonic.adapter.select_session.SelectSessionListAdapter
import com.team_gdb.pentatonic.base.BaseBottomSheetDialogFragment
import com.team_gdb.pentatonic.databinding.DialogBandSessionSelectBinding
import com.team_gdb.pentatonic.ui.cover_view.CoverViewViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

import timber.log.Timber

class SessionSelectBottomSheetDialog :
    BaseBottomSheetDialogFragment<DialogBandSessionSelectBinding, CoverViewViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.dialog_band_session_select
    override val viewModel: CoverViewViewModel by sharedViewModel()

    private lateinit var sessionListAdapter: SelectSessionListAdapter


    override fun initStartView() {
        binding.viewModel = this.viewModel
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun initDataBinding() {
        // BandCoverActivity 에서 넘어온 밴드 상세 정보 (공유된 데이터)
        viewModel.bandInfo.observe(this) {
            sessionListAdapter = SelectSessionListAdapter { sessionData, coverURL ->
                viewModel.addSession(
                    sessionName = sessionData.position.name,
                    coverURL = coverURL
                )
            }
            binding.sessionList.apply {
                this.layoutManager = LinearLayoutManager(context)
                this.adapter = sessionListAdapter
                this.setHasFixedSize(true)
            }

            // 아직 한 명도 참여하지 않은 세션에 대해서는 리스트 생성 X
            sessionListAdapter.setItem(it.session?.filter {
                !it?.cover.isNullOrEmpty()
            })
        }

        // 사용자에 의해 선택된 세션 정보
        viewModel.selectedSessionLiveData.observe(this) {
            Timber.d(it.toString())
            // 완료하기 버튼 활성화 여부 : 1개 이상 세션 선택 시 활성화
            binding.completeSessionSelectButton.isEnabled = it.size > 0
        }
    }

    override fun initAfterBinding() {
        binding.closeButton.setOnClickListener {
            viewModel.selectedSessionLiveData.postValue(hashMapOf())
            dismiss()
        }

        // 밴드 세션 선택 완료 버튼을 누르면, 해당 세션으로 구성된 음원 요청
        binding.completeSessionSelectButton.setOnClickListener {
            viewModel.getMergedCover()
            dismiss()
        }
    }
}