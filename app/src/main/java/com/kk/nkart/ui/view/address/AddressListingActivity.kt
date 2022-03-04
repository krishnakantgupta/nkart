package com.kk.nkart.ui.view.address

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.kk.jet2articalassignment.data.api.ApiHelper
import com.kk.nkart.R
import com.kk.nkart.base.AppPreferences
import com.kk.nkart.base.BaseApplication
import com.kk.nkart.base.core.BaseActivity
import com.kk.nkart.dagger.CoreDI
import com.kk.nkart.data.api.ApiServiceImpl
import com.kk.nkart.databinding.ActivityAddressListBinding
import com.kk.nkart.navigation.NavigationRouter
import com.kk.nkart.ui.common.IRecyclerItemClickListener
import com.kk.nkart.ui.view.adapter.AddressListAdapter
import javax.inject.Inject

class AddressListingActivity : BaseActivity() {

    internal lateinit var addressListingViewModelFactory: AddressListingViewModel.Factory


    @Inject
    lateinit var navigationRouter: NavigationRouter

    @Inject
    lateinit var appPreferences: AppPreferences

    private lateinit var viewModel: AddressListingViewModel
    private lateinit var binding: ActivityAddressListBinding

    lateinit var addressListAdapter: AddressListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CoreDI.getActivityComponent(this).inject(this)
        setUpViewModel()
        setUpBinding()
        setUpObserver()
        init()
    }

    private fun setUpViewModel() {
        addressListingViewModelFactory = AddressListingViewModel.Factory(this.application as BaseApplication, ApiHelper(ApiServiceImpl()))
        viewModel = ViewModelProvider(this, addressListingViewModelFactory).get(AddressListingViewModel::class.java)
        registerGoBack(viewModel)
    }

    private fun setUpBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_address_list)
        binding.viewModel = viewModel
    }


    private fun setUpObserver() {
        viewModel.progressEvent.observe(this, Observer { event ->
            event?.getContentIfNotHandled()?.let {
                if (it) {
                    progressDialog.show(this@AddressListingActivity, getString(R.string.pelase_wait))
                } else {
                    progressDialog.dialog.dismiss()
                }
            }
        })
        viewModel.addressResponse.observe(this, Observer { event ->
            event?.getContentIfNotHandled()?.let {
                if (it != null && it.isNotEmpty()) {
                    addressListAdapter.setData(it)
                } else {
                    binding.btnDefault.visibility = View.GONE
                }
            }
        })
    }

    private fun init() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        addressListAdapter = AddressListAdapter(this@AddressListingActivity, object : IRecyclerItemClickListener {
            override fun onItemClick(data: Any?, position: Int) {
                var list = (addressListAdapter.addressList as ArrayList)
                list.removeAt(position)
                addressListAdapter.setData(list)
            }
        })
        binding.recyclerView.adapter = addressListAdapter
        viewModel.getAddress()
    }

}