package com.kk.nkart.ui.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kk.nkart.base.AppMemory
import com.kk.nkart.data.models.WishListModel
import com.kk.nkart.databinding.ViewWishlistItemBinding
import com.kk.nkart.ui.common.IRecyclerItemClickListener


class WishListItemAdapter(private val context: Context, private val itemDeleteListener: IRecyclerItemClickListener, private val itemSaveListener: IRecyclerItemClickListener) : RecyclerView.Adapter<WishListItemAdapter.CartViewHolder>() {

    var wishList: List<WishListModel>? = null

    fun setData(wishList: List<WishListModel>?) {
        this.wishList = wishList
        notifyDataSetChanged()
    }

    fun removeItem(index: Int) {
        var id = this.wishList?.get(index)?.productId ?: -1
        if (id != -1) {
            AppMemory.wishListIds.remove(id)
            (this.wishList as ArrayList).removeAt(index)
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        var layoutInflater = LayoutInflater.from(parent.context)
        var binding = ViewWishlistItemBinding.inflate(layoutInflater, parent, false)
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val cartModel = wishList?.get(position)
        holder.bind(cartModel!!, position)
    }

    override fun getItemCount(): Int {
        return wishList?.size ?: 0
    }


    inner class CartViewHolder(viewBinding: ViewWishlistItemBinding) : RecyclerView.ViewHolder(viewBinding.root) {
        private val binding = viewBinding
        fun bind(wishListModel: WishListModel, position: Int) {
            binding.title.text = wishListModel.title
            binding.description.text = wishListModel.shortDesc
            binding.btnDelete.tag = position
            binding.btnDelete.setOnClickListener { v ->
                var index = v.tag as Int
                itemDeleteListener.onItemClick(wishList!![index], index)
            }
            binding.btnMoveToCart.tag = position
            binding.btnMoveToCart.setOnClickListener { v ->
                var index = v.tag as Int
                wishList?.get(index)?.let {
                    AppMemory.wishListIds.remove(it.productId)
                }
                itemSaveListener.onItemClick(wishList!![index], index)
            }
        }
    }
}
