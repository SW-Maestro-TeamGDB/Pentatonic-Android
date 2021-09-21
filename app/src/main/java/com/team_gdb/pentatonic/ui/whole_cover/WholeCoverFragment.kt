package com.team_gdb.pentatonic.ui.whole_cover

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.team_gdb.pentatonic.R
import com.team_gdb.pentatonic.adapter.cover_list.CoverHorizontalListAdapter
import com.team_gdb.pentatonic.adapter.cover_list.CoverVerticalListAdapter
import com.team_gdb.pentatonic.base.BaseFragment
import com.team_gdb.pentatonic.data.model.CoverEntity
import com.team_gdb.pentatonic.data.model.SessionData
import com.team_gdb.pentatonic.databinding.FragmentWholeCoverBinding
import com.team_gdb.pentatonic.ui.band_cover.BandCoverActivity
import com.team_gdb.pentatonic.ui.lounge.LoungeFragment
import com.team_gdb.pentatonic.ui.lounge.LoungeFragment.Companion.COVER_ENTITY
import com.team_gdb.pentatonic.ui.lounge.LoungeFragment.Companion.COVER_ID
import com.team_gdb.pentatonic.util.setQueryDebounce

import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class WholeCoverFragment : BaseFragment<FragmentWholeCoverBinding, WholeCoverViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.fragment_whole_cover
    override val viewModel: WholeCoverViewModel by viewModel()

    private lateinit var coverListAdapter: CoverVerticalListAdapter  // 솔로 커버 리스트

    override fun initStartView() {
        binding.viewModel = this.viewModel
        addDisposable(binding.searchView.setQueryDebounce({
            // it 키워드에 사용자의 쿼리가 담기게 됨
            viewModel.getCover(it)
        }, binding.textClearButton))
        coverListAdapter = CoverVerticalListAdapter {
            val intent = Intent(requireContext(), BandCoverActivity::class.java)
            intent.putExtra(COVER_ID, it.id)
            startActivity(intent)
        }
        binding.coverList.apply {
            this.layoutManager = LinearLayoutManager(context)
            this.adapter = coverListAdapter
            this.setHasFixedSize(true)
        }
    }

    override fun initDataBinding() {
        viewModel.coverList.observe(this) {
            // CoverEntity List 를 리사이클러뷰에 바인딩
            coverListAdapter.setItem(it)
        }
    }

    override fun initAfterBinding() {
        // 초기 화면 : 모든 커버 표시
        viewModel.getCover("")

        binding.textClearButton.setOnClickListener {
            binding.searchView.text.clear()
        }
    }
}