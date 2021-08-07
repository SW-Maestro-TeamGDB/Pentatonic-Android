package com.team_gdb.pentatonic.ui.band_cover

import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.team_gdb.pentatonic.R
import com.team_gdb.pentatonic.adapter.SessionListAdapter
import com.team_gdb.pentatonic.base.BaseFragment
import com.team_gdb.pentatonic.data.model.CoverEntity
import com.team_gdb.pentatonic.databinding.FragmentBandCoverBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class BandCoverFragment : BaseFragment<FragmentBandCoverBinding, BandCoverViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.fragment_band_cover
    override val viewModel: BandCoverViewModel by viewModel()

    private val coverItemArgs: BandCoverFragmentArgs by navArgs()
    private val coverEntity: CoverEntity by lazy {
        coverItemArgs.bandCoverEntity
    }

    override fun initStartView() {
        binding.viewModel = this.viewModel
    }

    override fun initDataBinding() {
    }

    override fun initAfterBinding() {
        binding.coverNameTextView.text = coverEntity.coverName
        binding.coverIntroductionTextView.text = coverEntity.introduction
        if (coverEntity.imageUrl.isNotBlank()) {
            Glide.with(this)
                .load(coverEntity.imageUrl)
                .override(480, 272)
                .into(binding.coverImage)
        } else {
            Glide.with(this)
                .load(R.drawable.placeholder_cover_bg)
                .override(480, 272)
                .into(binding.coverImage)
        }

        val sessionListAdapter = SessionListAdapter{
            findNavController().navigate(BandCoverFragmentDirections.actionNavigationBandCoverToNavigationProfile(it))
        }
        binding.sessionList.apply {
            this.layoutManager = LinearLayoutManager(context)
            this.adapter = sessionListAdapter
            this.setHasFixedSize(true)
        }
        sessionListAdapter.setItem(coverEntity.sessionDataList)
    }
}