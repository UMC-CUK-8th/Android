package com.flo.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.flow.data.Song
import com.flow.data.SongDatabase
import com.flow.databinding.FragmentLockerSavedsongBinding
import com.flow.ui.adapters.SavedSongRVAdapter
import com.flow.R

class SavedSongFragment : Fragment() {
    lateinit var binding: FragmentLockerSavedsongBinding
    lateinit var songDB: SongDatabase

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLockerSavedsongBinding.inflate(inflater, container, false)
        songDB = SongDatabase.getInstance(requireContext())!!

        insertLikedDummySongsIfNone()

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        initRecyclerview()
    }

    private fun initRecyclerview() {
        binding.lockerSavedSongRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        val songRVAdapter = SavedSongRVAdapter()

        songRVAdapter.setMyItemClickListener(object : SavedSongRVAdapter.MyItemClickListener {
            override fun onRemoveSong(songId: Int) {
                songDB.songDao().updateIsLikeById(false, songId)
            }
        })

        binding.lockerSavedSongRecyclerView.adapter = songRVAdapter

        val likedSongs = songDB.songDao().getLikedSongs(true)
        songRVAdapter.addSongs(likedSongs as ArrayList<Song>)
    }

    private fun insertLikedDummySongsIfNone() {
        val likedSongs = songDB.songDao().getLikedSongs(true)
        if (likedSongs.isNotEmpty()) return

        val dummySongs = listOf(
            Song("Lilac", "아이유 (IU)", 0, 244, false, "music_lilac", R.drawable.img_album_exp, true),
            Song("Fool", "WINNER", 0, 222, false, "music_fool", R.drawable.img_album_exp2, true),
            Song("너를 사랑하고 있어", "백현 (BAEKHYUN)", 0, 197, false, "music_mylove", R.drawable.img_album_exp3, true),
            Song("Next Level", "aespa", 0, 221, false, "music_nextlevel", R.drawable.img_album_exp4, true),
            Song("낙하 (with IU)", "AKMU (악뮤)", 0, 212, false, "music_nakka", R.drawable.img_album_exp5, true),
            Song("LOVE DIVE", "IVE", 0, 176, false, "music_lovedive", R.drawable.img_album_exp6, true)
        )

        dummySongs.forEach { songDB.songDao().insert(it) }
        Log.d("DummyInsert", "좋아요된 더미 곡 6개 삽입 완료")
    }
}
