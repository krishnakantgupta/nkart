package com.kk.nkart.ui.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import com.kk.nkart.R


class InfoPagerAdapter(private val context: Context) : PagerAdapter() {

    private var mLayoutInflater: LayoutInflater =
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    val imgIds = arrayOf(R.drawable.inf_icon_1, R.drawable.info_icon_2, R.drawable.info_icon_3)
    val imgBgsIds = arrayOf(R.drawable.info_bg_1, R.drawable.info_bg_2, R.drawable.info_bg_3)
    val titles = arrayOf(R.string.splash_title_1, R.string.splash_title_2, R.string.splash_title_3);
    val descriptions =
        arrayOf(R.string.splash_description_1, R.string.splash_title_2, R.string.splash_title_3);
//    private lateinit var binding : ViewSplashPagerBining

    fun setData() {

    }

    override fun getCount(): Int {
        return 3
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object` as View
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
//        binding = ViewSplashPagerBining.inflate(mLayoutInflater)
//        val view = binding.root
        val itemView =
            mLayoutInflater.inflate(R.layout.view_splash_pager, container, false) as ViewGroup
        (itemView.findViewById<TextView>(R.id.tvTitle)).text = context.getString(titles[position])
        (itemView.findViewById<TextView>(R.id.tvDescription)).text =
            context.getString(descriptions[position])
        (itemView.findViewById<ImageView>(R.id.icon)).setImageResource(imgIds[position])
        (itemView.findViewById<ImageView>(R.id.icon)).setBackgroundResource(imgBgsIds[position])
        container.addView(itemView)
        return itemView
    }


    override fun destroyItem(container: ViewGroup, position: Int, view: Any) {
        container.removeView(view as View)
    }
}