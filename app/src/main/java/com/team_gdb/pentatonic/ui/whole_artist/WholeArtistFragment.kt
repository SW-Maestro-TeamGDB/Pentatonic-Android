package com.team_gdb.pentatonic.ui.whole_artist

import android.content.Intent
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.team_gdb.pentatonic.R
import com.team_gdb.pentatonic.adapter.artist.ArtistListAdapter
import com.team_gdb.pentatonic.adapter.cover_list.CoverVerticalListAdapter
import com.team_gdb.pentatonic.adapter.ranking.ArtistRankingListAdapter
import com.team_gdb.pentatonic.base.BaseFragment
import com.team_gdb.pentatonic.data.genre.GenreList.genreList
import com.team_gdb.pentatonic.data.level.LevelList.levelList
import com.team_gdb.pentatonic.databinding.FragmentWholeArtistBinding
import com.team_gdb.pentatonic.databinding.FragmentWholeCoverBinding
import com.team_gdb.pentatonic.ui.cover_view.band_cover.BandCoverActivity
import com.team_gdb.pentatonic.ui.cover_view.solo_cover.SoloCoverActivity
import com.team_gdb.pentatonic.ui.lounge.LoungeFragment.Companion.COVER_ID
import com.team_gdb.pentatonic.ui.profile.ProfileActivity
import com.team_gdb.pentatonic.util.setQueryDebounce

import org.koin.androidx.viewmodel.ext.android.viewModel

class WholeArtistFragment : BaseFragment<FragmentWholeArtistBinding, WholeArtistViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.fragment_whole_artist
    override val viewModel: WholeArtistViewModel by viewModel()

    private lateinit var artistListAdapter: ArtistListAdapter

    override fun onResume() {
        super.onResume()

        viewModel.getUserList()
    }

    override fun initStartView() {
        binding.viewModel = this.viewModel
        binding.titleBar.titleTextView.text = "아티스트 검색"

        viewModel.getUserList()

        // 아티스트 리스트 어댑터
        artistListAdapter = ArtistListAdapter {
            val intent = Intent(requireContext(), ProfileActivity::class.java)
            intent.putExtra(ProfileActivity.USER_ID, it)
            startActivity(intent)
        }

        addDisposable(binding.searchView.setQueryDebounce({
            // it 키워드에 사용자의 쿼리가 담기게 됨
            viewModel.getUserList()
        }, binding.textClearButton))

        binding.artistList.apply {
            this.layoutManager = LinearLayoutManager(context)
            this.adapter = artistListAdapter
            this.setHasFixedSize(true)
        }
    }

    override fun initDataBinding() {
        viewModel.userList.observe(this) {
            // CoverEntity List 를 리사이클러뷰에 바인딩
            artistListAdapter.setItem(it)
        }
    }

    override fun initAfterBinding() {
        binding.titleBar.backButton.setOnClickListener {
            findNavController().popBackStack(R.id.navigation_whole_artist, true)
        }

        binding.textClearButton.setOnClickListener {
            binding.searchView.text.clear()
        }

    }

}