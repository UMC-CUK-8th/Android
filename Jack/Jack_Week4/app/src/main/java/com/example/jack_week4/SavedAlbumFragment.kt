package com.example.jack_week4

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.jack_week4.databinding.FragmentLibrarySavedalbumBinding

class SavedAlbumFragment : Fragment() {
    lateinit var binding: FragmentLibrarySavedalbumBinding
    lateinit var albumDB: SongDatabase

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLibrarySavedalbumBinding.inflate(inflater, container, false)
        albumDB = SongDatabase.getInstance(requireContext())!!
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        initRecyclerview()
    }

    private fun initRecyclerview() {
        binding.lockerSavedSongRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        val albumRVAdapter = AlbumLibraryRVAdapter()
        albumRVAdapter.setMyItemClickListener(object : AlbumLibraryRVAdapter.MyItemClickListener {
            override fun onRemoveSong(songId: Int) {
                albumDB.albumDao().getLikedAlbums(getJwt())
            }
        })

        binding.lockerSavedSongRecyclerView.adapter = albumRVAdapter
        albumRVAdapter.addAlbums(albumDB.albumDao().getLikedAlbums(getJwt()) as ArrayList)
    }

    private fun getJwt(): Int {
        val spf = activity?.getSharedPreferences("auth", AppCompatActivity.MODE_PRIVATE)
        val jwt = spf!!.getInt("jwt", 0)
        Log.d("MAIN_ACT/GET_JWT", "jwt_token: $jwt")
        return jwt
    }
}
