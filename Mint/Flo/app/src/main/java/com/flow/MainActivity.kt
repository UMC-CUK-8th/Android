package com.flow

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.flow.SongActivity
import com.flow.data.Album
import com.flow.data.Song
import com.flow.databinding.ActivityMainBinding
import com.flow.data.SongDatabase
import com.flow.ui.HomeFragment
import com.flow.ui.LookFragment
import com.flow.ui.SearchFragment
import com.google.gson.Gson
import kotlin.collections.isNotEmpty
import kotlin.jvm.java



class MainActivity : AppCompatActivity() {


    lateinit var binding: ActivityMainBinding
    private var song: Song = Song()
    private var gson: Gson = Gson()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_FLO)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        inputDummySongs()
        inputDummyAlbums()
        initBottomNavigation()

        val spf = getSharedPreferences("song", MODE_PRIVATE)
        val isPlaying = spf.getBoolean("isPlaying", false)
        song.isPlaying = isPlaying
        setPlayerStatus(isPlaying)

        binding.mainMiniplayerBtn.setOnClickListener {
            setPlayerStatus(true)
        }

        binding.mainPauseBtn.setOnClickListener {
            setPlayerStatus(false)
        }

        binding.mainPlayerCl.setOnClickListener {
            val editor = getSharedPreferences("song", MODE_PRIVATE).edit()
            editor.putInt("songId", song.id)
            editor.apply()

            val intent = Intent(this, SongActivity::class.java)
            startActivity(intent)
        }
    }


    private fun setPlayerStatus(isPlaying: Boolean) {
        song.isPlaying = isPlaying

        if (isPlaying) {
            binding.mainMiniplayerBtn.visibility = View.GONE
            binding.mainPauseBtn.visibility = View.VISIBLE
        } else {
            binding.mainMiniplayerBtn.visibility = View.VISIBLE
            binding.mainPauseBtn.visibility = View.GONE
        }

        // 상태 저장
        val sharedPreferences = getSharedPreferences("song", MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putBoolean("isPlaying", isPlaying)
            apply()
        }
    }



    private fun getJwt(): String? {
        val spf = this.getSharedPreferences("auth2", AppCompatActivity.MODE_PRIVATE)

        return spf!!.getString("jwt", "")
    }

    override fun onStart() {
        super.onStart()
//        val sharedPreferences = getSharedPreferences("song", MODE_PRIVATE)
//        val songJson = sharedPreferences.getString("songData", null)
//
//        song = if(songJson == null){
//            Song("라일락", "아이유(IU)", 0,60, false, "music_lilac")
//        } else {
//            gson.fromJson(songJson, Song::class.java)
//        }
        val spf = getSharedPreferences("song", MODE_PRIVATE)
        val songId = spf.getInt("songId", 0)

        val songDB = SongDatabase.getInstance(this)!!

        song = if (songId == 0) {
            songDB.songDao().getSong(1)
        } else {
            songDB.songDao().getSong(songId)
        }

        Log.d("song ID", song.id.toString())
        setMiniPlayer(song)
    }

    private fun initBottomNavigation() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_frm, HomeFragment())
            .commitAllowingStateLoss()
        binding.mainBnv.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.homeFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, HomeFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
                R.id.lookFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, LookFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
                R.id.searchFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, SearchFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
                R.id.lockerFragment -> {
                    supportFragmentManager.beginTransaction()
//                        .replace(R.id.main_frm, LockerFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
            }
            false
        }
    }

    private fun setMiniPlayer(song: Song) {
        binding.mainMiniplayerTitleTv.text = song.title
        binding.mainMiniplayerSingerTv.text = song.singer
        binding.mainMiniplayerProgressSb.progress = (song.second * 100000) / song.playTime
    }

    private fun inputDummySongs() {
        val songDB = SongDatabase.getInstance(this)!!
        val songs = songDB.songDao().getSongs()

        if (songs.isNotEmpty()) return

        songDB.songDao().insert(
            Song(
                "Lilac",
                "아이유 (IU)",
                0,
                244,
                false,
                "music_lilac",
                R.drawable.img_album_exp,
                false,
            )
        )

        songDB.songDao().insert(
            Song(
                "Fool",
                "WINNER",
                0,
                222,
                false,
                "music_fool",
                R.drawable.img_album_exp2,
                false,
            )
        )

        songDB.songDao().insert(
            Song(
                "너를 사랑하고 있어",
                "백현 (BAEKHYUN)",
                0,
                197,
                false,
                "music_mylove",
                R.drawable.img_album_exp3,
                false,
            )
        )

        songDB.songDao().insert(
            Song(
                "Next Level",
                "aespa",
                0,
                221,
                false,
                "music_nextlevel",
                R.drawable.img_album_exp4,
                false,
            )
        )

        songDB.songDao().insert(
            Song(
                "낙하 (with IU)",
                "AKMU (악뮤)",
                0,
                212,
                false,
                "music_nakka",
                R.drawable.img_album_exp5,
                false,
            )
        )

        songDB.songDao().insert(
            Song(
                "LOVE DIVE",
                "IVE",
                0,
                176,
                false,
                "music_lovedive",
                R.drawable.img_album_exp6,
                false,
            )
        )

        val _songs = songDB.songDao().getSongs()
        Log.d("DB data", _songs.toString())
    }

    private fun inputDummyAlbums() {
        val songDB = SongDatabase.getInstance(this)!!
        val albums = songDB.albumDao().getAlbums()

        if (albums.isNotEmpty()) return

        songDB.albumDao().insert(
            Album(
                0,
                "IU 5th Album 'LILAC'", "아이유 (IU)", R.drawable.img_album_exp
            )
        )

        songDB.albumDao().insert(
            Album(
                1,
                "FATE NUMBER FOR", "WINNER", R.drawable.img_album_exp2
            )
        )

        songDB.albumDao().insert(
            Album(
                2,
                "낭만닥터 김사부 2 OST Part 1", "백현 (BAEKHYUN)", R.drawable.img_album_exp3
            )
        )

        songDB.albumDao().insert(
            Album(
                3,
                "Next Level", "aespa", R.drawable.img_album_exp4
            )
        )

        songDB.albumDao().insert(
            Album(
                4,
                "NEXT EPISODE", "AKMU (악뮤)", R.drawable.img_album_exp5
            )
        )

        songDB.albumDao().insert(
            Album(
                5,
                "LOVE DIVE", "IVE", R.drawable.img_album_exp6
            )
        )

    }

}