package com.flow

import android.content.BroadcastReceiver
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
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
import com.flow.ui.LockerFragment
import com.flow.ui.LookFragment
import com.flow.ui.SearchFragment
import kotlin.jvm.java


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var songDB: SongDatabase

    /* ── MusicService 바인딩 ─────────────────────────────────────────────── */
    private var musicService: MusicService? = null
    private var bound = false
    private val conn = object : ServiceConnection {
        override fun onServiceConnected(n: ComponentName?, b: IBinder?) {
            musicService = (b as MusicService.MusicBinder).getService()
            bound = true
            refreshMini()           // 서비스 연결 직후 UI 세팅
        }
        override fun onServiceDisconnected(p0: ComponentName?) { bound = false }
    }

    /* ── 진행률 수신 ─────────────────────────────────────────────────────── */
    private val progressReceiver = object : BroadcastReceiver() {
        override fun onReceive(c: Context?, i: Intent?) {
            val cur = i?.getIntExtra("posMs", 0) ?: 0
            val dur = i?.getIntExtra("duration", 1) ?: 1
            binding.mainMiniplayerProgressSb.progress = cur * 1000 / dur
        }
    }

    /* ───────────────────────────────────────────────────────────────────── */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_FLO)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /* ① DB 더미 삽입 (앱 최초 실행 대비) */
        songDB = SongDatabase.getInstance(this)!!
        inputDummySongs(); inputDummyAlbums()

        /* ② MusicService 시작·바인드 */
        Intent(this, MusicService::class.java).also { startService(it); bindService(it, conn, 0) }

        /* ③ 버튼 리스너 */
        binding.mainMiniplayerBtn.setOnClickListener {
            /* 곡이 아직 없다면 1번 곡부터 재생 */
            if (musicService?.currentSong == null) {
                val first = songDB.songDao().getSong(1)          // id=1 이 첫 곡
                musicService?.setSong(first, autoPlay = true)
            } else {
                musicService?.play()
            }
            updatePlayPause()
        }
        binding.mainPauseBtn.setOnClickListener {
            musicService?.pause(); updatePlayPause()
        }
        binding.mainPlayerCl.setOnClickListener {
            val id = musicService?.currentSong?.id ?: return@setOnClickListener
            startActivity(Intent(this, SongActivity::class.java).putExtra("songId", id))
        }

        /* ④ 네비게이션 · 진행률 리시버 */
        initBottomNavigation()
        LocalBroadcastManager.getInstance(this)
            .registerReceiver(progressReceiver, IntentFilter("FLOW_PROGRESS"))
    }

    override fun onDestroy() {
        super.onDestroy()
        if (bound) unbindService(conn)
        LocalBroadcastManager.getInstance(this).unregisterReceiver(progressReceiver)
    }

    /* ── 미니플레이어 UI 갱신 ───────────────────────────────────────────── */
    private fun refreshMini() {
        val s = musicService?.currentSong ?: return
        binding.mainMiniplayerTitleTv.text  = s.title
        binding.mainMiniplayerSingerTv.text = s.singer
        updatePlayPause()
    }
    private fun updatePlayPause() = with(binding) {
        val playing = musicService?.isPlaying == true
        mainMiniplayerBtn.visibility = if (playing) View.GONE else View.VISIBLE
        mainPauseBtn.visibility      = if (playing) View.VISIBLE else View.GONE
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
                R.id.lockerFragment -> supportFragmentManager.beginTransaction()
                    .replace(R.id.main_frm, LockerFragment()).commitAllowingStateLoss()
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
