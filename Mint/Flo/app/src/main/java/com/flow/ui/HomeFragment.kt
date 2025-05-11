package com.flow.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.flow.MainActivity
import com.flow.R
import com.flow.SongActivity
import com.flow.data.Album
import com.flow.data.SongDatabase
import com.flow.databinding.FragmentHomeBinding
import com.flow.ui.adapters.AlbumRVAdapter
import com.google.gson.Gson


class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
    private var albumDatas = ArrayList<Album>()

    private lateinit var songDB : SongDatabase

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)


        // 데이터 리스트 생성 더머 데이터
        songDB = SongDatabase.getInstance(requireContext())!!
        albumDatas.addAll(songDB.albumDao().getAlbums()) // songDB에서 album list를 가져옵니다.
        Log.d("albumlist", albumDatas.toString())

        // 어댑터와 데이터 리스트 연결
        val albumRVAdapter = AlbumRVAdapter(albumDatas)
        binding.homeTodayMusicAlbumRv.adapter = albumRVAdapter
        binding.homeTodayMusicAlbumRv.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        albumRVAdapter.setMyItemClickListener(object : AlbumRVAdapter.MyItemClickListener {
            override fun onItemClick(album: Album) {
                changeAlbumFragment(album)
            }

            override fun onRemoveAlbum(position: Int) {
                albumRVAdapter.removeItem(position)
            }

            override fun onPlayAlbum(album: Album) {
                val songs = songDB.songDao().getSongs().filter { it.coverImg == album.coverImg }
                val activity = context as? MainActivity
                activity?.playAlbumSongs(songs)
                playFirstSongInAlbum(album)
            }
        })

        return binding.root
    }



    private fun changeAlbumFragment(album: Album) {
        (context as MainActivity).supportFragmentManager.beginTransaction()
            .replace(R.id.main_frm, AlbumFragment().apply {
                arguments = Bundle().apply {
                    val gson = Gson()
                    val albumJson = gson.toJson(album)
                    putString("album", albumJson)
                }
            })
            .commitAllowingStateLoss()
    }

    /**
     * 앨범의 전체 곡 리스트 전달
     */
    private fun playFirstSongInAlbum(album: Album) {
        val songs = songDB.songDao().getSongs().filter { it.coverImg == album.coverImg }
        if (songs.isEmpty()) return

        val gson = Gson()
        val songListJson = gson.toJson(songs)

        val intent = Intent(requireContext(), SongActivity::class.java).apply {
            putExtra("songList", songListJson)
            putExtra("songId", songs[0].id)
        }

        startActivity(intent)
    }

}