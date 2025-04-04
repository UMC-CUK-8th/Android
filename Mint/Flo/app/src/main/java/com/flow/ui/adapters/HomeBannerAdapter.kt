package com.flow.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.flow.databinding.ItemBannerBinding

class HomeBannerAdapter(private val bannerList: List<Int>) :
    RecyclerView.Adapter<HomeBannerAdapter.BannerViewHolder>() {

    inner class BannerViewHolder(private val binding: ItemBannerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(imageResId: Int) {
            binding.itemBannerImage.setImageResource(imageResId)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerViewHolder {
        val binding = ItemBannerBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return BannerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BannerViewHolder, position: Int) {
        holder.bind(bannerList[position])
    }

    override fun getItemCount(): Int = bannerList.size
}
