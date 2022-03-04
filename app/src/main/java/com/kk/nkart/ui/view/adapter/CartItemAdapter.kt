package com.kk.nkart.ui.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kk.nkart.R
import com.kk.nkart.base.AppMemory
import com.kk.nkart.data.models.CartModel
import com.kk.nkart.databinding.ViewCartItemBinding
import com.kk.nkart.databinding.ViewCartTotalBinding
import com.kk.nkart.ui.common.IRecyclerItemClickListener


class CartItemAdapter(private val context: Context, private val itemDeleteListener: IRecyclerItemClickListener, private val itemSaveListener: IRecyclerItemClickListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var cartList: List<CartModel>? = null
    var totalPrice = 0.0
    var discountPrice = 0.0
    val ITEM: Int = 0
    val FOOTER: Int = 1
    var size = 0

    fun setData(cartList: List<CartModel>?, totalPrice: Double, discountPrice: Double) {
        this.cartList = cartList
        this.totalPrice = totalPrice
        this.discountPrice = discountPrice
        size = cartList?.size ?: 0
        notifyDataSetChanged()
    }

    fun removeItem(index: Int) {
        (this.cartList as ArrayList).removeAt(index)
        size = cartList?.size ?: 0
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var layoutInflater = LayoutInflater.from(parent.context)
        if (viewType == FOOTER) {
            var binding = ViewCartTotalBinding.inflate(layoutInflater, parent, false)
            return CartTotalViewHolder(binding)
        } else {
            var binding = ViewCartItemBinding.inflate(layoutInflater, parent, false)
            return CartViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is CartViewHolder) {
            val cartModel = cartList?.get(position)
            holder.bind(cartModel!!, position)
        } else if (holder is CartTotalViewHolder) {
            holder.bind()
        }
    }

    override fun getItemCount(): Int {
        if (size > 0) {
            return size + 1
        } else {
            return 0
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == size)
            FOOTER
        else
            ITEM
    }

    inner class CartTotalViewHolder(viewBinding: ViewCartTotalBinding) : RecyclerView.ViewHolder(viewBinding.root) {
        private val binding = viewBinding
        fun bind() {
            binding.productPrice.text = String.format(context.getString(R.string.product_price), totalPrice)
            binding.totalDiscountValue.text = String.format(context.getString(R.string.product_price), discountPrice)
            binding.totalCartPriceValue.text = String.format(context.getString(R.string.product_price), totalPrice)
        }
    }

    inner class CartViewHolder(viewBinding: ViewCartItemBinding) : RecyclerView.ViewHolder(viewBinding.root) {
        private val binding = viewBinding
        fun bind(cartModel: CartModel, position: Int) {
            binding.title.text = cartModel.title
            binding.description.text = cartModel.description
            cartModel.getColor()?.let {
                binding.colorView.root.visibility = View.VISIBLE
                binding.color.visibility = View.VISIBLE
                binding.colorView.colorView.setCardBackgroundColor(it)
            }

            cartModel.getSize()?.let {
                binding.sizeView.root.visibility = View.VISIBLE
                binding.size.visibility = View.VISIBLE
            }
            binding.btnDelete.tag = position
            binding.btnDelete.setOnClickListener { v ->
                var index = v.tag as Int
                itemDeleteListener.onItemClick(cartList!![index], index)
            }
            if (AppMemory.wishListIds.contains(cartModel.productId)) {
                binding.btnHeart.visibility = View.VISIBLE
            } else {
                binding.btnHeart.visibility = View.GONE
            }
            binding.btnHeart.tag = position
            binding.btnHeart.setOnClickListener { v ->
                var index = v.tag as Int
                v.visibility = View.GONE
                cartList?.get(index)?.let {
                    AppMemory.wishListIds.add(it.productId)
                }
                itemSaveListener.onItemClick(cartList!![index], index)
            }
        }
    }
}
