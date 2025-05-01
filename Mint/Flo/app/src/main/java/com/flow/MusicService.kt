package com.flow

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.flow.data.Song
import com.flow.data.SongDatabase

class MusicService : Service() {

    /* 바인더 -------------------------------------------------------------- */
    inner class MusicBinder : Binder() {
        fun getService(): MusicService = this@MusicService
    }
    private val binder = MusicBinder()

    /* 플레이어 & 타이머 ---------------------------------------------------- */
    private var mediaPlayer: MediaPlayer? = null
    private var timer: Timer? = null
    private var curSong: Song? = null

    override fun onCreate() {
        super.onCreate()
        SongDatabase.getInstance(this)            // 초기화만 해 둠
    }

    override fun onBind(intent: Intent?): IBinder = binder

    override fun onDestroy() {
        super.onDestroy()
        timer?.interrupt()
        mediaPlayer?.release()
    }

    /* ───────────── 재생 제어 public API ───────────── */
    fun setSong(song: Song, fromMs: Int = 0, autoPlay: Boolean = false) {
        if (curSong?.id == song.id) return        // 같은 곡이면 건너뜀

        timer?.interrupt()
        mediaPlayer?.release()

        curSong = song
        val res = resources.getIdentifier(song.music, "raw", packageName)
        mediaPlayer = MediaPlayer.create(this, res).apply { seekTo(fromMs) }
        if (autoPlay) mediaPlayer?.start()

        startTimer(fromMs, autoPlay)
    }

    fun play()  { mediaPlayer?.start();  timer?.isPlaying = true }
    fun pause() { mediaPlayer?.pause();  timer?.isPlaying = false }
    fun toggle() = if (isPlaying) pause() else play()
    fun seekTo(ms: Int) { mediaPlayer?.seekTo(ms); timer?.seekTo(ms) }

    val isPlaying      get() = mediaPlayer?.isPlaying == true
    val currentSong    get() = curSong
    val durationMs     get() = curSong?.playTime?.times(1000) ?: 1
    val currentPosition: Int get() = mediaPlayer?.currentPosition ?: 0

    /* ───────────── Timer 스레드 ───────────── */
    private fun startTimer(startMs: Int, playing: Boolean) {
        timer?.interrupt()
        timer = Timer(durationMs, startMs, playing).also { it.start() }
    }

    private inner class Timer(
        private val totalMs: Int,
        startMs: Int,
        @Volatile var isPlaying: Boolean
    ) : Thread() {

        @Volatile private var curMs = startMs
        fun seekTo(ms: Int) { curMs = ms }

        override fun run() {
            try {
                while (curMs < totalMs) {
                    if (isPlaying) {
                        sleep(200)
                        curMs += 200
                        val i = Intent("FLOW_PROGRESS").apply {
                            putExtra("posMs", curMs)
                            putExtra("duration", totalMs)
                        }
                        LocalBroadcastManager.getInstance(applicationContext).sendBroadcast(i)
                    } else sleep(200)
                }
            } catch (_: InterruptedException) { Log.d("MusicService","Timer end") }
        }
    }
}
