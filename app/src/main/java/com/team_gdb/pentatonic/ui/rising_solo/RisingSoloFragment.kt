package com.team_gdb.pentatonic.ui.rising_solo

import android.content.Intent
import androidx.recyclerview.widget.LinearLayoutManager
import com.team_gdb.pentatonic.R
import com.team_gdb.pentatonic.adapter.cover_list.CoverVerticalListAdapter
import com.team_gdb.pentatonic.base.BaseFragment
import com.team_gdb.pentatonic.data.model.CoverEntity
import com.team_gdb.pentatonic.data.model.SessionData
import com.team_gdb.pentatonic.databinding.FragmentRisingSoloBinding
import com.team_gdb.pentatonic.ui.cover_view.solo_cover.SoloCoverActivity
import com.team_gdb.pentatonic.ui.lounge.LoungeFragment
import com.team_gdb.pentatonic.ui.lounge.LoungeViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class RisingSoloFragment : BaseFragment<FragmentRisingSoloBinding, LoungeViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.fragment_rising_solo
    override val viewModel: LoungeViewModel by sharedViewModel()

    private lateinit var soloCoverListAdapter: CoverVerticalListAdapter  // 솔로 커버 리스트

    override fun initStartView() {
        // 솔로 커버 리사이클러뷰 어댑터 생성
        soloCoverListAdapter = CoverVerticalListAdapter { coverId, _ ->
            val intent = Intent(requireContext(), SoloCoverActivity::class.java)
            intent.putExtra(LoungeFragment.COVER_ID, coverId)
            startActivity(intent)
        }

        binding.soloCoverList.apply {
            this.layoutManager = LinearLayoutManager(context)
            this.adapter = soloCoverListAdapter
            this.setHasFixedSize(true)
        }
    }

    override fun initDataBinding() {
        viewModel.trendBandsQuery.observe(this) {
            soloCoverListAdapter.setItem(it.filter { it.isSoloBand }.map {
                val sessionDataList = it.session?.map {
                    SessionData(
                        sessionName = it?.position?.rawValue!!,
                        sessionMaxSize = it.maxMember,
                        currentParticipant = it.cover!!.size
                    )
                }
                CoverEntity(
                    id = it.bandId,
                    coverName = it.name,
                    introduction = it.introduce,
                    imageURL = it.backGroundURI,
                    sessionDataList = sessionDataList!!,
                    like = it.likeCount,
                    view = it.viewCount,
                    originalSong = "${it.song.artist} - ${it.song.name}"
                )
            })
        }
    }

    override fun initAfterBinding() {
    }
}