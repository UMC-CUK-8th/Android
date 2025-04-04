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

    // 샘플 앨범 목록 (가짜 데이터)
    private val sampleAlbums = listOf(
        Album("앨범1", R.drawable.ic_album_sample),
        Album("앨범2", R.drawable.ic_album_sample),
        Album("앨범3", R.drawable.ic_album_sample),
        Album("앨범4", R.drawable.ic_album_sample),
        Album("앨범5", R.drawable.ic_album_sample)
    )

    // 배너 이미지 리스트
    private val bannerImages = listOf(
        R.drawable.ic_banner_sample,
        R.drawable.ic_banner_sample,
        R.drawable.ic_banner_sample
    )

    // Handler를 이용한 자동 슬라이드 (3초마다 전환)
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

        // 1) 앨범 RecyclerView 세팅 (수평 스크롤)
        val albumAdapter = AlbumListAdapter(sampleAlbums) { album ->
            // 앨범 클릭 시 처리 (필요하면 구현)
        }
        binding.homeAlbumRv.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.homeAlbumRv.adapter = albumAdapter

        // 2) 배너 ViewPager2 + Indicator
        val bannerAdapter = HomeBannerAdapter(bannerImages)
        binding.homeBannerVp.adapter = bannerAdapter
        binding.homeBannerIndicator.setViewPager(binding.homeBannerVp)

        // 페이지 변경 시 자동 슬라이드 예약
        binding.homeBannerVp.registerOnPageChangeCallback(object :
            androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                // 이전 슬라이드 예약 취소 후 재예약
                sliderHandler.removeCallbacks(sliderRunnable)
                sliderHandler.postDelayed(sliderRunnable, 3000) // 3초 후 전환
            }
        })

        // 3) '앨범 보기' 텍스트 클릭 -> AlbumFragment 이동 (예시)
        binding.albumClickTv.setOnClickListener {
            val albumTitle = "HOME에서 보낸 앨범 제목"
            val albumSinger = "HOME에서 보낸 앨범 가수"

            val fragment = AlbumFragment().apply {
                arguments = Bundle().apply {
                    putString("albumTitle", albumTitle)
                    putString("albumSinger", albumSinger)
                }
            }
            parentFragmentManager.beginTransaction()
                .replace(R.id.main_container, fragment)
                .addToBackStack(null)
                .commit()
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // 슬라이더 콜백 제거
        sliderHandler.removeCallbacks(sliderRunnable)
        _binding = null
    }
}
