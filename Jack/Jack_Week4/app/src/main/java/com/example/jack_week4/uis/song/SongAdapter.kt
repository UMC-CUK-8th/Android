package com.example.jack_week4.uis.song

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.jack_week4.data.entities.Song
import com.example.jack_week4.databinding.ItemSongBinding

class SongAdapter(
    private var songs: ArrayList<Song>
) : RecyclerView.Adapter<SongAdapter.ViewHolder>() {

    interface MyItemClickListener {
        fun onRemoveSong(position: Int)
    }

    private lateinit var mItemClickListener: MyItemClickListener

    fun setMyItemClickListener(itemClickListener: MyItemClickListener) {
        mItemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemSongBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(songs[position])
        holder.binding.itemSongMoreIv.setOnClickListener {
            if (position >= 0 && position < songs.size) {

                mItemClickListener.onRemoveSong(position)
            }
        }
    }

    override fun getItemCount(): Int = songs.size

    fun removeSong(position: Int) {
        if (position >= 0 && position < songs.size) {

            songs.removeAt(position)
            notifyItemRemoved(position)

            notifyItemRangeChanged(position, songs.size)
        }
    }

    inner class ViewHolder(val binding: ItemSongBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(song: Song) {
            binding.itemSongTitleTv.text = song.title
            binding.itemSongSingerTv.text = song.singer
            song.coverImg?.let { binding.itemSongImgIv.setImageResource(it) }

            binding.root.isSelected = song.isSelected

            // root 클릭 시 선택 상태 토글
            binding.root.setOnClickListener {
                song.isSelected = !song.isSelected
                binding.root.isSelected = song.isSelected
            }
        }
    }
}
