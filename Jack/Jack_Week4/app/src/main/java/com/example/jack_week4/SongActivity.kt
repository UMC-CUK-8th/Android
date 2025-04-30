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

    // 추가된 변수
    private var isPlaying: Boolean = false
    private var currentPosition: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        isPlaying = intent.getBooleanExtra("isPlaying", false)
        currentPosition = intent.getIntExtra("currentPosition", 0)

        initSong()
        setPlayer(song)

        binding.songDown.setOnClickListener {
            val resultIntent = Intent()
            resultIntent.putExtra("album_title", binding.songMusicTitle.text.toString())
            setResult(RESULT_OK, resultIntent)
            finish()
        }

        binding.songPlay.setOnClickListener {
            if (mediaPlayer == null) {
                val music = resources.getIdentifier(song.music, "raw", this.packageName)
                mediaPlayer = MediaPlayer.create(this, music)
                mediaPlayer?.seekTo(currentPosition)  // 현재 위치로 재생 시작
            }

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
                    val newPosition = (progress * song.playTime * 10)
                    mediaPlayer?.seekTo((newPosition / 10).toInt())
                    currentPosition = mediaPlayer?.currentPosition ?: 0 // 새로 바뀐 시간 저장
                }
            }

            override fun onStartTrackingTouch(seekBar: android.widget.SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: android.widget.SeekBar?) {}
        })
    }

    override fun onPause(){
        super.onPause()
        setPlayStatus(false)
        song.second = ((binding.songProgress.progress * song.playTime)/10)/1000
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

    private fun initSong() {
        if (intent.hasExtra("title") && intent.hasExtra("singer")) {
            song = Song(
                intent.getStringExtra("title")!!,
                intent.getStringExtra("singer")!!,
                intent.getIntExtra("second", 0),
                intent.getIntExtra("playTime", 0),
                intent.getBooleanExtra("isPlaying", false),
                intent.getStringExtra("music")!!
            )
        }
    }

    private fun setPlayer(song: Song){
        binding.songMusicTitle.text = song.title
        binding.songMusicSinger.text = song.singer
        binding.songStartTime.text = String.format("%02d:%02d",song.second / 60, song.second % 60)
        binding.songEndTime.text = String.format("%02d:%02d",song.playTime / 60, song.playTime % 60)
        binding.songProgress.progress = (song.second * 1000 / song.playTime)

        val music = resources.getIdentifier(song.music, "raw", this.packageName)
        mediaPlayer = MediaPlayer.create(this, music)

        setPlayStatus(song.isPlaying)
    }

    private fun setPlayStatus(isPlaying: Boolean) {
        song.isPlaying = isPlaying


        if (isPlaying) {
            if (mediaPlayer?.isPlaying == false) {
                mediaPlayer?.start()
            }
            binding.songPlay.visibility = View.GONE
            binding.songPause.visibility = View.VISIBLE
            timer?.isPlaying = true
        } else {
            if (mediaPlayer?.isPlaying == true) {
                mediaPlayer?.pause()
            }
            binding.songPlay.visibility = View.VISIBLE
            binding.songPause.visibility = View.GONE
            timer?.isPlaying = false
        }
    }

    private fun startTimer() {
        timer = Timer(song.playTime,song.isPlaying)
        timer?.start()
    }

    inner class Timer(private val playTime: Int, var isPlaying: Boolean = true) : Thread() {

        private var second: Int = 0
        private var mills: Float = 0f

        override fun run() {
            super.run()
            try {
                while (true) {

                    if (second >= playTime) {
                        break
                    }

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
