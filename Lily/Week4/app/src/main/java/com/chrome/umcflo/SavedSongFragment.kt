package com.chrome.umcflo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.chrome.umcflo.databinding.FragmentLockerBinding
import com.chrome.umcflo.databinding.FragmentSavedSongBinding
import com.google.gson.Gson

class SavedSongFragment : Fragment() {
    private lateinit var binding: FragmentSavedSongBinding
    private lateinit var adapter: SongAdapter
    private val songList = mutableListOf<Song>(
        Song("Lilac", "아이유 (IU)", 0),
        Song("Blueming", "아이유 (IU)", 0),
        Song("Celebrity", "아이유 (IU)", 0),
        Song("스물셋", "아이유 (IU)", 0),
        Song("홀씨", "아이유 (IU)", 0),
        Song("Love Wins All", "아이유 (IU)", 0),
        Song("밤편지", "아이유 (IU)", 0),
        Song("팔레트", "아이유 (IU)", 0),
        Song("에잇", "아이유 (IU)", 0),
        Song("내 손을 잡아", "아이유 (IU)", 0),
        Song("어푸", "아이유 (IU)", 0),
        Song("Shopper", "아이유 (IU)", 0),
        Song("좋은 날", "아이유 (IU)", 0),
        Song("Strawberry Moon", "아이유 (IU)", 0),
        Song("금요일에 만나요", "아이유 (IU)", 0)




                private var albumDatas = ArrayList<Album>()
    lateinit var binding : FragmentSavedSongBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSavedSongBinding.inflate(inflater, container, false)

        albumDatas.apply {
            add(Album("Butter", "방탄소년단 (BTS)", R.drawable.img_album_exp))
            add(Album("Lilac", "아이유 (IU)", R.drawable.img_album_exp2))
            add(Album("Next Level", "에스파 (AESPA)", R.drawable.img_album_exp3))
            add(Album("Boy with Luv", "방탄소년단 (BTS)", R.drawable.img_album_exp4))
            add(Album("BBoom BBoom", "모모랜드 (MOMOLAND)", R.drawable.img_album_exp5))
            add(Album("Weekend", "태연 (Tae Yeon)", R.drawable.img_album_exp6))
        }

        val lockerAlbumRVAdapter = LockerAlbumRVAdapter(albumDatas)
        binding.lockerMusicAlbumRv.adapter = lockerAlbumRVAdapter
        binding.lockerMusicAlbumRv.layoutManager = LinearLayoutManager(requireActivity())

        return binding.root
    }
}



    override fun onStart() {
        super.onStart()
        initRecyclerview()
    }

    private fun initRecyclerview(){
        binding.lockerMusicAlbumRv.layoutManager = LinearLayoutManager(requireActivity())
        val lockerAlbumRVAdapter = LockerAlbumRVAdapter()

        lockerAlbumRVAdapter.setItemClickListener(object : LockerAlbumRVAdapter.OnItemClickListener {

            override fun onRemoveAlbum(songId: Int) {
                songDB.songDao().updateIsLikeById(false, songId)
            }
        })
        binding.lockerMusicAlbumRv.adapter = lockerAlbumRVAdapter
        lockerAlbumRVAdapter.addSongs(songDB.songDao().getLikedSongs(true) as ArrayList<Song>)
    }
}