package com.team_gdb.pentatonic.ui.rising_band

import android.content.Intent
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.team_gdb.pentatonic.R
import com.team_gdb.pentatonic.adapter.cover_list.CoverVerticalListAdapter
import com.team_gdb.pentatonic.base.BaseFragment
import com.team_gdb.pentatonic.data.model.CoverEntity
import com.team_gdb.pentatonic.data.model.SessionData
import com.team_gdb.pentatonic.databinding.FragmentRisingBandBinding
import com.team_gdb.pentatonic.ui.cover_view.band_cover.BandCoverActivity
import com.team_gdb.pentatonic.ui.lounge.LoungeFragment
import com.team_gdb.pentatonic.ui.lounge.LoungeViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class RisingBandFragment : BaseFragment<FragmentRisingBandBinding, LoungeViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.fragment_rising_band
    override val viewModel: LoungeViewModel by sharedViewModel()

    private lateinit var bandCoverListAdapter: CoverVerticalListAdapter  // 밴드 커버 리스트

    override fun initStartView() {
        binding.titleBar.titleTextView.text = "떠오르는 밴드 커버"

        // 밴드 커버 리사이클러뷰 어댑터 생성
        bandCoverListAdapter = CoverVerticalListAdapter { coverId, _ ->
            val intent = Intent(requireContext(), BandCoverActivity::class.java)
            intent.putExtra(LoungeFragment.COVER_ID, coverId)
            startActivity(intent)
        }

        binding.risingBandList.apply {
            this.layoutManager = LinearLayoutManager(context)
            this.adapter = bandCoverListAdapter
            this.setHasFixedSize(true)
        }
    }

    override fun initDataBinding() {
        viewModel.trendBandsQuery.observe(this) {
            bandCoverListAdapter.setItem(it.filter { !it.isSoloBand }.map {
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
        binding.titleBar.backButton.setOnClickListener {
            findNavController().popBackStack(R.id.navigation_rising_band, true)
        }
    }
}