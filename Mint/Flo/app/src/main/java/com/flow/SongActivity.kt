package com.flow

import android.content.*
import android.os.Bundle
import android.os.IBinder
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.flow.data.Song
import com.flow.data.SongDatabase
import com.flow.databinding.ActivitySongBinding
import com.google.gson.Gson

class SongActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySongBinding
    private val songs = arrayListOf<Song>()
    private var nowPos = 0

    private var musicService: MusicService? = null
    private var bound = false
    private val conn = object : ServiceConnection {
        override fun onServiceConnected(c: ComponentName?, b: IBinder?) {
            musicService = (b as MusicService.MusicBinder).getService()
            bound = true
            initPlayerUI()
        }

        override fun onServiceDisconnected(p0: ComponentName?) {
            bound = false
        }
    }

    private val progressReceiver = object : BroadcastReceiver() {
        override fun onReceive(c: Context?, i: Intent?) {
            val cur = i?.getIntExtra("posMs", 0) ?: 0
            val dur = i?.getIntExtra("duration", 1) ?: 1
            binding.songProgressSb.progress = cur * 1000 / dur
            binding.songStartTimeTv.text =
                String.format("%02d:%02d", cur / 1000 / 60, (cur / 1000) % 60)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val gson = Gson()
        val songListJson = intent.getStringExtra("songList")
        val songId = intent.getIntExtra("songId", -1)

        if (!songListJson.isNullOrEmpty()) {
            val songArray = gson.fromJson(songListJson, Array<Song>::class.java)
            songs.addAll(songArray)
        } else {
            val dbSongs = SongDatabase.getInstance(this)?.songDao()?.getSongs()
            if (dbSongs.isNullOrEmpty()) {
                Toast.makeText(this, "곡 정보가 없습니다.", Toast.LENGTH_SHORT).show()
                finish()
                return
            }
            songs.addAll(dbSongs)
        }

        nowPos = songs.indexOfFirst { it.id == songId }.let { if (it == -1) 0 else it }

        Intent(this, MusicService::class.java).also {
            startService(it)
            bindService(it, conn, 0)
        }

        initClick()

        LocalBroadcastManager.getInstance(this)
            .registerReceiver(progressReceiver, IntentFilter("FLOW_PROGRESS"))
    }

    override fun onDestroy() {
        super.onDestroy()
        if (bound) unbindService(conn)
        LocalBroadcastManager.getInstance(this).unregisterReceiver(progressReceiver)
    }

    private fun initPlayerUI() = setPlayer(songs[nowPos])

    private fun setPlayer(song: Song) = with(binding) {
        songMusicTitleTv.text = song.title
        songSingerNameTv.text = song.singer
        songAlbumIv.setImageResource(song.coverImg!!)
        songEndTimeTv.text = String.format("%02d:%02d", song.playTime / 60, song.playTime % 60)
        songLikeIv.setImageResource(
            if (song.isLike) R.drawable.ic_my_like_on else R.drawable.ic_my_like_off
        )

        if (musicService?.currentSong?.id == song.id) {
            val cur = musicService?.currentPosition ?: 0
            songProgressSb.progress = cur * 1000 / musicService!!.durationMs
            songStartTimeTv.text = String.format("%02d:%02d", cur / 1000 / 60, (cur / 1000) % 60)
        } else {
            musicService?.setSong(song, autoPlay = true)
        }
        updatePlayPause()
    }

    private fun updatePlayPause() = with(binding) {
        val playing = musicService?.isPlaying == true
        songMiniplayerIv.visibility = if (playing) View.GONE else View.VISIBLE
        songPauseIv.visibility = if (playing) View.VISIBLE else View.GONE
    }

    private fun initClick() = with(binding) {
        songMiniplayerIv.setOnClickListener { musicService?.play(); updatePlayPause() }
        songPauseIv.setOnClickListener { musicService?.pause(); updatePlayPause() }
        songNextIv.setOnClickListener { moveSong(+1) }
        songPreviousIv.setOnClickListener { moveSong(-1) }
        songDownIb.setOnClickListener { finish() }

        songLikeIv.setOnClickListener {
            val song = songs[nowPos]
            song.isLike = !song.isLike

            songLikeIv.setImageResource(
                if (song.isLike) R.drawable.ic_my_like_on else R.drawable.ic_my_like_off
            )

            SongDatabase.getInstance(this@SongActivity)!!
                .songDao().updateIsLikeById(song.isLike, song.id)

            showCustomToast(if (song.isLike) "좋아요 추가됨" else "좋아요 제거됨", song.isLike)
        }
    }

    private fun moveSong(d: Int) {
        if (nowPos + d !in songs.indices) {
            Toast.makeText(this, "더 이상 곡이 없습니다", Toast.LENGTH_SHORT).show()
            return
        }
        nowPos += d
        setPlayer(songs[nowPos])
    }

    private fun showCustomToast(message: String, isLiked: Boolean) {
        val inflater = layoutInflater
        val layout = inflater.inflate(R.layout.custom_toast, null)

        val icon = layout.findViewById<ImageView>(R.id.toast_icon)
        val text = layout.findViewById<TextView>(R.id.toast_text)

        icon.setImageResource(
            if (isLiked) R.drawable.ic_my_like_on else R.drawable.ic_my_like_off
        )
        text.text = message

        with(Toast(this)) {
            duration = Toast.LENGTH_SHORT
            view = layout
            setGravity(Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL, 0, 200) // 위치 추가
            show()
        }
    }

}
