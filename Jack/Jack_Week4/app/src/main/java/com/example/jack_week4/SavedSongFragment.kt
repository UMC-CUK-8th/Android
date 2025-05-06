package com.example.jack_week4

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.jack_week4.databinding.FragmentSavedSongBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SavedSongFragment : Fragment() {

    private lateinit var binding: FragmentSavedSongBinding
    private lateinit var adapter: SongAdapter
    private val songList = mutableListOf(
        Song("Lilac", "아이유 (IU)", 0),
        Song("Lilac", "아이유 (IU)", 0),
        Song("Lilac", "아이유 (IU)", 0)
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSavedSongBinding.inflate(inflater, container, false)

        // 어댑터 설정
        adapter = SongAdapter(songList) { song ->

        }

        binding.savedSongRecyclerView.adapter = adapter
        binding.savedSongRecyclerView.layoutManager = LinearLayoutManager(context)

        return binding.root
    }
}

