package com.example.jack_week4

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.jack_week4.databinding.ItemSongBinding

class SavedSongRVAdapter : RecyclerView.Adapter<SavedSongRVAdapter.ViewHolder>() {

    private val songs = ArrayList<SongFirebase>()

    interface MyItemClickListener {
        fun onRemoveSong(songId: Int)
    }

    private lateinit var mItemClickListener: MyItemClickListener

    fun setMyItemClickListener(listener: MyItemClickListener) {
        mItemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemSongBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = songs.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(songs[position])
        holder.binding.itemSongMoreIv.setOnClickListener {
            mItemClickListener.onRemoveSong(songs[position].id)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addSongs(songList: List<SongFirebase>) {
        songs.clear()
        songs.addAll(songList)
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ItemSongBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(song: SongFirebase) {
            binding.itemSongTitleTv.text = song.title
            binding.itemSongSingerTv.text = song.singer
            // 이미지 세팅 주석 처리 (coverImg가 0일 경우 앱 크래시 방지)
            // binding.itemSongImgIv.setImageResource(song.coverImg)
        }
    }
}
