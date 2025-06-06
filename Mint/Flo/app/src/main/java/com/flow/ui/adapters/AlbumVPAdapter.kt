package com.flow.ui.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.flow.ui.DetailFragment
import com.flow.ui.SongFragment
import com.flow.ui.VideoFragment

class AlbumVPAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> SongFragment()
            1 -> DetailFragment()
            else -> VideoFragment()
        }
    }

}