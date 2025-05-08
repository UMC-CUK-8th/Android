package com.example.jack_week4

import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.jack_week4.databinding.ActivityMainBinding
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    private var song: Song = Song()
    private var gson: Gson = Gson()

    private var mediaPlayer: MediaPlayer? = null // MediaPlayer 객체 추가

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_FLO)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initBottomNavigation()

        // MiniPlayer 클릭 시 SongActivity로 이동
        binding.mainPlayerCl.setOnClickListener {
            val intent = Intent(this, SongActivity::class.java)
            intent.putExtra("title", song.title)
            intent.putExtra("singer", song.singer)
            intent.putExtra("song", song.second)
            intent.putExtra("playTime", song.playTime)
            intent.putExtra("isPlaying", song.isPlaying)
            intent.putExtra("music", song.music)
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        val sharedPreferences = getSharedPreferences("song", MODE_PRIVATE)
        val songJson = sharedPreferences.getString("songData", null)

        // SharedPreferences에서 저장된 곡 데이터 읽어오기
        song = if (songJson == null) {
            Song("라일락", "아이유(IU)", 0, 60, false, "music_lilac")
        } else {
            gson.fromJson(songJson, Song::class.java)
        }

        // MiniPlayer UI 설정
        setMiniPlayer(song)
    }

    override fun onStop() {
        super.onStop()
        // 앱 종료 시 MediaPlayer 리소스를 해제
        mediaPlayer?.release()
        mediaPlayer = null
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
                        .replace(R.id.main_frm, SeeFragment())
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
                        .replace(R.id.main_frm, LibraryFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
            }
            false
        }
    }

    private fun setMiniPlayer(song: Song) {
        // MiniPlayer UI 업데이트
        binding.mainMiniplayerTitleTv.text = song.title
        binding.mainMiniplayerSingerTv.text = song.singer
        binding.mainMiniplayerProgressSb.progress = (song.second * 100000) / song.playTime

        // MiniPlayer ▶ 버튼 클릭 시 재생/일시 정지 토글
        binding.mainMiniplayerBtn.setOnClickListener {
            if (song.isPlaying) {
                pauseSong()
            } else {
                playSong(song)
            }
        }
    }

    private fun playSong(song: Song) {
        // MediaPlayer로 음악 재생
        mediaPlayer = MediaPlayer.create(this, Uri.parse(song.music))
        mediaPlayer?.start()

        // 재생 상태 변경
        song.isPlaying = true
        binding.mainMiniplayerBtn.setImageResource(R.drawable.ic_pause)  // 재생 버튼 -> 일시 정지 버튼으로 변경
    }

    private fun pauseSong() {
        mediaPlayer?.pause()
        song.isPlaying = false
        binding.mainMiniplayerBtn.setImageResource(R.drawable.ic_play)  // 일시 정지 버튼 -> 재생 버튼으로 변경
    }
}
