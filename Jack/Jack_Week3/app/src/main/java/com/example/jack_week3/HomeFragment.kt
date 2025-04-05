package com.example.jack_week3

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.jack_week3.databinding.FragmentHomeBinding

class HomeFragment : Fragment(R.layout.fragment_home) {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var albumAdapter: AlbumAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        // 앨범 리스트 데이터
        val albumList = listOf(
            Album("라일락", "아이유", R.drawable.img_album_exp2),
            Album("Celebrity", "아이유", R.drawable.img_album_exp2),
            Album("Blueming", "아이유", R.drawable.img_album_exp2)
        )

        // 어댑터 설정
        albumAdapter = AlbumAdapter(albumList, object : AlbumAdapter.OnItemClickListener {
            override fun onItemClick(album: Album) {
                (context as MainActivity).supportFragmentManager.beginTransaction()
                    .replace(R.id.my_nav_host, AlbumFragment())
                    .addToBackStack(null)
                    .commitAllowingStateLoss()
            }
        })

        binding.homeTodayMusicAlbum.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.homeTodayMusicAlbum.adapter = albumAdapter

        // 패널 클릭 시 앨범 프래그먼트로 이동


        // 배너 뷰페이저
        val bannerAdapter = BannerVPAdapter(this)
        bannerAdapter.addFragment(BannerFragment(R.drawable.img_home_viewpager_exp))
        bannerAdapter.addFragment(BannerFragment(R.drawable.img_home_viewpager_exp2))
        binding.homeBanner.adapter = bannerAdapter
        binding.homeBanner.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        return binding.root
    }
}
