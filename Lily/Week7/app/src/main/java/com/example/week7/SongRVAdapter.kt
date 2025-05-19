package com.example.week7

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.example.week7.databinding.ItemSongBinding

class SongRVAdapter(val context: Context) : RecyclerView.Adapter<SongRVAdapter.ViewHolder>() {


    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): SongRVAdapter.ViewHolder {
        val binding: ItemSongBinding = ItemSongBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ViewHolder(binding)
    }




    inner class ViewHolder(val binding: ItemSongBinding) : RecyclerView.ViewHolder(binding.root){

        val coverImg : ImageView = binding.itemSongImgIv
        val title : TextView = binding.itemSongTitleTv
        val singer : TextView = binding.itemSongSingerTv

//        fun bind(song: FloChartSongs){
//            if(song.coverImgUrl == "" || song.coverImgUrl == null) {
//            } else {
//                Glide.with(context).load(song.coverImgUrl).into(binding.itemSongImgIv)
//            }
//
//            binding.itemSongTitleTv.text = song.title
//            binding.itemSongSingerTv.text = song.singer
//        }
    }

    interface MyItemClickListener{
        fun onRemoveSong(songId: Int)
    }

    private lateinit var mItemClickListener: MyItemClickListener

    fun setMyItemClickListener(itemClickListener: MyItemClickListener){
        mItemClickListener = itemClickListener
    }
}