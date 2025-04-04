package com.flow.ui.adapters

import com.flow.ui.DetailFragment
import com.flow.ui.VideoFragment
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class AlbumViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> DetailFragment()
            else -> VideoFragment()
        }
    }
}
