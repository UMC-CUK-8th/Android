package com.flo.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.flow.R
import com.flow.data.Song
import com.flow.data.SongDatabase
import com.flow.databinding.FragmentLockerSavedsongBinding
import com.flow.ui.adapters.SavedSongRVAdapter

class SavedSongFragment : Fragment() {

    private lateinit var binding: FragmentLockerSavedsongBinding
    private lateinit var songDB: SongDatabase
    private lateinit var adapter: SavedSongRVAdapter

    private var externalSelectAllBtn: View? = null
    private var externalDeleteBtn: View? = null

    fun setSelectAllButton(button: View) {
        externalSelectAllBtn = button
    }

    fun setDeleteButton(button: View) {
        externalDeleteBtn = button
    }

    fun enterDeleteMode() {
        externalDeleteBtn?.visibility = View.VISIBLE
        adapter.selectAll()

        externalDeleteBtn?.setOnClickListener {
            val selected = adapter.getSelectedSongs()
            selected.forEach {
                songDB.songDao().updateIsLikeById(false, it.id)
            }
            val updated = songDB.songDao().getLikedSongs(true) as ArrayList<Song>
            adapter.addSongs(updated)
            Toast.makeText(context, "${selected.size}곡 삭제됨", Toast.LENGTH_SHORT).show()
            externalDeleteBtn?.visibility = View.GONE
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLockerSavedsongBinding.inflate(inflater, container, false)
        songDB = SongDatabase.getInstance(requireContext())!!
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        initRecyclerView()
    }

    private fun initRecyclerView() {
        val likedSongs = songDB.songDao().getLikedSongs(true) as ArrayList<Song>
        adapter = SavedSongRVAdapter(likedSongs)
        binding.lockerSavedSongRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.lockerSavedSongRecyclerView.adapter = adapter
    }
}