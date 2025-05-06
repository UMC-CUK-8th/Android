package com.example.jack_week4

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.jack_week4.databinding.FragmentAlbumBinding
import com.google.gson.Gson
import com.google.android.material.tabs.TabLayoutMediator

class AlbumFragment : Fragment() {

    private lateinit var binding: FragmentAlbumBinding
    private val information = arrayListOf("수록곡", "상세정보", "영상")
    private var isLiked: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAlbumBinding.inflate(inflater, container, false)

        val albumData = arguments?.getString("album")
        val gson = Gson()
        val album = gson.fromJson(albumData, Album::class.java)

        setViews(album)
        initViewPager()
        setClickListeners(album)

        return binding.root
    }

    private fun setViews(album: Album) {
        binding.albumMusicTitle.text = album.title
        binding.albumSinger.text = album.singer
        binding.albumAlbumImg.setImageResource(album.coverImage!!)

        if (isLiked) {
            binding.albumLike.setImageResource(R.drawable.ic_my_like_on)
        } else {
            binding.albumLike.setImageResource(R.drawable.ic_my_like_off)
        }
    }

    private fun setClickListeners(album: Album) {
        // 앨범의 첫 번째 곡을 선택하는 로직 추가
        binding.albumPlayBlack.setOnClickListener {
            val firstSong = album.songs?.get(0)

            // MainActivity로 첫 번째 곡 정보 전달
            val intent = Intent(context, MainActivity::class.java).apply {
                putExtra("title", firstSong?.title)
                putExtra("singer", firstSong?.singer)
                putExtra("song", firstSong?.second)
                putExtra("playTime", firstSong?.playTime)
                putExtra("isPlaying", firstSong?.isPlaying)
                putExtra("music", firstSong?.music)
            }
            startActivity(intent)
        }

        // 뒤로 가기 버튼
        binding.albumBack.setOnClickListener {
            (context as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_frm, HomeFragment())
                .commitAllowingStateLoss()
        }
    }

    private fun initViewPager() {
        val albumAdapter = AlbumVPAdapter(this)
        binding.albumContentVp.adapter = albumAdapter
        TabLayoutMediator(binding.albumContentTb, binding.albumContentVp) { tab, position ->
            tab.text = information[position]
        }.attach()
    }
}
