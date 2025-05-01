package com.flow

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.flow.data.Song
import com.flow.data.SongDatabase
import com.flow.databinding.ActivitySongBinding

class SongActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySongBinding
    private lateinit var timer: Timer

    private var mediaPlayer: MediaPlayer? = null
    private val songs = arrayListOf<Song>()
    private lateinit var songDB: SongDatabase
    private var nowPos = 0           // 재생 중인 곡 인덱스

    // ──────────────────────────────────────────────────────────────────────────
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initPlayList()
        initSong()
        initClickListener()
    }

    override fun onPause() {
        super.onPause()

        // 현재 위치 저장
        songs[nowPos].second =
            ((binding.songProgressSb.progress * songs[nowPos].playTime) / 100000) / 1000
        songs[nowPos].isPlaying = false
        setPlayerStatus(false)

        getSharedPreferences("song", MODE_PRIVATE).edit()
            .putInt("songId", songs[nowPos].id)
            .apply()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (::timer.isInitialized) timer.interrupt()
        mediaPlayer?.release()
        mediaPlayer = null
    }

    // ─────────────────── 초기화 ────────────────────────────────────────────────
    private fun initPlayList() {
        songDB = SongDatabase.getInstance(this)!!
        songs.addAll(songDB.songDao().getSongs())
    }

    private fun initSong() {
        val songId = getSharedPreferences("song", MODE_PRIVATE).getInt("songId", 0)
        nowPos = songs.indexOfFirst { it.id == songId }.takeIf { it != -1 } ?: 0

        Log.d("SongActivity", "current song id=${songs[nowPos].id}")
        startTimer(0)
        setPlayer(songs[nowPos])
    }

    private fun initClickListener() = with(binding) {
        songDownIb.setOnClickListener { finish() }
        songMiniplayerIv.setOnClickListener { setPlayerStatus(true); restartTimer() }
        songPauseIv.setOnClickListener { setPlayerStatus(false) }
        songNextIv.setOnClickListener { moveSong(+1) }
        songPreviousIv.setOnClickListener { moveSong(-1) }
        songLikeIv.setOnClickListener { toggleLike() }
    }

    // ─────────────────── 좋아요 ────────────────────────────────────────────────
    private fun toggleLike() {
        val cur = songs[nowPos]
        cur.isLike = !cur.isLike
        songDB.songDao().updateIsLikeById(cur.isLike, cur.id)
        binding.songLikeIv.setImageResource(
            if (cur.isLike) R.drawable.ic_my_like_on else R.drawable.ic_my_like_off
        )
    }

    // ─────────────────── 곡 이동 ───────────────────────────────────────────────
    private fun moveSong(direct: Int) {
        if (nowPos + direct !in songs.indices) {
            Toast.makeText(this, "더 이상 곡이 없습니다", Toast.LENGTH_SHORT).show(); return
        }
        nowPos += direct

        restartTimer()
        mediaPlayer?.release(); mediaPlayer = null
        setPlayer(songs[nowPos])
    }

    // ─────────────────── Player 세팅 ─────────────────────────────────────────
    private fun setPlayer(song: Song) = with(binding) {
        songMusicTitleTv.text   = song.title
        songSingerNameTv.text   = song.singer
        songStartTimeTv.text    = String.format("%02d:%02d", song.second / 60, song.second % 60)
        songEndTimeTv.text      = String.format("%02d:%02d", song.playTime / 60, song.playTime % 60)
        songAlbumIv.setImageResource(song.coverImg!!)
        songProgressSb.progress = (song.second * 100000) / song.playTime

        val resId = resources.getIdentifier(song.music, "raw", packageName)
        mediaPlayer = MediaPlayer.create(this@SongActivity, resId)

        songLikeIv.setImageResource(
            if (song.isLike) R.drawable.ic_my_like_on else R.drawable.ic_my_like_off
        )
        setPlayerStatus(song.isPlaying)
    }

    private fun setPlayerStatus(isPlaying: Boolean) = with(binding) {
        songs[nowPos].isPlaying = isPlaying
        timer.isPlaying         = isPlaying

        getSharedPreferences("song", MODE_PRIVATE).edit()
            .putBoolean("isPlaying", isPlaying).apply()

        if (isPlaying) {
            songMiniplayerIv.visibility = View.GONE
            songPauseIv.visibility      = View.VISIBLE
            mediaPlayer?.start()
        } else {
            songMiniplayerIv.visibility = View.VISIBLE
            songPauseIv.visibility      = View.GONE
            mediaPlayer?.pause()
        }
    }

    // ─────────────────── Timer 스레드 ─────────────────────────────────────────
    private fun startTimer(startMs: Int) {
        timer = Timer(songs[nowPos].playTime, startMs, songs[nowPos].isPlaying)
        timer.start()
    }

    private fun restartTimer() {
        if (::timer.isInitialized && timer.isAlive) timer.interrupt()
        startTimer(mediaPlayer?.currentPosition ?: 0)
    }

    inner class Timer(
        private val durationSec: Int,
        startMs: Int = 0,
        @Volatile var isPlaying: Boolean = true
    ) : Thread() {

        private var mills  = startMs
        private var second = startMs / 1000

        override fun run() {
            try {
                while (second < durationSec) {
                    if (isPlaying) {
                        sleep(200)
                        mills  += 200
                        second  = mills / 1000

                        runOnUiThread {
                            binding.songProgressSb.progress =
                                (mills * 100000 / (durationSec * 1000f)).toInt()
                            binding.songStartTimeTv.text =
                                String.format("%02d:%02d", second / 60, second % 60)
                        }

                        // 메인 화면으로 진행률 브로드캐스트
                        val i = Intent("FLOW_PROGRESS").apply {
                            putExtra("posMs", mills)
                            putExtra("duration", durationSec * 1000)
                        }
                        LocalBroadcastManager.getInstance(applicationContext).sendBroadcast(i)
                    }
                }
            } catch (e: InterruptedException) {
                Log.d("SongActivity", "Timer stop: ${e.message}")
            }
        }
    }
}
