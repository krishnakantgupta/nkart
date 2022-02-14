package com.kk.nkart.ui.view.cart

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.kk.jet2articalassignment.data.api.ApiHelper
import com.kk.nkart.R
import com.kk.nkart.base.AppMemory
import com.kk.nkart.base.AppPreferences
import com.kk.nkart.base.BaseApplication
import com.kk.nkart.base.core.BaseActivity
import com.kk.nkart.dagger.CoreDI
import com.kk.nkart.data.api.ApiServiceImpl
import com.kk.nkart.data.models.CartModel
import com.kk.nkart.databinding.ActivityCartBinding
import com.kk.nkart.navigation.NavigationRouter
import com.kk.nkart.ui.common.IRecyclerItemClickListener
import com.kk.nkart.ui.view.adapter.CartItemAdapter
import javax.inject.Inject

class CartActivity : BaseActivity() {


    internal lateinit var cartViewModelFactory: CartViewModel.Factory


    @Inject
    lateinit var navigationRouter: NavigationRouter

    @Inject
    lateinit var appPreferences: AppPreferences

    private lateinit var viewModel: CartViewModel
    private lateinit var binding: ActivityCartBinding

    lateinit var cartListAdapter: CartItemAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CoreDI.getActivityComponent(this).inject(this)
        setUpViewModel()
        setUpBinding()
        setUpObserver()
        init()
    }


    private fun setUpViewModel() {
        cartViewModelFactory = CartViewModel.Factory(navigationRouter, appPreferences, this.application as BaseApplication, ApiHelper(ApiServiceImpl()))
        viewModel = ViewModelProvider(this, cartViewModelFactory).get(CartViewModel::class.java)
        registerGoBack(viewModel)
    }

    private fun setUpBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cart)
        binding.viewModel = viewModel
    }


    private fun setUpObserver() {
        viewModel.progressEvent.observe(this, Observer { event ->
            event?.getContentIfNotHandled()?.let {
                if (it) {
                    progressDialog.show(this@CartActivity, getString(R.string.pelase_wait))
                } else {
                    progressDialog.dialog.dismiss()
                }
            }
        })
        viewModel.modeToWishList.observe(this, Observer { event ->
            event?.getContentIfNotHandled()?.let {
                if (it != -1) {
                    cartListAdapter.removeItem(it)
                }
            }
        })

        viewModel.deleteFromcartResponse.observe(this, Observer { event ->
            event?.getContentIfNotHandled()?.let {
                if (it != -1) {
                    cartListAdapter.removeItem(it)
                }
            }
        })
        viewModel.cartListResponse.observe(this, Observer { event ->
            event?.getContentIfNotHandled()?.let {
                if (it != null && it.isNotEmpty()) {
                    binding.emptyView.visibility = View.GONE
                    binding.recyclerView.visibility = View.VISIBLE
                    cartListAdapter.setData(it)
                } else {
                    binding.btnCheckout.visibility = View.GONE
                    binding.recyclerView.visibility = View.GONE
                    binding.emptyView.visibility = View.VISIBLE
                }
            }
        })
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return super.onCreateOptionsMenu(menu)
    }

    @SuppressLint("WrongConstant")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun init() {
        var toolbar = binding.toolbar.toolbar
        initToolbar(toolbar, true, true, "My cart")

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        cartListAdapter = CartItemAdapter(this@CartActivity, object : IRecyclerItemClickListener {
            override fun onItemClick(data: Any?, position: Int) {
//                cartListAdapter.removeItem(position)
//                var list = (cartListAdapter.cartList as ArrayList)
//                list.removeAt(position)
//                cartListAdapter.setData(list)
                viewModel.deleteFromCart(data as CartModel, position)
            }
        }, object : IRecyclerItemClickListener {
            override fun onItemClick(data: Any?, position: Int) {
//                cartListAdapter.saveItemInWishList(position)
                viewModel.moveToWishList(data as CartModel, position)
            }
        })
        binding.recyclerView.adapter = cartListAdapter
        viewModel.getCartList(AppMemory.userModel.userId)
    }

}