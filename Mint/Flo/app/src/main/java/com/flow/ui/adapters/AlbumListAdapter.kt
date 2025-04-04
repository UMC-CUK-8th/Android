package com.flow.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.flow.R
import com.flow.data.Album

class AlbumListAdapter(
    private val albumList: List<Album>,
    private val onAlbumClick: (Album) -> Unit
) : RecyclerView.Adapter<AlbumListAdapter.AlbumViewHolder>() {

    inner class AlbumViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ivCover: ImageView = itemView.findViewById(R.id.iv_cover)
        private val tvTitle: TextView = itemView.findViewById(R.id.tv_title)

        fun bind(album: Album) {
            ivCover.setImageResource(album.coverResId)
            tvTitle.text = album.title
            itemView.setOnClickListener {
                onAlbumClick(album)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_album, parent, false)
        return AlbumViewHolder(view)
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        holder.bind(albumList[position])
    }

    override fun getItemCount(): Int = albumList.size
}
