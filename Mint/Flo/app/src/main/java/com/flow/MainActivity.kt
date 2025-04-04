package com.flow

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.flow.databinding.ActivityMainBinding
import com.flow.ui.AlbumFragment
import com.flow.ui.HomeFragment
import com.flow.ui.LockerFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    // SongActivity 결과 수신용 launcher
    private val songActivityLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val albumTitle = result.data?.getStringExtra("albumTitle")
            Toast.makeText(this, "돌아옴! 앨범제목: $albumTitle", Toast.LENGTH_SHORT).show()
        }
    }

    private var isMiniPlaying = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 초기 화면: HomeFragment
        supportFragmentManager.commit {
            replace(R.id.main_container, HomeFragment())
        }

        // BottomNavigation 리스너
        binding.mainBottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.menu_home -> {
                    supportFragmentManager.commit {
                        replace(R.id.main_container, HomeFragment())
                    }
                    true
                }
                R.id.menu_search -> {
                    supportFragmentManager.commit {
                        replace(R.id.main_container, AlbumFragment())
                    }
                    true
                }
                R.id.menu_explore -> {
                    supportFragmentManager.commit {
                        replace(R.id.main_container, LockerFragment())
                    }
                    true
                }
                R.id.menu_library -> {
                    supportFragmentManager.commit {
                        replace(R.id.main_container, LockerFragment())
                    }
                    true
                }
                else -> false
            }
        }

        // 미니플레이어 전체 클릭 시 SongActivity로 이동
        binding.miniPlayer.setOnClickListener {
            val intent = Intent(this, SongActivity::class.java)
            intent.putExtra("title", binding.miniPlayerTitle.text.toString())
            intent.putExtra("singer", binding.miniPlayerSinger.text.toString())
            songActivityLauncher.launch(intent)
        }

        // 개별 미니플레이어 버튼 리스너
        binding.miniPlayerPrevBtn.setOnClickListener {
            Toast.makeText(this, "이전 곡", Toast.LENGTH_SHORT).show()
        }

        binding.miniPlayerNextBtn.setOnClickListener {
            Toast.makeText(this, "다음 곡", Toast.LENGTH_SHORT).show()
        }

        binding.miniPlayerPlayBtn.setOnClickListener {
            isMiniPlaying = !isMiniPlaying
            if (isMiniPlaying) {
                binding.miniPlayerPlayBtn.setImageResource(R.drawable.ic_pause)
            } else {
                binding.miniPlayerPlayBtn.setImageResource(R.drawable.ic_play)
            }
        }
    }
}
