package com.flow

import android.content.*
import android.os.Bundle
import android.os.IBinder
import android.view.View
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

    /* ── MusicService ───────────────────────────────────────────────────── */
    private var musicService: MusicService? = null
    private var bound = false
    private val conn = object : ServiceConnection {
        override fun onServiceConnected(c: ComponentName?, b: IBinder?) {
            musicService = (b as MusicService.MusicBinder).getService()
            bound = true
            initPlayerUI()                 // 곡 이미 재생 중이라면 위치 그대로 표시
        }
        override fun onServiceDisconnected(p0: ComponentName?) { bound = false }
    }

    /* ── Broadcast 수신 ──────────────────────────────────────────────────── */
    private val progressReceiver = object : BroadcastReceiver() {
        override fun onReceive(c: Context?, i: Intent?) {
            val cur = i?.getIntExtra("posMs", 0) ?: 0
            val dur = i?.getIntExtra("duration", 1) ?: 1
            binding.songProgressSb.progress = cur * 1000 / dur
            binding.songStartTimeTv.text =
                String.format("%02d:%02d", cur/1000/60, (cur/1000)%60)
        }
    }

    /* ───────────────────────────────────────────────────────────────────── */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // JSON으로 전달받은 곡 리스트 파싱
        val songListJson = intent.getStringExtra("songList")
        val gson = Gson()
        val songArray = gson.fromJson(songListJson, Array<Song>::class.java)
        songs.addAll(songArray)

        // Service 바인드
        Intent(this, MusicService::class.java).also { startService(it); bindService(it, conn, 0) }

        // 플레이리스트
            // songs.addAll(SongDatabase.getInstance(this)!!.songDao().getSongs())
        nowPos = songs.indexOfFirst { it.id == intent.getIntExtra("songId", 0) }.let { if (it==-1) 0 else it }

        initClick()

        // 진행률 브로드캐스트
        LocalBroadcastManager.getInstance(this)
            .registerReceiver(progressReceiver, IntentFilter("FLOW_PROGRESS"))


    }

    override fun onDestroy() {
        super.onDestroy()
        if (bound) unbindService(conn)
        LocalBroadcastManager.getInstance(this).unregisterReceiver(progressReceiver)
    }

    /* ───────────── UI 세팅 ───────────── */
    private fun initPlayerUI() = setPlayer(songs[nowPos])

    private fun setPlayer(song: Song) = with(binding) {
        songMusicTitleTv.text = song.title
        songSingerNameTv.text = song.singer
        songAlbumIv.setImageResource(song.coverImg!!)
        songEndTimeTv.text = String.format("%02d:%02d", song.playTime/60, song.playTime%60)
        songLikeIv.setImageResource(
            if (song.isLike) R.drawable.ic_my_like_on else R.drawable.ic_my_like_off
        )

        /** 이미 같은 곡이 서비스에 재생 중이면 재설정하지 않고 UI만 맞춘다 */
        if (musicService?.currentSong?.id == song.id) {
            val cur = musicService?.currentPosition ?: 0
            songProgressSb.progress = cur * 1000 / musicService!!.durationMs
            songStartTimeTv.text = String.format("%02d:%02d", cur/1000/60, (cur/1000)%60)
        } else {
            musicService?.setSong(song, autoPlay = true)   // 새 곡이면 재생
        }
        updatePlayPause()
    }

    private fun updatePlayPause() = with(binding) {
        val playing = musicService?.isPlaying == true
        songMiniplayerIv.visibility = if (playing) View.GONE else View.VISIBLE
        songPauseIv.visibility      = if (playing) View.VISIBLE else View.GONE
    }

    /* ───────────── 클릭 ───────────── */
    private fun initClick() = with(binding) {
        songMiniplayerIv.setOnClickListener { musicService?.play();  updatePlayPause() }
        songPauseIv.setOnClickListener      { musicService?.pause(); updatePlayPause() }
        songNextIv.setOnClickListener       { moveSong(+1) }
        songPreviousIv.setOnClickListener   { moveSong(-1) }
        songDownIb.setOnClickListener       { finish() }
        songLikeIv.setOnClickListener {
            val song = songs[nowPos]
            song.isLike = !song.isLike

            // UI 업데이트
            songLikeIv.setImageResource(
                if (song.isLike) R.drawable.ic_my_like_on else R.drawable.ic_my_like_off
            )

            // DB 업데이트
            SongDatabase.getInstance(this@SongActivity)!!
                .songDao().updateIsLikeById(song.isLike, song.id)

            Toast.makeText(this@SongActivity,
                if (song.isLike) "좋아요 추가됨" else "좋아요 제거됨",
                Toast.LENGTH_SHORT
            ).show()
        }

    }

    private fun moveSong(d: Int) {
        if (nowPos + d !in songs.indices) {
            Toast.makeText(this, "더 이상 곡이 없습니다", Toast.LENGTH_SHORT).show(); return
        }
        nowPos += d
        setPlayer(songs[nowPos])
    }
}
