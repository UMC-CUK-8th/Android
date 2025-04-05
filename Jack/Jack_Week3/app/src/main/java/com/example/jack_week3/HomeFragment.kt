package com.example.jack_week3

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.jack_week3.databinding.FragmentHomeBinding

class HomeFragment : Fragment(R.layout.fragment_home) {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var albumAdapter: AlbumAdapter
    private val sliderHandler = Handler(Looper.getMainLooper())

    private val panelImageList = listOf(
        R.drawable.btn_panel_play_large,
        R.drawable.btn_panel_play_large,
        R.drawable.btn_panel_play_large
    )

    private var panelImageIndex = 0

    // Runnable 따로 선언해서 onPause/onResume에서도 관리
    private val panelSliderRunnable = object : Runnable {
        override fun run() {
            panelImageIndex = (panelImageIndex + 1) % panelImageList.size
            binding.homePannelAlbumImgIv.setImageResource(panelImageList[panelImageIndex])
            sliderHandler.postDelayed(this, 3000) // 3초마다 실행
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        // 앨범 리스트
        val albumList = listOf(
            Album("라일락", "아이유", R.drawable.img_album_exp2),
            Album("라일락", "아이유", R.drawable.img_album_exp2),
            Album("라일락", "아이유", R.drawable.img_album_exp2),
            Album("라일락", "아이유", R.drawable.img_album_exp2)
        )

        // 앨범 어댑터 및 리사이클러뷰 연결
        albumAdapter = AlbumAdapter(albumList, object : AlbumAdapter.OnItemClickListener {
            override fun onItemClick(album: Album) {
                (context as MainActivity).supportFragmentManager.beginTransaction()
                    .replace(R.id.my_nav_host, AlbumFragment())
                    .commitAllowingStateLoss()
            }
        })
        binding.homeTodayMusicAlbum.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.homeTodayMusicAlbum.adapter = albumAdapter

        // 패널 클릭 시 이동
        binding.homePannelAlbumImgIv.setOnClickListener {
            (context as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.my_nav_host, AlbumFragment())
                .commitAllowingStateLoss()
        }

        // 초기 이미지 세팅
        binding.homePannelAlbumImgIv.setImageResource(panelImageList[panelImageIndex])

        // 슬라이더 시작
        sliderHandler.postDelayed(panelSliderRunnable, 3000)

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        sliderHandler.postDelayed(panelSliderRunnable, 3000)
    }

    override fun onPause() {
        super.onPause()
        sliderHandler.removeCallbacks(panelSliderRunnable)
    }
}
