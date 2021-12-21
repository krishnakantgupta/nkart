package com.kk.nkart.ui.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kk.nkart.R
import com.kk.nkart.data.models.CategoryModel
import com.kk.nkart.databinding.ItemTopCategoryListBinding
import com.kk.nkart.ui.common.IRecyclerItemClickListener


class CategoryAdapter(private val context: Context, private val itemClickListener: IRecyclerItemClickListener) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    var categoryList: List<CategoryModel>? = null
    private var selectedIndex = 0

    fun setData(categoryList: List<CategoryModel>?) {
        this.categoryList = categoryList
        selectedIndex = 0
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        var layoutInflater = LayoutInflater.from(parent.context)
        var binding = ItemTopCategoryListBinding.inflate(layoutInflater, parent, false)
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        var categoryModel: CategoryModel? = null
        if (position > 0) {
            categoryModel = categoryList?.get(position - 1)
        }
        holder.bind(categoryModel, position)
    }

    override fun getItemCount(): Int {
        return (categoryList?.size ?: 0) + 1
    }


    inner class CategoryViewHolder(viewBinding: ItemTopCategoryListBinding) : RecyclerView.ViewHolder(viewBinding.root) {
        private val binding = viewBinding
        fun bind(categoryModel: CategoryModel?, position: Int) {
            binding.title.tag = position
            binding.title.text = categoryModel?.categoryName
                ?: context.resources.getString(R.string.all)
            if (position == selectedIndex) {
                binding.title.setTextColor(context.resources.getColor(R.color.colorPrimary))
            } else {
                binding.title.setTextColor(context.resources.getColor(R.color.black_a))
            }
            binding.title.setOnClickListener { v ->
                var position = v.tag as Int
                notifyItemChanged(selectedIndex)
                notifyItemChanged(position)
                selectedIndex = position
                itemClickListener.onItemClick(position)
            }
        }
    }
}
