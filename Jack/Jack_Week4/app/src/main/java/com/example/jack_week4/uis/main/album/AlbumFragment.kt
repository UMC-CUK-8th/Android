package com.example.jack_week4.uis.main.album

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.jack_week4.uis.main.home.HomeFragment
import com.example.jack_week4.uis.main.MainActivity
import com.example.jack_week4.R
import com.example.jack_week4.uis.song.SongDatabase
import com.example.jack_week4.data.entities.Album
import com.example.jack_week4.data.entities.Like
import com.example.jack_week4.databinding.FragmentAlbumBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.Gson

class AlbumFragment : Fragment() {
    private lateinit var binding: FragmentAlbumBinding
    private val information = arrayListOf("수록곡", "상세정보", "영상")

    private var isLiked: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAlbumBinding.inflate(inflater, container, false)

        val albumData = arguments?.getString("album")
        val gson = Gson()

        val album = gson.fromJson(albumData, Album::class.java)
        isLiked = isLikedAlbum(album.id)

        setViews(album)
        initViewPager()
        setClickListeners(album)


        return binding.root
    }

    private fun setViews(album: Album) {
        binding.albumMusicTitle.text = album.title.toString()
        binding.albumSinger.text = album.singer.toString()
        binding.albumAlbumImg.setImageResource(album.coverImg!!)

        if(isLiked) {
            binding.albumLike.setImageResource(R.drawable.ic_my_like_on)
        } else {
            binding.albumLike.setImageResource(R.drawable.ic_my_like_off)
        }
    }

    private fun setClickListeners(album: Album) {
        val userId: Int = getJwt()

        binding.albumLike.setOnClickListener {
            if(isLiked) {
                binding.albumLike.setImageResource(R.drawable.ic_my_like_off)
                disLikeAlbum(userId, album.id)
            } else {
                binding.albumLike.setImageResource(R.drawable.ic_my_like_on)
                likeAlbum(userId, album.id)
            }

            isLiked = !isLiked
        }

        //set click listener
        binding.albumBack.setOnClickListener {
            (context as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_frm, HomeFragment())
                .commitAllowingStateLoss()
        }
    }

    private fun initViewPager() {
        //init viewpager
        val albumAdapter = AlbumVPAdapter(this)

        binding.albumContentVp.adapter = albumAdapter
        TabLayoutMediator(binding.albumContentTb, binding.albumContentVp) { tab, position ->
            tab.text = information[position]
        }.attach()
    }

    private fun disLikeAlbum(userId: Int, albumId: Int) {
        val songDB = SongDatabase.getInstance(requireContext())!!
        songDB.albumDao().disLikeAlbum(userId, albumId)
    }

    private fun likeAlbum(userId: Int, albumId: Int) {
        val songDB = SongDatabase.getInstance(requireContext())!!
        val like = Like(userId, albumId)

        songDB.albumDao().likeAlbum(like)
    }


    private fun isLikedAlbum(albumId: Int): Boolean {
        val songDB = SongDatabase.getInstance(requireContext())!!
        val userId = getJwt()

        val likeId: Int? = songDB.albumDao().isLikedAlbum(userId, albumId)

        return likeId != null
    }

    private fun getJwt(): Int {
        val spf = activity?.getSharedPreferences("auth", AppCompatActivity.MODE_PRIVATE)
        val jwt = spf!!.getInt("jwt", 0)
        Log.d("MAIN_ACT/GET_JWT", "jwt_token: $jwt")

        return jwt
    }
}