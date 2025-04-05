package com.example.jack_week3

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.jack_week3.databinding.ActivitySongBinding
class SongActivity : AppCompatActivity() {

    lateinit var binding : ActivitySongBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        binding.songDown.setOnClickListener{
            val resultIntent = Intent()
            resultIntent.putExtra("album_title", binding.songMusicTitle.text.toString())
            setResult(RESULT_OK, resultIntent)
            finish()
        }
        binding.songPlay.setOnClickListener {
            setPlayStatus(false)
        }
        binding.songPause.setOnClickListener {
            setPlayStatus(true)
        }
    }
    fun setPlayStatus(isPlaying :Boolean){
        if(isPlaying){
            binding.songPlay.visibility = View.VISIBLE
            binding.songPause.visibility = View.GONE
        }
        else {
            binding.songPlay.visibility = View.GONE
            binding.songPause.visibility = View.VISIBLE
        }
    }
}