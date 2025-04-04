package com.flow.ui.adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter

class BannerPagerAdapter(
    private val context: Context,
    private val bannerImages: List<Int>
) : PagerAdapter() {

    override fun getCount(): Int = bannerImages.size

    override fun isViewFromObject(view: View, any: Any): Boolean {
        return view == any
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val imageView = ImageView(context)
        imageView.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        imageView.setImageResource(bannerImages[position])

        container.addView(imageView)
        return imageView
    }

    override fun destroyItem(container: ViewGroup, position: Int, any: Any) {
        container.removeView(any as View)
    }
}

