package com.flow.ui.adapters

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.flow.data.Song
import com.flow.databinding.ItemSongBinding

class SavedSongRVAdapter(private val songs: ArrayList<Song>) :
    RecyclerView.Adapter<SavedSongRVAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemSongBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(song: Song) {
            binding.itemSongImgIv.setImageResource(song.coverImg!!)
            binding.itemSongTitleTv.text = song.title
            binding.itemSongSingerTv.text = song.singer

            binding.itemSongContainer.setBackgroundColor(
                if (song.isChecked) Color.parseColor("#E0F7FA") else Color.WHITE
            )

            binding.root.setOnClickListener {
                song.isChecked = !song.isChecked
                notifyItemChanged(absoluteAdapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemSongBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = songs.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(songs[position])
    }

    fun getSelectedSongs(): List<Song> = songs.filter { it.isChecked }

    fun removeSongs(selected: List<Song>) {
        songs.removeAll(selected)
        notifyDataSetChanged()
    }

    fun addSongs(newSongs: ArrayList<Song>) {
        songs.clear()
        songs.addAll(newSongs)
        notifyDataSetChanged()
    }

    fun selectAll() {
        songs.forEach { it.isChecked = true }
        notifyDataSetChanged()
    }
}