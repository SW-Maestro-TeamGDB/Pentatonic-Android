package com.team_gdb.pentatonic.ui.my_page.cover_history

import android.content.Intent
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.team_gdb.pentatonic.R
import com.team_gdb.pentatonic.adapter.cover_list.CoverVerticalListAdapter
import com.team_gdb.pentatonic.base.BaseFragment
import com.team_gdb.pentatonic.data.model.CoverEntity
import com.team_gdb.pentatonic.data.model.SessionData
import com.team_gdb.pentatonic.databinding.FragmentCoverHistoryBinding
import com.team_gdb.pentatonic.ui.cover_view.band_cover.BandCoverActivity
import com.team_gdb.pentatonic.ui.cover_view.solo_cover.SoloCoverActivity
import com.team_gdb.pentatonic.ui.lounge.LoungeFragment
import com.team_gdb.pentatonic.ui.my_page.MyPageViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class CoverHistoryFragment : BaseFragment<FragmentCoverHistoryBinding, MyPageViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.fragment_cover_history
    override val viewModel: MyPageViewModel by sharedViewModel()

    private lateinit var coverListAdapter: CoverVerticalListAdapter

    override fun initStartView() {
        binding.viewModel = this.viewModel
        binding.titleBar.titleTextView.text = "커버 히스토리"
        coverListAdapter = CoverVerticalListAdapter { coverId, isSoloBand ->
            val intent = if (isSoloBand) {
                Intent(requireContext(), SoloCoverActivity::class.java)
            } else {
                Intent(requireContext(), BandCoverActivity::class.java)
            }
            intent.putExtra(LoungeFragment.COVER_ID, coverId)
            startActivity(intent)
        }
        binding.coverList.apply {
            this.layoutManager = LinearLayoutManager(context)
            this.adapter = coverListAdapter
            this.setHasFixedSize(true)
        }
    }

    override fun initDataBinding() {
        viewModel.coverHistoryList.observe(this) {
            // CoverEntity List 를 리사이클러뷰에 바인딩
            coverListAdapter.setItem(it.map {
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
            findNavController().popBackStack(R.id.navigation_cover_history, true)
        }
    }
}