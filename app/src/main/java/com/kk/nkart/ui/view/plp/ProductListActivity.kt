package com.kk.nkart.ui.view.plp

import android.os.Bundle
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.kk.jet2articalassignment.data.api.ApiHelper
import com.kk.nkart.R
import com.kk.nkart.base.AppPreferences
import com.kk.nkart.base.BaseApplication
import com.kk.nkart.base.Constants
import com.kk.nkart.base.core.BaseActivity
import com.kk.nkart.dagger.CoreDI
import com.kk.nkart.data.api.ApiServiceImpl
import com.kk.nkart.data.models.ProductDetailsModel
import com.kk.nkart.data.models.ProductModel
import com.kk.nkart.databinding.ActivityProductListBinding
import com.kk.nkart.navigation.NavigationRouter
import com.kk.nkart.navigation.NavigationTarget
import com.kk.nkart.ui.view.adapter.ProductItemAdapter
import javax.inject.Inject

class ProductListActivity : BaseActivity() {

    private lateinit var binding: ActivityProductListBinding

    @Inject
    lateinit var appPreferences: AppPreferences

    @Inject
    lateinit var navigationRouter: NavigationRouter

    private var product: ProductModel? = null
    private var productDetailsModel: ProductDetailsModel = ProductDetailsModel()


    private lateinit var adapter: ProductItemAdapter
    private lateinit var viewModel: ProductListingViewModel
    internal lateinit var productListingViewModelFactory: ProductListingViewModel.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CoreDI.getActivityComponent(this).inject(this)
        setUpViewModel()
        setUpBinding()
        setUpObserver()
        init()
    }

    private fun setUpViewModel() {
        productListingViewModelFactory = ProductListingViewModel.Factory(appPreferences, this.application as BaseApplication, ApiHelper(ApiServiceImpl()))
        viewModel = ViewModelProvider(this, productListingViewModelFactory).get(ProductListingViewModel::class.java)
        registerGoBack(viewModel)
    }

    private fun setUpBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_list)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
    }

    private fun setUpObserver() {
        viewModel.progressEvent.observe(this, Observer { event ->
            event?.getContentIfNotHandled()?.let {
                if (it) {
                    progressDialog.show(this@ProductListActivity, getString(R.string.pelase_wait))
                } else {
                    progressDialog.dialog.dismiss()
                }
            }
        })

        viewModel.productListReponse.observe(this, Observer { event ->
            var productList = event.getContentIfNotHandled()
            if (productList != null) {
                adapter.setData(productList)
            }
        })
    }

    private fun init() {
        var name = "Product Listing"
        adapter = object : ProductItemAdapter(this@ProductListActivity) {
            override fun onItemClick(data: Any?, position: Int) {
                data?.let { it ->
                    var productModel = (it as ProductModel)
                    navigationRouter.navigateTo(NavigationTarget.to(NavigationTarget.PDP_SCREEN).withParam(Constants.BUNDLE_KEY_PRODUCT, productModel))
                }
            }
        }
        binding.recyclerView.layoutManager = GridLayoutManager(this, 2)
        binding.recyclerView.adapter = adapter
        var toolbar = binding.toolbar.toolbar
        if (intent.extras?.containsKey(Constants.BUNDLE_KEY_SUB_CATEGORY_ID) == true) {
            var subCategoryId = intent.extras?.getInt(Constants.BUNDLE_KEY_SUB_CATEGORY_ID) ?: 1
            var categoryId = intent.extras?.getInt(Constants.BUNDLE_KEY_CATEGORY_ID) ?: 1
            name = intent.extras?.getString(Constants.BUNDLE_KEY_SUB_CATEGORY_NAME)
                ?: "Product Listing"
            viewModel.fetchProductList(subCategoryId)
        } else {
            var productList = intent.extras?.get(Constants.BUNDLE_KEY_PRODUCT_LIST) as List<ProductModel>
                ?: listOf()
            name = intent.extras?.getString(Constants.BUNDLE_KEY_SUB_CATEGORY_NAME)
                ?: "Product Listing"
            adapter.setData(productList)
        }
        initToolbar(toolbar, true, true, name)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            android.R.id.home -> {
                finish()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }

    }
}