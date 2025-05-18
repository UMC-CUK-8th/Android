package com.example.week7

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.week7.databinding.FragmentHomeBinding
import com.google.gson.Gson
import java.util.*
import kotlin.collections.ArrayList

class HomeFragment : Fragment(), CommunicationInterface {

    private lateinit var binding: FragmentHomeBinding
    private var albumDatas = ArrayList<Album>()
    private lateinit var songDB: SongDatabase

    private val timer = Timer()
    private val handler = Handler(Looper.getMainLooper())

    override fun sendData(album: Album) {
        if (activity is MainActivity) {
            (activity as MainActivity).updateMainPlayerCl(album)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        inputDummyAlbums()

        songDB = SongDatabase.getInstance(requireContext())!!
        albumDatas.addAll(songDB.albumDao().getAlbums())
        Log.d("albumlist", albumDatas.toString())

        val albumRVAdapter = AlbumRVAdapter(albumDatas)
        binding.homeTodayMusicAlbumRv.adapter = albumRVAdapter
        binding.homeTodayMusicAlbumRv.layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)

        albumRVAdapter.setItemClickListener(object : AlbumRVAdapter.OnItemClickListener {
            override fun onItemClick(album: Album) {
                changeAlbumFragment(album)
            }

            override fun onPlayAlbum(album: Album) {
                sendData(album)
            }
        })

        val bannerAdapter = BannerVPAdapter(this)
        bannerAdapter.addFragment(BannerFragment(R.drawable.img_home_viewpager_exp))
        bannerAdapter.addFragment(BannerFragment(R.drawable.img_home_viewpager_exp2))

        binding.homeBannerVp.adapter = bannerAdapter
        binding.homeBannerVp.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        binding.homeBannerIndicator.setViewPager(binding.homeBannerVp)

        autoSlide(bannerAdapter)

        val pannelAdpater = PannelVpAdapter(this)
        pannelAdpater.addFragment(PannelFragment(R.drawable.img_first_album_default))
        pannelAdpater.addFragment(PannelFragment(R.drawable.img_first_album_default))
        binding.homePannelBackgroundVp.adapter = pannelAdpater
        binding.homePannelBackgroundVp.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        binding.homePannelIndicator.setViewPager(binding.homePannelBackgroundVp)

        return binding.root
    }

    private fun autoSlide(adapter: BannerVPAdapter) {
        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                handler.post {
                    val nextItem = binding.homeBannerVp.currentItem + 1
                    binding.homeBannerVp.currentItem =
                        if (nextItem < adapter.itemCount) nextItem else 0
                }
            }
        }, 3000, 3000)
    }

    private fun changeAlbumFragment(album: Album) {
        (context as MainActivity).supportFragmentManager.beginTransaction()
            .replace(R.id.main_frm, AlbumFragment().apply {
                arguments = Bundle().apply {
                    val gson = Gson()
                    putString("album", gson.toJson(album))
                }
            })
            .commitAllowingStateLoss()
    }

    private fun inputDummyAlbums() {
        val songDB = SongDatabase.getInstance(requireActivity())!!
        val songs = songDB.albumDao().getAlbums()
        if (songs.isNotEmpty()) return

        songDB.albumDao().insert(
            Album(1, "IU 5th Album 'LILAC'", "아이유 (IU)", R.drawable.img_album_exp2)
        )
        songDB.albumDao().insert(
            Album(2, "Butter", "방탄소년단 (BTS)", R.drawable.img_album_exp)
        )
        songDB.albumDao().insert(
            Album(3, "iScreaM Vol.10: Next Level Remixes", "에스파 (AESPA)", R.drawable.img_album_exp3)
        )
        songDB.albumDao().insert(
            Album(4, "Map of the Soul Persona", "뮤직 보이 (Music Boy)", R.drawable.img_album_exp4)
        )
        songDB.albumDao().insert(
            Album(5, "Great!", "모모랜드 (MOMOLAND)", R.drawable.img_album_exp5)
        )

        Log.d("DB data", songDB.albumDao().getAlbums().toString())
    }
}
