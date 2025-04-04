package com.flow.ui.adapters

import com.flow.ui.DetailFragment

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class LockerViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 3 // 예: Liked / Playlist / Download

    override fun createFragment(position: Int): Fragment {
        // 실제로는 각각의 Locker 상세 Fragment를 생성
        // 여기서는 예시로 DetailFragment만 재활용
        return DetailFragment()
    }
}
