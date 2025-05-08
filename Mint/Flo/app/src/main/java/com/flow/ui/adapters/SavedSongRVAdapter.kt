package com.flow.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.flow.data.Song
import com.flow.databinding.ItemSongBinding
import android.graphics.Color

class SavedSongRVAdapter :
    RecyclerView.Adapter<SavedSongRVAdapter.ViewHolder>() {
    private val songs = ArrayList<Song>()

    interface MyItemClickListener {
        fun onRemoveSong(songId: Int)
    }

    private lateinit var mItemClickListener: MyItemClickListener

    fun setMyItemClickListener(itemClickListener: MyItemClickListener) {
        mItemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding: ItemSongBinding =
            ItemSongBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(songs[position])
        holder.binding.itemSongMoreIv.setOnClickListener {
            val songId = songs[position].id  // 💡 먼저 ID 저장
            mItemClickListener.onRemoveSong(songId)  // DB 작업 먼저
            removeSong(position)  // 그 다음 리스트에서 삭제
        }
    }

    override fun getItemCount(): Int = songs.size

    @SuppressLint("NotifyDataSetChanged")
    fun addSongs(songs: ArrayList<Song>) {
        this.songs.clear()
        this.songs.addAll(songs)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun removeSong(position: Int) {
        if (position in songs.indices) {
            songs.removeAt(position)
            notifyDataSetChanged()
        }
    }

    inner class ViewHolder(val binding: ItemSongBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(song: Song) {
            binding.itemSongImgIv.setImageResource(song.coverImg!!)
            binding.itemSongTitleTv.text = song.title
            binding.itemSongSingerTv.text = song.singer

            // 💡 배경색 설정
            binding.itemSongContainer.setBackgroundColor(
                if (song.isChecked) Color.parseColor("#E0F7FA") // 하늘색
                else Color.WHITE
            )

            // 💡 클릭 시 체크 상태 토글
            binding.root.setOnClickListener {
                song.isChecked = !song.isChecked
                notifyItemChanged(adapterPosition)
            }
        }
    }

}
