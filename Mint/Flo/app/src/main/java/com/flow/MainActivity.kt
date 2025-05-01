package com.flow

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.flow.data.Album
import com.flow.data.Song
import com.flow.data.SongDatabase
import com.flow.databinding.ActivityMainBinding
import com.flow.ui.HomeFragment
import com.flow.ui.LookFragment
import com.flow.ui.SearchFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var song: Song = Song()

    // ──────────────────────────────────────────────────────────────────────────
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_FLO)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        inputDummySongs(); inputDummyAlbums(); initBottomNavigation()

        val isPlaying = getSharedPreferences("song", MODE_PRIVATE)
            .getBoolean("isPlaying", false)
        song.isPlaying = isPlaying
        setPlayerStatus(isPlaying)

        binding.mainMiniplayerBtn.setOnClickListener { setPlayerStatus(true) }
        binding.mainPauseBtn.setOnClickListener    { setPlayerStatus(false) }

        binding.mainPlayerCl.setOnClickListener {
            getSharedPreferences("song", MODE_PRIVATE).edit()
                .putInt("songId", song.id).apply()
            startActivity(Intent(this, SongActivity::class.java))
        }
    }

    // ─────────────────── 진행률 수신 ──────────────────────────────────────────
    private val progressReceiver = object : BroadcastReceiver() {
        override fun onReceive(c: Context?, i: Intent?) {
            val cur = i?.getIntExtra("posMs", 0) ?: 0
            val dur = i?.getIntExtra("duration", 1) ?: 1
            binding.mainMiniplayerProgressSb.progress = (cur * 100000 / dur)
        }
    }

    override fun onStart() {
        super.onStart()

        // 미니플레이어 정보 로드
        val songId = getSharedPreferences("song", MODE_PRIVATE).getInt("songId", 0)
        val db = SongDatabase.getInstance(this)!!
        song = if (songId == 0) db.songDao().getSong(1) else db.songDao().getSong(songId)
        setMiniPlayer(song)

        LocalBroadcastManager.getInstance(this)
            .registerReceiver(progressReceiver, IntentFilter("FLOW_PROGRESS"))
    }

    override fun onStop() {
        super.onStop()
        LocalBroadcastManager.getInstance(this).unregisterReceiver(progressReceiver)
    }

    // ─────────────────── UI ──────────────────────────────────────────────────
    private fun setPlayerStatus(isPlaying: Boolean) {
        song.isPlaying = isPlaying
        binding.mainMiniplayerBtn.visibility = if (isPlaying) View.GONE else View.VISIBLE
        binding.mainPauseBtn.visibility      = if (isPlaying) View.VISIBLE else View.GONE

        getSharedPreferences("song", MODE_PRIVATE).edit()
            .putBoolean("isPlaying", isPlaying).apply()
    }

    private fun setMiniPlayer(song: Song) = with(binding) {
        mainMiniplayerTitleTv.text = song.title
        mainMiniplayerSingerTv.text = song.singer
        mainMiniplayerProgressSb.progress = (song.second * 100000 / song.playTime)
    }

    // ───────────────────── 네비게이션/더미 데이터 (기존 코드 그대로) ───────────
    private fun initBottomNavigation() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_frm, HomeFragment())
            .commitAllowingStateLoss()

        binding.mainBnv.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.homeFragment -> supportFragmentManager.beginTransaction()
                    .replace(R.id.main_frm, HomeFragment()).commitAllowingStateLoss()
                R.id.lookFragment -> supportFragmentManager.beginTransaction()
                    .replace(R.id.main_frm, LookFragment()).commitAllowingStateLoss()
                R.id.searchFragment -> supportFragmentManager.beginTransaction()
                    .replace(R.id.main_frm, SearchFragment()).commitAllowingStateLoss()
                else -> { /* 생략 */ }
            }
            true
        }
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
