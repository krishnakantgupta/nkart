package com.kk.nkart.ui.view.wishlist

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
import com.kk.nkart.data.models.WishListModel
import com.kk.nkart.databinding.ActivityCartBinding
import com.kk.nkart.databinding.ActivityWishlistBinding
import com.kk.nkart.navigation.NavigationRouter
import com.kk.nkart.navigation.NavigationTarget
import com.kk.nkart.ui.common.IRecyclerItemClickListener
import com.kk.nkart.ui.view.adapter.CartItemAdapter
import com.kk.nkart.ui.view.adapter.WishListItemAdapter
import javax.inject.Inject

class WishListActivity : BaseActivity() {


    internal lateinit var wishlistViewModelFactory: WishListViewModel.Factory


    @Inject
    lateinit var navigationRouter: NavigationRouter

    @Inject
    lateinit var appPreferences: AppPreferences

    private lateinit var viewModel: WishListViewModel
    private lateinit var binding: ActivityWishlistBinding

    lateinit var cartListAdapter: WishListItemAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CoreDI.getActivityComponent(this).inject(this)
        setUpViewModel()
        setUpBinding()
        setUpObserver()
        init()
    }


    private fun setUpViewModel() {
        wishlistViewModelFactory = WishListViewModel.Factory(appPreferences, this.application as BaseApplication, ApiHelper(ApiServiceImpl()))
        viewModel = ViewModelProvider(this, wishlistViewModelFactory).get(WishListViewModel::class.java)
        registerGoBack(viewModel)
    }

    private fun setUpBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_wishlist)
        binding.viewModel = viewModel
    }


    private fun setUpObserver() {
        viewModel.progressEvent.observe(this, Observer { event ->
            event?.getContentIfNotHandled()?.let {
                if (it) {
                    progressDialog.show(this@WishListActivity, getString(R.string.pelase_wait))
                } else {
                    progressDialog.dialog.dismiss()
                }
            }
        })
        viewModel.deleteFromWishlistResponse.observe(this, Observer { event ->
            event?.getContentIfNotHandled()?.let {
                if (it !=-1) {
                    cartListAdapter.removeItem(it)
                }
            }
        })

        viewModel.addToCardResponse.observe(this, Observer { event ->
            event?.getContentIfNotHandled()?.let {
                if (it !=-1) {
                    cartListAdapter.removeItem(it)
                }
            }
        })
        viewModel.wishListResponse.observe(this, Observer { event ->
            event?.getContentIfNotHandled()?.let {
                if (it != null && it.isNotEmpty()) {
                    binding.emptyView.visibility = View.GONE
                    binding.recyclerView.visibility = View.VISIBLE
                    cartListAdapter.setData(it)
                } else {
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
        initToolbar(toolbar, true, true, getString(R.string.wishlist))

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        cartListAdapter = WishListItemAdapter(this@WishListActivity, object : IRecyclerItemClickListener {
            override fun onItemClick(data: Any?, position: Int) {
                viewModel.deleteFromWishList(data as WishListModel, position)
            }
        }, object : IRecyclerItemClickListener {
            override fun onItemClick(data: Any?, position: Int) {
                viewModel.addToCart(data as WishListModel, position)
            }
        })
        binding.recyclerView.adapter = cartListAdapter
        viewModel.getWishList(AppMemory.userModel.userId)
    }
}