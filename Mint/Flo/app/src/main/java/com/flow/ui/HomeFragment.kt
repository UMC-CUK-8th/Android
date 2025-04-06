package com.flow.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.flow.R
import com.flow.data.Album
import com.flow.databinding.FragmentHomeBinding
import com.flow.ui.adapters.AlbumListAdapter
import com.flow.ui.adapters.HomeBannerAdapter

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val sampleAlbums = listOf(
        Album("앨범1", R.drawable.ic_album_sample),
        Album("앨범2", R.drawable.ic_album_sample),
        Album("앨범3", R.drawable.ic_album_sample),
        Album("앨범4", R.drawable.ic_album_sample),
        Album("앨범5", R.drawable.ic_album_sample)
    )

    private val bannerImages = listOf(
        R.drawable.ic_banner_sample,
        R.drawable.ic_banner_sample,
        R.drawable.ic_banner_sample
    )

    private val sliderHandler = Handler(Looper.getMainLooper())
    private val sliderRunnable = Runnable {
        binding.homeBannerVp.currentItem =
            (binding.homeBannerVp.currentItem + 1) % bannerImages.size
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        val albumAdapter = AlbumListAdapter(sampleAlbums) { album ->
            // 앨범 클릭 시 처리
            val fragment = AlbumFragment().apply {
                arguments = Bundle().apply {
                    putString("albumTitle", album.title)
                }
            }
            parentFragmentManager.beginTransaction()
                .replace(R.id.main_container, fragment)
                .addToBackStack(null)
                .commit()
        }

        binding.homeAlbumRv.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.homeAlbumRv.adapter = albumAdapter

        val bannerAdapter = HomeBannerAdapter(bannerImages)
        binding.homeBannerVp.adapter = bannerAdapter
        binding.homeBannerIndicator.setViewPager(binding.homeBannerVp)

        binding.homeBannerVp.registerOnPageChangeCallback(object :
            androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                sliderHandler.removeCallbacks(sliderRunnable)
                sliderHandler.postDelayed(sliderRunnable, 3000)
            }
        })

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        sliderHandler.removeCallbacks(sliderRunnable)
        _binding = null
    }
}