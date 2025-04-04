package com.flow

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.flow.databinding.ActivitySongBinding

class SongActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySongBinding
    private var isRepeat = false
    private var isShuffle = false
    private var isPlaying = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // MainActivity에서 넘어온 노래 정보 설정
        val title = intent.getStringExtra("title")
        val singer = intent.getStringExtra("singer")
        binding.songTitleTv.text = title
        binding.songSingerTv.text = singer

        // 반복재생 버튼
        binding.songRepeatBtn.setOnClickListener {
            isRepeat = !isRepeat
            if (isRepeat) {
                binding.songRepeatBtn.setImageResource(R.drawable.ic_repeat_active)
            } else {
                binding.songRepeatBtn.setImageResource(R.drawable.ic_repeat_inactive)
            }
        }

        // 이전곡 버튼
        binding.songPrevBtn.setOnClickListener {
            Toast.makeText(this, "이전 곡", Toast.LENGTH_SHORT).show()
        }

        // 재생/일시정지 버튼
        binding.songPlayBtn.setOnClickListener {
            isPlaying = !isPlaying
            if (isPlaying) {
                binding.songPlayBtn.setImageResource(R.drawable.ic_pause)
            } else {
                binding.songPlayBtn.setImageResource(R.drawable.ic_play)
            }
        }

        // 다음곡 버튼
        binding.songNextBtn.setOnClickListener {
            Toast.makeText(this, "다음 곡", Toast.LENGTH_SHORT).show()
        }

        // 셔플 버튼
        binding.songShuffleBtn.setOnClickListener {
            isShuffle = !isShuffle
            if (isShuffle) {
                binding.songShuffleBtn.setImageResource(R.drawable.ic_shuffle_active)
            } else {
                binding.songShuffleBtn.setImageResource(R.drawable.ic_shuffle_inactive)
            }
        }

        // 뒤로가기 버튼
        binding.songBackBtn.setOnClickListener {
            val intent = Intent()
            intent.putExtra("albumTitle", "SongActivity에서 보낸 앨범 제목")
            setResult(RESULT_OK, intent)
            finish()
        }
    }
}
