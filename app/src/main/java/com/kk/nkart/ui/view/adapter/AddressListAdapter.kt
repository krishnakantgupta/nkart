package com.kk.nkart.ui.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kk.nkart.data.models.AddressModel
import com.kk.nkart.databinding.ViewAddressCardBinding
import com.kk.nkart.ui.common.IRecyclerItemClickListener


class AddressListAdapter(private val context: Context, private val itemClickListener: IRecyclerItemClickListener) : RecyclerView.Adapter<AddressListAdapter.AddressViewHolder>() {

    var addressList: List<AddressModel>? = null
    var selectedIndex = 0

    fun setData(addressList: List<AddressModel>?) {
        this.addressList = addressList
        selectedIndex = 0
        addressList?.let {
            for ((index, addressModel) in it.withIndex()) {
                if(addressModel.default){
                    selectedIndex = index
                    break
                }
            }
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddressViewHolder {
        var layoutInflater = LayoutInflater.from(parent.context)
        var binding = ViewAddressCardBinding.inflate(layoutInflater, parent, false)
        return AddressViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AddressViewHolder, position: Int) {
        val addressModel = addressList?.get(position)
        holder.bind(addressModel!!, position)
    }

    override fun getItemCount(): Int {
        return addressList?.size ?: 0
    }


    inner class AddressViewHolder(viewBinding: ViewAddressCardBinding) : RecyclerView.ViewHolder(viewBinding.root) {
        private val binding = viewBinding
        fun bind(addressModel: AddressModel, position: Int) {
            binding.checkmark.isChecked = position == selectedIndex
            binding.checkmark.tag = position
            binding.checkmark.setOnClickListener { v ->
                var index = v.tag as Int
                binding.checkmark.isChecked = !binding.checkmark.isChecked
                notifyItemChanged(selectedIndex)
                notifyItemChanged(index)
                selectedIndex = index
            }
            binding.name.text = addressModel.name
            binding.address.text = addressModel.getAddressForView()
            binding.btnDelete.tag = position
            binding.btnDelete.setOnClickListener { v ->
                var position = v.tag as Int
                itemClickListener.onItemClick(addressList?.get(position)!!, position)
            }
        }
    }
}
