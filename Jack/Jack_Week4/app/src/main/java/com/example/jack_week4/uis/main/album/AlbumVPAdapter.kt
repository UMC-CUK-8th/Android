package com.example.jack_week4.uis.main.album

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.jack_week4.uis.main.home.DetailFragment
import com.example.jack_week4.uis.song.SongFragment
import com.example.jack_week4.uis.main.home.VideoFragment

class AlbumVPAdapter(fragment:Fragment) : FragmentStateAdapter(fragment){
    override fun getItemCount(): Int =3

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> SongFragment()
            1 -> DetailFragment()
            else -> VideoFragment()
        }
    }

}