package com.kk.nkart.ui.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.kk.nkart.R
import com.kk.nkart.ui.common.IRecyclerItemClickListener
import com.kk.nkart.utils.ImageUtils

class ImageGalleryAdapter(private val context: Context, private val imageList: List<String>) :
    PagerAdapter() {


    private var mLayoutInflater: LayoutInflater =
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    private var imageArray = listOf<String>()
    private var listener: IRecyclerItemClickListener? = null

    init {
        imageArray = imageList
    }

    fun setImages(images: ArrayList<String>) {
        imageArray = images
        notifyDataSetChanged()
    }

    fun serItemClickListener(listener: IRecyclerItemClickListener) {
        this.listener = listener
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
        imageView.tag = position
        ImageUtils.loadImage(context, imageView as ImageView, imageArray[position])
        imageView.setOnClickListener { v ->
            var index = v.tag as Int
            listener?.onItemClick(imageArray[index], index)
        }
        container.addView(imageView)
        return imageView
    }

    override fun destroyItem(container: ViewGroup, position: Int, view: Any) {
        container.removeView(view as View)
    }
}
