package com.example.jack_week3

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.example.jack_week3.databinding.FragmentHomeBinding

class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var bannerAdapter: BannerAdapter
    private val bannerList = listOf(
        BannerItem(R.drawable.banner1),
        BannerItem(R.drawable.banner2),
        BannerItem(R.drawable.banner3)
    )

    private val handler = Handler(Looper.getMainLooper())
    private var currentPage = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupBannerViewPager(view)
        startAutoSlide()
    }

    private fun setupBannerViewPager(view: View) {
        bannerAdapter = BannerAdapter(bannerList)
        view.findViewById<ViewPager2>(R.id.bannerViewPager)?.adapter = bannerAdapter

        view.findViewById<ViewPager2>(R.id.bannerViewPager)?.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                currentPage = position
            }
        })
    }

    private fun startAutoSlide() {
        val runnable = object : Runnable {
            override fun run() {
                if (currentPage == bannerList.size - 1) {
                    currentPage = 0
                } else {
                    currentPage++
                }
                view?.findViewById<ViewPager2>(R.id.bannerViewPager)?.setCurrentItem(currentPage, true)
                handler.postDelayed(this, 3000) // 3초마다 변경
            }
        }
        handler.postDelayed(runnable, 3000)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        handler.removeCallbacksAndMessages(null)
    }
}
