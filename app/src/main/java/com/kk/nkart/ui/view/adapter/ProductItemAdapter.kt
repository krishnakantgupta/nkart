package com.kk.nkart.ui.view.adapter

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
import com.kk.nkart.base.AppMemory
import com.kk.nkart.data.models.ProductModel
import com.kk.nkart.databinding.ItemProductViewBinding
import com.kk.nkart.ui.common.IRecyclerItemClickListener
import com.kk.nkart.utils.ImageUtils

abstract class ProductItemAdapter(private val context: Context) :
    RecyclerView.Adapter<ProductItemAdapter.ProductViewHolder>(), IRecyclerItemClickListener {


    constructor(context: Context, productList: List<ProductModel>) : this(context){
        this.productList = productList as ArrayList<ProductModel>
    }


    var productList: ArrayList<ProductModel> = ArrayList()

    fun setData(productList: List<ProductModel>) {
        this.productList = productList as ArrayList<ProductModel>
        notifyDataSetChanged()
    }


    fun addData(productList: List<ProductModel>) {
        this.productList.addAll(productList as ArrayList<ProductModel>)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    inner class ProductViewHolder(viewBinding: ItemProductViewBinding) : RecyclerView.ViewHolder(viewBinding.root) {
        private val binding = viewBinding

        var view: View = viewBinding.root
        lateinit var tvTitle: TextViewCompat
        lateinit var tvPrice: TextViewCompat
        lateinit var tvDiscount: TextView
        lateinit var imgHeartView: ImageViewCompat
        lateinit var imgView: ImageViewCompat

        fun bind(position: Int) {
            var productModel = productList[position]
            val discount = (productModel.price / 100) * productModel.discount
            binding.tvProductName.text = productModel.title
            binding.tvProductPrice.text = String.format(context.getString(R.string.product_price), productModel.price)
            binding.tvProductDiscountPrice.text = String.format(context.getString(R.string.product_price), discount)
            binding.tvDiscount.text = "-${productModel.discount.toInt()}%"
            productModel.thumbnailUrl?.let { ImageUtils.loadImage(binding.imgThumb.context, binding.imgThumb, it) }
            binding.tvProductDiscountPrice.paintFlags = (binding.tvProductDiscountPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG)

            if (AppMemory.wishListIds.isNotEmpty() && AppMemory.wishListIds.contains(productModel.productId)) {
                binding.btnHeart.setImageResource(R.drawable.ic_heart_filled)
                productModel.isFavourite = true
            } else {
                binding.btnHeart.setImageResource(R.drawable.ic_heart_outline)
                productModel.isFavourite = false
            }
            binding.btnHeart.tag = position
            binding.btnHeart.setOnClickListener { v ->
                var position = v.tag as Int
                if (productList[position].isFavourite) {
                    productList[position].isFavourite = false
                    AppMemory.wishListIds.remove(productList[position].productId)
                } else {
                    productList[position].isFavourite = true
                    AppMemory.wishListIds.add(productList[position].productId)
                }
                notifyItemChanged(position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        var layoutInflater = LayoutInflater.from(parent.context)
        var binding = ItemProductViewBinding.inflate(layoutInflater, parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.view.tag = position
        holder.view.setOnClickListener { v ->
            var index = v.tag as Int
            this.onItemClick(productList[index], index)
        }
        holder.bind(position)
    }
}

