package com.example.jack_week3

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.jack_week3.R
import com.example.jack_week3.Album

class AlbumAdapter(
    private val albumList: List<Album>,
    private val itemClickListener: OnItemClickListener
) : RecyclerView.Adapter<AlbumAdapter.AlbumViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(album: Album)
    }

    inner class AlbumViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.item_album_title_tv)
        val singer: TextView = itemView.findViewById(R.id.item_album_singer_tv)
        val image: ImageView = itemView.findViewById(R.id.item_album_cover_img_iv)
        val playBtn: ImageView = itemView.findViewById(R.id.item_album_play_img_iv)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    itemClickListener.onItemClick(albumList[position])
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_album, parent, false)
        return AlbumViewHolder(view)
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        val album = albumList[position]
        holder.title.text = album.title
        holder.singer.text = album.singer
        holder.image.setImageResource(album.coverImageResId)
    }

    override fun getItemCount(): Int = albumList.size
}
