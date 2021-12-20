package com.kk.nkart.ui.common

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.kk.nkart.R

class ImageGalleryAdapter(private val context: Context, private val imageList: ArrayList<String>) :
    PagerAdapter() {


    private var mLayoutInflater: LayoutInflater =
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    private var imageArray = arrayListOf<String>()

    init {
        imageArray = imageList
    }

    fun setImages(images: ArrayList<String>) {
        imageArray = images
        notifyDataSetChanged()
    }

    override fun getCount(): Int {
        return imageArray.size
    }

    override fun isViewFromObject(view: View, itemView: Any): Boolean {
        return view === itemView as View
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val imageView =
            mLayoutInflater.inflate(R.layout.item_image, container, false)
        container.addView(imageView)
        return imageView
    }

    override fun destroyItem(container: ViewGroup, position: Int, view: Any) {
        container.removeView(view as View)
    }
}
