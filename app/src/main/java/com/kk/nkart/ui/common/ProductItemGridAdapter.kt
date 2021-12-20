package com.kk.nkart.ui.common

import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.widget.ImageViewCompat
import androidx.core.widget.TextViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.kk.nkart.R

abstract class ProductItemGridAdapter(private val context: Context) :
    RecyclerView.Adapter<ProductItemGridAdapter.ProductViewHolder>(), IRecyclerItemClickListener {


    fun setData() {
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return 19
    }

    class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var view: View = itemView
        lateinit var tvTitle: TextViewCompat
        lateinit var tvPrice: TextViewCompat
        lateinit var tvDiscount: TextView
        lateinit var imgHeartView: ImageViewCompat
        lateinit var imgView: ImageViewCompat

        init {

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_product_view, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.view.tag = position
        holder.view.setOnClickListener { v ->
            var index = v.tag as Int
            this.onItemClick(index)
        }
//        holder.tvDiscount.paintFlags = (holder.tvDiscount.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG)
    }
}


//tvProductDiscountPrice.setPaintFlags(tvProductDiscountPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG)