package com.kk.nkart.ui.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kk.nkart.base.AppMemory
import com.kk.nkart.data.models.CartModel
import com.kk.nkart.databinding.ViewCartItemBinding
import com.kk.nkart.ui.common.IRecyclerItemClickListener


class CartItemAdapter(private val context: Context, private val itemDeleteListener: IRecyclerItemClickListener,private val itemSaveListener: IRecyclerItemClickListener) : RecyclerView.Adapter<CartItemAdapter.CartViewHolder>() {

    var cartList: List<CartModel>? = null

    fun setData(cartList: List<CartModel>?) {
        this.cartList = cartList
        notifyDataSetChanged()
    }

    fun removeItem(index:Int){
        (this.cartList as ArrayList).removeAt(index)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        var layoutInflater = LayoutInflater.from(parent.context)
        var binding = ViewCartItemBinding.inflate(layoutInflater, parent, false)
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val cartModel = cartList?.get(position)
        holder.bind(cartModel!!, position)
    }

    override fun getItemCount(): Int {
        return cartList?.size ?: 0
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
                itemDeleteListener.onItemClick(cartList!![index],index)
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
                itemSaveListener.onItemClick(cartList!![index],index)
            }
        }
    }
}
