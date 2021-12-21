package com.kk.nkart.ui.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kk.nkart.R
import com.kk.nkart.data.models.SubCategoryModel
import com.kk.nkart.databinding.ItemCategoryListBinding
import com.kk.nkart.databinding.ItemTopCategoryListBinding
import com.kk.nkart.ui.common.IRecyclerItemClickListener


class SubCategoryAdapter(private val context: Context, private val itemClickListener: IRecyclerItemClickListener) : RecyclerView.Adapter<SubCategoryAdapter.CategoryViewHolder>() {

    var subCategoryList: List<SubCategoryModel>? = null

    fun setData(subCategoryModel: List<SubCategoryModel>?) {
        this.subCategoryList = subCategoryModel
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        var layoutInflater = LayoutInflater.from(parent.context)
        var binding = ItemCategoryListBinding.inflate(layoutInflater, parent, false)
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val categoryModel = subCategoryList?.get(position)
        holder.bind(categoryModel!!, position)
    }

    override fun getItemCount(): Int {
        return subCategoryList?.size ?: 0
    }


    inner class CategoryViewHolder(viewBinding: ItemCategoryListBinding) : RecyclerView.ViewHolder(viewBinding.root) {
        private val binding = viewBinding
        fun bind(categoryModel: SubCategoryModel, position: Int) {
            binding.title.tag = position
            binding.title.text = categoryModel.subCategoryName
            binding.title.setOnClickListener { v ->
                var position = v.tag as Int
                itemClickListener.onItemClick(position)
            }
        }
    }
}
