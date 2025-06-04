package com.example.jack_week4.uis.main.library

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.jack_week4.uis.song.SongFirebase
import com.example.jack_week4.databinding.FragmentLibrarySavedsongBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class SavedSongFragment : Fragment() {

    private lateinit var binding: FragmentLibrarySavedsongBinding
    private lateinit var adapter: SavedSongRVAdapter
    private lateinit var database: DatabaseReference
    private val likedSongs = arrayListOf<SongFirebase>()

    private val userId: String
        get() {
            val uid = FirebaseAuth.getInstance().currentUser?.uid
            Log.d("SavedSongFragment", "현재 로그인한 userId = $uid")
            return uid ?: "test_user"
        }



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLibrarySavedsongBinding.inflate(inflater, container, false)
        adapter = SavedSongRVAdapter()
        database = FirebaseDatabase.getInstance().getReference("likes").child(userId)

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        initRecyclerView()
        loadLikedSongs()
    }

    private fun initRecyclerView() {
        binding.librarySavedSongRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.librarySavedSongRecyclerView.adapter = adapter

        adapter.setMyItemClickListener(object : SavedSongRVAdapter.MyItemClickListener {
            override fun onRemoveSong(songId: Int) {
                database.child(songId.toString()).removeValue()
                loadLikedSongs()
            }
        })
    }

    private fun loadLikedSongs() {
        database.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                likedSongs.clear()
                for (child in snapshot.children) {
                    val songFirebase = child.getValue(SongFirebase::class.java)
                    if (songFirebase != null) {
                        likedSongs.add(songFirebase)
                    } else {
                        Log.d("SavedSongFragment", "Failed to parse SongFirebase for key ${child.key}")
                    }
                }
                adapter.addSongs(likedSongs)
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }
}
