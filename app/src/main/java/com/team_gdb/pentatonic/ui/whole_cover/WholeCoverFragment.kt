package com.team_gdb.pentatonic.ui.whole_cover

import android.content.Intent
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.team_gdb.pentatonic.R
import com.team_gdb.pentatonic.adapter.cover_list.CoverVerticalListAdapter
import com.team_gdb.pentatonic.base.BaseFragment
import com.team_gdb.pentatonic.data.genre.GenreList.genreList
import com.team_gdb.pentatonic.data.level.LevelList.levelList
import com.team_gdb.pentatonic.databinding.FragmentWholeCoverBinding
import com.team_gdb.pentatonic.ui.cover_view.band_cover.BandCoverActivity
import com.team_gdb.pentatonic.ui.cover_view.solo_cover.SoloCoverActivity
import com.team_gdb.pentatonic.ui.lounge.LoungeFragment.Companion.COVER_ID
import com.team_gdb.pentatonic.util.setQueryDebounce

import org.koin.androidx.viewmodel.ext.android.viewModel

class WholeCoverFragment : BaseFragment<FragmentWholeCoverBinding, WholeCoverViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.fragment_whole_cover
    override val viewModel: WholeCoverViewModel by viewModel()

    private lateinit var coverListAdapter: CoverVerticalListAdapter

    override fun onResume() {
        super.onResume()

        viewModel.getCover()
    }

    override fun initStartView() {
        binding.viewModel = this.viewModel
        addDisposable(binding.searchView.setQueryDebounce({
            // it 키워드에 사용자의 쿼리가 담기게 됨
            viewModel.getCover()
        }, binding.textClearButton))
        coverListAdapter = CoverVerticalListAdapter { coverId, isSoloBand ->
            val intent = if (isSoloBand) {
                Intent(requireContext(), SoloCoverActivity::class.java)
            } else {
                Intent(requireContext(), BandCoverActivity::class.java)
            }
            intent.putExtra(COVER_ID, coverId)
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

        viewModel.genre.observe(this) {
            // 장르 새로 선택할 때마다 다시 쿼리
            viewModel.getCover()
        }

        viewModel.level.observe(this) {
            // 레벨 새로 선택할 때마다 다시 쿼리
            viewModel.getCover()
        }
    }

    override fun initAfterBinding() {
        binding.titleBar.backButton.setOnClickListener {
            findNavController().popBackStack(R.id.navigation_whole_cover, true)
        }

        binding.titleBar.titleTextView.text = "전체 커버"

        binding.textClearButton.setOnClickListener {
            binding.searchView.text.clear()
        }

        val genreItems = resources.getStringArray(R.array.genre_array)
        binding.genreSpinner.adapter =
            ArrayAdapter(requireContext(), R.layout.item_genre_spinner, genreItems)
        binding.genreSpinner.onItemSelectedListener = genreSpinnerItemSelectedListener

        val levelItems = resources.getStringArray(R.array.level_array)
        binding.levelSpinner.adapter =
            ArrayAdapter(requireContext(), R.layout.item_level_spinner, levelItems)
        binding.levelSpinner.onItemSelectedListener = levelSpinnerItemSelectedListener

    }


    private val genreSpinnerItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(
            parent: AdapterView<*>?,
            view: View?,
            position: Int,
            id: Long
        ) {
            if (position == 0) {
                (parent?.getChildAt(0) as TextView).text = "장르"
                viewModel.genre.value = null
            } else {
                viewModel.genre.value = genreList[position]
            }
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {}
    }

    private val levelSpinnerItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(
            parent: AdapterView<*>?,
            view: View?,
            position: Int,
            id: Long
        ) {
            (parent?.getChildAt(0) as TextView).run {
                if (position == 0) {
                    text = "난이도"
                    textSize = 14F
                    viewModel.level.value = null
                } else {
                    viewModel.level.value = levelList[position]
                }
                setTextColor(ContextCompat.getColor(context, R.color.white))
            }
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {}
    }
}