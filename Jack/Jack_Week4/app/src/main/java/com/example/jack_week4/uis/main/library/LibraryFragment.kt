package com.example.jack_week4.uis.main.library

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.jack_week4.uis.signin.LoginActivity
import com.example.jack_week4.uis.main.MainActivity
import com.example.jack_week4.uis.song.SongDatabase
import com.example.jack_week4.databinding.FragmentLibraryBinding
import com.google.android.material.tabs.TabLayoutMediator

class LibraryFragment : Fragment() {
    lateinit var binding: FragmentLibraryBinding
    private val information = arrayListOf("저장한곡", "음악파일", "저장앨범")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLibraryBinding.inflate(inflater, container, false)

        val libraryAdapter = LibraryVPAdapter(this)
        binding.libraryContentVp.adapter = libraryAdapter
        TabLayoutMediator(binding.libraryContentTb, binding.libraryContentVp) { tab, position ->
            tab.text = information[position]
        }.attach()

        binding.libraryLoginTv.setOnClickListener {
            startActivity(Intent(activity, LoginActivity::class.java))
        }

        val songDB = SongDatabase.getInstance(requireContext())!!
        val userId = getUserId()
        val likedAlbums = songDB.albumDao().getLikedAlbums(userId)

        Log.d("LOKERFRAG/GET_ALBUMS", likedAlbums.toString())

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        initViews()
    }

    private fun getUserId(): Int {
        val spf = activity?.getSharedPreferences("auth", AppCompatActivity.MODE_PRIVATE)
        return spf?.getInt("userId", 0) ?: 0
    }

    private fun initViews() {
        val userId = getUserId()
        if (userId == 0) {
            binding.libraryLoginTv.text = "로그인"
            binding.libraryLoginTv.setOnClickListener {
                startActivity(Intent(activity, LoginActivity::class.java))
            }
        } else {
            binding.libraryLoginTv.text = "로그아웃"
            binding.libraryLoginTv.setOnClickListener {
                logout()
                startActivity(Intent(activity, MainActivity::class.java))
            }
        }
    }

    private fun logout() {
        val spf = activity?.getSharedPreferences("auth", AppCompatActivity.MODE_PRIVATE)
        val editor = spf!!.edit()
        editor.remove("userId")
        editor.apply()
    }
}
