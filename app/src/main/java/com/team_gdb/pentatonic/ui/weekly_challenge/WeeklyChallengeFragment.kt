package com.team_gdb.pentatonic.ui.weekly_challenge

import android.content.Intent
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.team_gdb.pentatonic.R
import com.team_gdb.pentatonic.TestData
import com.team_gdb.pentatonic.adapter.cover_list.CoverVerticalListAdapter
import com.team_gdb.pentatonic.adapter.cover_list.WeeklyChallengeCoverListAdapter
import com.team_gdb.pentatonic.base.BaseFragment
import com.team_gdb.pentatonic.data.model.SongEntity
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.team_gdb.pentatonic.databinding.FragmentWeeklyChallengeBinding
import com.team_gdb.pentatonic.ui.cover_view.band_cover.BandCoverActivity
import com.team_gdb.pentatonic.ui.create_cover.CreateCoverActivity
import com.team_gdb.pentatonic.ui.create_record.CreateRecordActivity
import com.team_gdb.pentatonic.ui.lounge.LoungeFragment
import com.team_gdb.pentatonic.ui.lounge.LoungeFragment.Companion.COVER_ENTITY
import com.team_gdb.pentatonic.ui.lounge.LoungeFragment.Companion.COVER_ID
import com.team_gdb.pentatonic.ui.studio.StudioFragment
import com.team_gdb.pentatonic.ui.studio.StudioFragment.Companion.BAND_COVER
import com.team_gdb.pentatonic.ui.studio.StudioFragment.Companion.COVER_MODE
import com.team_gdb.pentatonic.ui.studio.StudioFragment.Companion.SONG_ENTITY

class WeeklyChallengeFragment :
    BaseFragment<FragmentWeeklyChallengeBinding, WeeklyChallengeViewModel>() {
    override val viewModel: WeeklyChallengeViewModel by viewModel()
    override val layoutResourceId: Int
        get() = R.layout.fragment_weekly_challenge

    private val weeklyChallengeFragmentArgs: WeeklyChallengeFragmentArgs by navArgs()

    private lateinit var weeklyChallengeCoverListAdapter: WeeklyChallengeCoverListAdapter

    override fun initStartView() {
        binding.viewModel = this.viewModel
        binding.lifecycleOwner = this

        // 이전 Fragment 에서 넘어온 Argument (위클리 챌린지 곡의 SongID)
        viewModel.getWeeklyChallengeSongInfo(weeklyChallengeFragmentArgs.weeklyChallengeSongId)

        weeklyChallengeCoverListAdapter = WeeklyChallengeCoverListAdapter {
            val intent = Intent(requireContext(), BandCoverActivity::class.java)
            intent.putExtra(COVER_ID, it)
            startActivity(intent)
        }
        binding.weeklyChallengeCoverList.apply {
            this.layoutManager = LinearLayoutManager(context)
            this.adapter = weeklyChallengeCoverListAdapter
            this.setHasFixedSize(true)
        }
    }

    override fun initDataBinding() {
        viewModel.weeklyChallengeSongImage.observe(this) {
            applyWeeklyChallengeImage(it)
        }

        viewModel.weeklyChallengeCoverList.observe(this) {
            weeklyChallengeCoverListAdapter.setItem(it)
        }
    }

    override fun initAfterBinding() {
        binding.backButton.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_weekly_challenge_to_navigation_lounge)
        }

        binding.createCoverButton.setOnClickListener {
            val intent =Intent(requireContext(), CreateCoverActivity::class.java)
            intent.putExtra(COVER_MODE, BAND_COVER)
            intent.putExtra(SONG_ENTITY, viewModel.weeklyChallengeSongEntity.value)
            startActivity(intent)
        }

        binding.createRecordButton.setOnClickListener {
            val intent = Intent(requireContext(), CreateRecordActivity::class.java)
            intent.putExtra(SONG_ENTITY, viewModel.weeklyChallengeSongEntity.value)
            startActivity(intent)
        }
    }

    private fun applyWeeklyChallengeImage(imageUrl: String) {
        Glide.with(this)
            .load(imageUrl)
            .placeholder(R.drawable.placeholder_cover_bg)
            .into(binding.weeklyChallengeSongImage)
    }

}