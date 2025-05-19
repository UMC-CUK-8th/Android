package com.example.week7

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.week7.databinding.FragmentLockerBinding
import com.example.week7.databinding.FragmentSavedSongBinding
import com.google.gson.Gson
import com.example.week7.LockerAlbumRVAdapter
class SavedSongFragment : Fragment() {

    lateinit var songDB: SongDatabase
    lateinit var binding : FragmentSavedSongBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSavedSongBinding.inflate(inflater, container, false)

        songDB = SongDatabase.getInstance(requireContext())!!

        return binding.root

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
        val songList = arrayListOf(
            Song("비의 랩소디", "아이유"),
            Song("아무노래", "지코"),
            Song("좋은 날", "아이유"),
            Song("Blueming", "아이유"),
            Song("너의 의미", "아이유")
        )
        lockerAlbumRVAdapter.addSongs(songList)
        lockerAlbumRVAdapter.addSongs(songDB.songDao().getLikedSongs(true) as ArrayList<Song>)
    }
}