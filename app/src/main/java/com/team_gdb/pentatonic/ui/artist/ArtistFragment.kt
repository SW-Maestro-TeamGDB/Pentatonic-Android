package com.team_gdb.pentatonic.ui.artist

import android.content.Intent
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.team_gdb.pentatonic.R
import com.team_gdb.pentatonic.base.BaseFragment
import com.team_gdb.pentatonic.databinding.FragmentArtistBinding
import com.team_gdb.pentatonic.ui.upload_test.UploadTestActivity

class ArtistFragment : BaseFragment<FragmentArtistBinding, ArtistViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.fragment_artist
    override val viewModel: ArtistViewModel by viewModel()

    override fun initStartView() {
        binding.viewModel = this.viewModel
    }

    override fun initDataBinding() {
    }

    override fun initAfterBinding() {
        binding.testButton.setOnClickListener {
            startActivity(Intent(context, UploadTestActivity::class.java))
        }
    }

}