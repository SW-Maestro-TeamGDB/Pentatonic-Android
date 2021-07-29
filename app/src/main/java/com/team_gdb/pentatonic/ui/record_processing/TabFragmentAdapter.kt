package com.team_gdb.pentatonic.ui.record_processing

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.team_gdb.pentatonic.ui.record_processing.RecordProcessingActivity.Companion.NUM_PAGES

class TabFragmentAdapter(fragment: FragmentActivity) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = NUM_PAGES

    override fun createFragment(position: Int): Fragment {
        return if (position == 0) {
            ControlFragment.newInstance("Control", "")
        } else {
            EffectFragment.newInstance("Effect", "")
        }
    }
}