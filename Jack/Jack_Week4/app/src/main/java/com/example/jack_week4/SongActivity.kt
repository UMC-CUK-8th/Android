package com.example.jack_week4

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.jack_week4.databinding.ActivitySongBinding
import com.google.gson.Gson

class SongActivity : AppCompatActivity() {

    lateinit var binding: ActivitySongBinding
    lateinit var song: Song
    private var timer: Timer? = null
    private var mediaPlayer: MediaPlayer? = null
    private var gson: Gson = Gson()

    private var isPlaying: Boolean = false
    private var currentPosition: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        // 저장된 song 데이터 불러오기
        val sharedPreferences = getSharedPreferences("song", MODE_PRIVATE)
        val songJson = sharedPreferences.getString("songData", null)

        song = if (songJson != null) {
            gson.fromJson(songJson, Song::class.java).apply {
                currentPosition = second * 1000
            }
        } else {
            // 처음 진입 시 intent에서 받기
            Song(
                intent.getStringExtra("title") ?: "제목 없음",
                intent.getStringExtra("singer") ?: "가수 없음",
                intent.getIntExtra("second", 0),
                intent.getIntExtra("playTime", 0),
                intent.getBooleanExtra("isPlaying", false),
                intent.getStringExtra("music") ?: "music_sample"
            ).apply {
                currentPosition = second * 1000
            }
        }

        setPlayer(song)

        binding.songDown.setOnClickListener {
            val resultIntent = Intent()
            resultIntent.putExtra("album_title", binding.songMusicTitle.text.toString())
            setResult(RESULT_OK, resultIntent)
            finish()
        }

        binding.songPlay.setOnClickListener {
            setPlayStatus(true)
            if (timer == null || timer?.isAlive == false) {
                startTimer()
            }
        }

        binding.songPause.setOnClickListener {
            setPlayStatus(false)
        }

        binding.songProgress.setOnSeekBarChangeListener(object : android.widget.SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: android.widget.SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    val newPosition = (progress * song.playTime * 10) / 10
                    mediaPlayer?.seekTo(newPosition)
                    currentPosition = newPosition
                    song.second = newPosition / 1000
                }
            }

            override fun onStartTrackingTouch(seekBar: android.widget.SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: android.widget.SeekBar?) {}
        })
    }

    override fun onPause() {
        super.onPause()
        setPlayStatus(false)

        // 현재 위치 저장
        currentPosition = mediaPlayer?.currentPosition ?: 0
        song.second = currentPosition / 1000
        song.isPlaying=false


        val sharedPreferences = getSharedPreferences("song", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val songJson = gson.toJson(song)
        editor.putString("songData", songJson)
        editor.apply()
    }

    override fun onDestroy() {
        super.onDestroy()
        timer?.interrupt()
        mediaPlayer?.release()
        mediaPlayer = null
    }

    private fun setPlayer(song: Song) {
        binding.songMusicTitle.text = song.title
        binding.songMusicSinger.text = song.singer
        binding.songStartTime.text = String.format("%02d:%02d", song.second / 60, song.second % 60)
        binding.songEndTime.text = String.format("%02d:%02d", song.playTime / 60, song.playTime % 60)
        binding.songProgress.progress = (song.second * 1000 / song.playTime)

        val musicRes = resources.getIdentifier(song.music, "raw", this.packageName)
        mediaPlayer = MediaPlayer.create(this, musicRes)
        mediaPlayer?.seekTo(currentPosition)

        setPlayStatus(song.isPlaying)
    }

    private fun setPlayStatus(isPlaying: Boolean) {
        song.isPlaying = isPlaying

        if (isPlaying) {
            mediaPlayer?.start()
            binding.songPlay.visibility = View.GONE
            binding.songPause.visibility = View.VISIBLE
            timer?.isPlaying = true
        } else {
            mediaPlayer?.pause()
            binding.songPlay.visibility = View.VISIBLE
            binding.songPause.visibility = View.GONE
            timer?.isPlaying = false
        }
    }

    private fun startTimer() {
        timer = Timer(song.playTime, currentPosition / 1000, song.isPlaying)
        timer?.start()
    }

    inner class Timer(private val playTime: Int, var second: Int, var isPlaying: Boolean = true) : Thread() {

        private var mills: Float = second * 1000f

        override fun run() {
            super.run()
            try {
                while (true) {
                    if (second >= playTime) break
                    if (isPlaying) {
                        sleep(50)
                        mills += 50
                        runOnUiThread {
                            binding.songProgress.progress = ((mills / playTime * 10).toInt())
                        }
                        if (mills % 1000 == 0f) {
                            runOnUiThread {
                                binding.songStartTime.text = String.format("%02d:%02d", second / 60, second % 60)
                            }
                            second++
                        }
                    }
                }
            } catch (e: InterruptedException) {
                Log.d("SongActivity", "Thread Terminates! ${e.message}")
            }
        }
    }
}
