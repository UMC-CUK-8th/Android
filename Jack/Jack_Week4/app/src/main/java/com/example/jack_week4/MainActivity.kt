package com.example.jack_week4

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.example.jack_week4.databinding.ActivityMainBinding
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {

    private val songActivityLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val title = result.data?.getStringExtra("album_title")
            title?.let {
                Toast.makeText(this, "제목: $it", Toast.LENGTH_SHORT).show()
            }
        }
    }

    lateinit var binding: ActivityMainBinding
    private var song: Song = Song()
    private var gson: Gson = Gson()

    private var isPlaying: Boolean = false // 음악이 재생 중인지 여부
    private var mediaPlayer: MediaPlayer? = null // MediaPlayer 객체

    private val handler = Handler(Looper.getMainLooper())
    private var currentPage = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.musicBar.setOnClickListener {
            val intent = Intent(this, SongActivity::class.java)
            // MainActivity의 음악 재생 상태와 현재 시간을 전달
            intent.putExtra("isPlaying", isPlaying)
            intent.putExtra("currentPosition", mediaPlayer?.currentPosition ?: 0)
            startActivity(intent)
        }

        // Set up bottom navigation view
        setBottomNavigationView()
        // Set up music bar
        setupMusicBar()

        // Default fragment selection
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.my_nav_host) as NavHostFragment
        val navController = navHostFragment.navController
        val song = Song(binding.songTitle.text.toString(), binding.artistName.text.toString(),
            0,
            120,
            false,
            "lilac")

        binding.musicBar.setOnClickListener {
            val intent = Intent(this, SongActivity::class.java)
            intent.putExtra("title", song.title)
            intent.putExtra("singer", song.singer)
            intent.putExtra("second", song.second)
            intent.putExtra("playTime", song.playTime)
            intent.putExtra("isPlaying", song.isPlaying)
            intent.putExtra("music", song.music)
            startActivity(intent)
        }

        if (savedInstanceState == null) {
            binding.myBottomNav.selectedItemId = R.id.homeFragment
        }
    }

    private fun setBottomNavigationView() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.my_nav_host) as NavHostFragment
        val navController = navHostFragment.navController

        binding.myBottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.homeFragment -> navController.navigate(R.id.homeFragment)
                R.id.seeFragment -> navController.navigate(R.id.seeFragment)
                R.id.searchFragment -> navController.navigate(R.id.searchFragment)
                R.id.libraryFragment -> navController.navigate(R.id.libraryFragment)
            }
            true
        }
    }

    private fun setupMusicBar() {
        val playPauseButton: ImageView = findViewById(R.id.play_pause_button)
        val prevButton: ImageView = findViewById(R.id.prev_button)
        val nextButton: ImageView = findViewById(R.id.next_button)
        val playlistButton: ImageView = findViewById(R.id.playlist_button)

        playPauseButton.setOnClickListener {
            if (isPlaying) {
                pauseMusic() // 음악 일시 정지
            } else {
                playMusic() // 음악 재생
            }
        }

        prevButton.setOnClickListener {
            // 이전 곡으로 이동하는 기능 추가
        }

        nextButton.setOnClickListener {
            // 다음 곡으로 이동하는 기능 추가
        }

        playlistButton.setOnClickListener {
            // 재생 목록 화면으로 이동하는 기능 추가
        }
    }

    private fun playMusic() {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(this, R.raw.lilac) // `raw` 폴더에 있는 음악 파일 경로
        }
        mediaPlayer?.start()
        isPlaying = true
        // 버튼 아이콘 변경
        findViewById<ImageView>(R.id.play_pause_button).setImageResource(R.drawable.ic_pause)
    }

    private fun pauseMusic() {
        mediaPlayer?.pause()
        isPlaying = false
        // 버튼 아이콘 변경
        findViewById<ImageView>(R.id.play_pause_button).setImageResource(R.drawable.ic_play)
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
        mediaPlayer = null
    }

    private fun setMiniPlayer(song: Song) {
        binding.songTitle.text = song.title
        binding.artistName.text = song.singer
        binding.songProgress.progress = (song.second * 100000) / song.playTime
    }

    override fun onStart() {
        super.onStart()
        val sharedPreferences = getSharedPreferences("song", MODE_PRIVATE)
        val songJson = sharedPreferences.getString("songData", null)

        song = if (songJson == null) {
            Song("라일락", "아이유(IU)", 0, 120, false, "lilac")
        } else {
            gson.fromJson(songJson, Song::class.java)
        }
        setMiniPlayer(song)
    }
}
