package com.team_gdb.pentatonic.ui.whole_cover

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.team_gdb.pentatonic.R
import com.team_gdb.pentatonic.base.BaseFragment
import com.team_gdb.pentatonic.databinding.FragmentWholeCoverBinding
import com.team_gdb.pentatonic.util.setQueryDebounce

import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class WholeCoverFragment : BaseFragment<FragmentWholeCoverBinding, WholeCoverViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.fragment_whole_cover
    override val viewModel: WholeCoverViewModel by viewModel()

    override fun initStartView() {
        binding.viewModel = this.viewModel
        addDisposable(binding.searchView.setQueryDebounce {
            // it 키워드에 사용자의 쿼리가 담기게 됨
        })
    }

    override fun initDataBinding() {
    }

    override fun initAfterBinding() {
    }
}