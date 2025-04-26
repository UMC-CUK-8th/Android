package com.example.jack_week4

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.jack_week4.databinding.ActivitySongBinding

class SongActivity : AppCompatActivity() {

    lateinit var binding: ActivitySongBinding
    lateinit var song: Song
    lateinit var timer: Timer
    private var mediaPlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        initSong()
        setPlayer(song)

        binding.songDown.setOnClickListener {
            val resultIntent = Intent()
            resultIntent.putExtra("album_title", binding.songMusicTitle.text.toString())
            setResult(RESULT_OK, resultIntent)
            finish()
        }

        binding.songPlay.setOnClickListener {
            setPlayStatus(true)
        }

        binding.songPause.setOnClickListener {
            setPlayStatus(false)
        }

        binding.songProgress.setOnSeekBarChangeListener(object :
            android.widget.SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(
                seekBar: android.widget.SeekBar?,
                progress: Int,
                fromUser: Boolean
            ) {
                if (fromUser) {
                    val newPosition = (progress * song.playTime * 10) // 1초 = 1000ms, 0.1초 = 100ms
                    mediaPlayer?.seekTo((newPosition / 10).toInt()) // ms로 변환

                }
            }

            override fun onStartTrackingTouch(seekBar: android.widget.SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: android.widget.SeekBar?) {}
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        timer.interrupt()
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

    private fun setPlayer(song: Song) {
        binding.songMusicTitle.text = song.title
        binding.songMusicSinger.text = song.singer
        binding.songEndTime.text =
            String.format("%02d:%02d", song.playTime / 60, song.playTime % 60)

        val music = resources.getIdentifier(song.music, "raw", this.packageName)
        mediaPlayer = MediaPlayer.create(this, music)
        startTimer()
        setPlayStatus(song.isPlaying)

    }

    private fun setPlayStatus(isPlaying: Boolean) {
        song.isPlaying = isPlaying
        timer.isPlaying = isPlaying
        if (isPlaying) {
            mediaPlayer?.start()
            binding.songPlay.visibility = View.GONE
            binding.songPause.visibility = View.VISIBLE
        } else {
            mediaPlayer?.pause()
            binding.songPlay.visibility = View.VISIBLE
            binding.songPause.visibility = View.GONE
        }
    }

    private fun startTimer() {
        timer = Timer(song.playTime, song.isPlaying)
        timer.start()
    }

    inner class Timer(private val playTime: Int,var isPlaying: Boolean = true):Thread(){

        private var second : Int = 0
        private var mills: Float = 0f

        override fun run() {
            super.run()
            try {
                while (true){

                    if (second >= playTime){
                        break
                    }

                    if (isPlaying){
                        sleep(50)
                        mills += 50

                        runOnUiThread {
                            binding.songProgress.progress = ((mills / playTime*10).toInt())
                        }

                        if (mills % 1000 == 0f){
                            runOnUiThread {
                                binding.songStartTime.text = String.format("%02d:%02d",second/60, second%60)
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