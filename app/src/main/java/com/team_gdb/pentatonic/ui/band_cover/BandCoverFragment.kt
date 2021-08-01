package com.team_gdb.pentatonic.ui.band_cover

import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.team_gdb.pentatonic.R
import com.team_gdb.pentatonic.base.BaseFragment
import com.team_gdb.pentatonic.data.model.CoverItem
import com.team_gdb.pentatonic.databinding.FragmentBandCoverBinding
import com.team_gdb.pentatonic.ui.lounge.LoungeFragmentDirections
import org.koin.androidx.viewmodel.ext.android.viewModel

class BandCoverFragment : BaseFragment<FragmentBandCoverBinding, BandCoverViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.fragment_band_cover
    override val viewModel: BandCoverViewModel by viewModel()

    private val coverItemArgs: BandCoverFragmentArgs by navArgs()
    private val coverItem: CoverItem by lazy {
        coverItemArgs.bandCoverItem
    }

    override fun initStartView() {
        binding.viewModel = this.viewModel
    }

    override fun initDataBinding() {
    }

    override fun initAfterBinding() {
        binding.coverNameTextView.text = coverItem.coverName
        binding.coverIntroductionTextView.text = coverItem.introduction
        if (coverItem.imageUrl.isNotBlank()) {
            Glide.with(this)
                .load(coverItem.imageUrl)
                .into(binding.coverImage)
        } else {
            Glide.with(this)
                .load(R.drawable.placeholder_cover_bg)
                .into(binding.coverImage)
        }
    }
}