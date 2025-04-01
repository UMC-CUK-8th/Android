package com.example.jack_week3

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.jack_week3.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private var isPlaying: Boolean = false // 음악이 재생 중인지 여부
    private var mediaPlayer: MediaPlayer? = null // MediaPlayer 객체
    private lateinit var bannerAdapter: BannerAdapter
    private val bannerList = listOf(
        BannerItem(R.drawable.banner1),
        BannerItem(R.drawable.banner2),
        BannerItem(R.drawable.banner3)
    )

    private val handler = Handler(Looper.getMainLooper())
    private var currentPage = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // Set up bottom navigation view
        setBottomNavigationView()
        // Set up music bar
        setupMusicBar()

        // Default fragment selection
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.my_nav_host) as NavHostFragment
        val navController = navHostFragment.navController

        if (savedInstanceState == null) {
            binding.myBottomNav.selectedItemId = R.id.homeFragment
        }
    }

    private fun setBottomNavigationView() {
        binding.myBottomNav.setOnItemSelectedListener { item ->
            val navController = supportFragmentManager.findFragmentById(R.id.my_nav_host)?.findNavController()
            navController?.let {
                when (item.itemId) {
                    R.id.homeFragment -> it.navigate(R.id.homeFragment)
                    R.id.seeFragment -> it.navigate(R.id.seeFragment)
                    R.id.searchFragment -> it.navigate(R.id.searchFragment)
                    R.id.libraryFragment -> it.navigate(R.id.libraryFragment)
                }
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
        // Activity가 종료될 때 MediaPlayer 객체 해제
        mediaPlayer?.release()
        mediaPlayer = null
    }
}
