package com.kk.nkart.ui.view.pdp

import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.kk.jet2articalassignment.data.api.ApiHelper
import com.kk.nkart.R
import com.kk.nkart.base.AppPreferences
import com.kk.nkart.base.BaseApplication
import com.kk.nkart.base.core.BaseActivity
import com.kk.nkart.dagger.CoreDI
import com.kk.nkart.data.api.ApiServiceImpl
import com.kk.nkart.data.models.ProductModel
import com.kk.nkart.databinding.ActivityProductDetailsBinding
import com.kk.nkart.navigation.NavigationRouter
import javax.inject.Inject

class ProductDetailsActivity : BaseActivity() {

    @Inject
    lateinit var appPreferences: AppPreferences

    @Inject
    lateinit var navigationRouter: NavigationRouter


    private lateinit var binding: ActivityProductDetailsBinding
    private var product: ProductModel? = null


    private lateinit var viewModel: ProductDetailsViewModel
    internal lateinit var productDetailsViewModelFactory: ProductDetailsViewModel.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CoreDI.getActivityComponent(this).inject(this)
        setUpViewModel()
        setUpBinding()
        init()

    }

    private fun setUpViewModel() {
        productDetailsViewModelFactory = ProductDetailsViewModel.Factory(appPreferences, this.application as BaseApplication, ApiHelper(ApiServiceImpl()))
        viewModel = ViewModelProvider(this, productDetailsViewModelFactory).get(ProductDetailsViewModel::class.java)
        registerGoBack(viewModel)
    }

    private fun setUpBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_details)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
    }

    private fun init() {
        if (intent.extras?.containsKey("product") == true) {
            product = intent.extras?.get("product") as ProductModel
            Toast.makeText(this, "" + (product?.title), Toast.LENGTH_SHORT).show()
        }
        product?.let { setData(it) }
    }

    private fun setData(productModel: ProductModel) {

    }

}