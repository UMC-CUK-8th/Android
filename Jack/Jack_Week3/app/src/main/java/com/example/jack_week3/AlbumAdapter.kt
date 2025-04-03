package com.example.jack_week3

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.jack_week3.R
import com.example.jack_week3.Album

class AlbumAdapter(private val albumList: List<Album>) : RecyclerView.Adapter<AlbumAdapter.AlbumViewHolder>() {

    class AlbumViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val albumCover: ImageView = itemView.findViewById(R.id.item_album_cover_img_iv)  // 앨범 커버 이미지
        private val playButton: ImageView = itemView.findViewById(R.id.item_album_play_img_iv)  // 플레이 버튼
        private val albumTitle: TextView = itemView.findViewById(R.id.item_album_title_tv)      // 앨범 제목
        private val albumSinger: TextView = itemView.findViewById(R.id.item_album_singer_tv)    // 가수 이름

        fun bind(album: Album) {
            albumCover.setImageResource(album.coverImageResId) // 이미지 설정
            albumTitle.text = album.title // 앨범 제목 설정
            albumSinger.text = album.singer // 가수 이름 설정
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_album, parent, false)
        return AlbumViewHolder(view)
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        holder.bind(albumList[position])
    }

    override fun getItemCount(): Int = albumList.size
}
