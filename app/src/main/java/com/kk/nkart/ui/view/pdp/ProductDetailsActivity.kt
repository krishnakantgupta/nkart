package com.kk.nkart.ui.view.pdp

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.view.get
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.flexbox.FlexboxLayout
import com.kk.jet2articalassignment.data.api.ApiHelper
import com.kk.nkart.R
import com.kk.nkart.base.AppMemory
import com.kk.nkart.base.AppPreferences
import com.kk.nkart.base.BaseApplication
import com.kk.nkart.base.Constants
import com.kk.nkart.base.core.BaseActivity
import com.kk.nkart.dagger.CoreDI
import com.kk.nkart.data.api.ApiServiceImpl
import com.kk.nkart.data.models.ProductDetailsModel
import com.kk.nkart.data.models.ProductModel
import com.kk.nkart.databinding.ActivityProductDetailsBinding
import com.kk.nkart.navigation.NavigationRouter
import com.kk.nkart.navigation.NavigationTarget
import com.kk.nkart.utils.ImageUtils
import javax.inject.Inject

class ProductDetailsActivity : BaseActivity() {

    @Inject
    lateinit var appPreferences: AppPreferences

    @Inject
    lateinit var navigationRouter: NavigationRouter


    private lateinit var binding: ActivityProductDetailsBinding
    private var product: ProductModel? = null
    private var productDetailsModel: ProductDetailsModel = ProductDetailsModel()


    private lateinit var viewModel: ProductDetailsViewModel
    internal lateinit var productDetailsViewModelFactory: ProductDetailsViewModel.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CoreDI.getActivityComponent(this).inject(this)
        setUpViewModel()
        setUpBinding()
        setUpObserver()
        init()
        fetchDetails()
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

    private fun setUpObserver() {
        viewModel.addToCardResponse.observe(this, Observer { event ->
            event?.getContentIfNotHandled()?.let {
                if (it) {
                    binding.btnAddToCart.visibility = View.GONE
                    product?.let {
                        AppMemory.cartListIds.add(it.productId)
                    }
                    setupBedge(AppMemory.cartListIds.size)
                }
            }
        })
        viewModel.wishListUpdate.observe(this, Observer { event ->
            event?.getContentIfNotHandled()?.let {
                updateWishListIcon(it)
            }
        })
        viewModel.productDetailsResponse.observe(this, Observer { event ->
            event?.getContentIfNotHandled()?.let {
                productDetailsModel = it
                productDetailsModel.copyDataFromProduct(product!!)
                bindData()
            }
        })
        viewModel.progressEvent.observe(this, Observer { event ->
            event?.getContentIfNotHandled()?.let {
                if (it) {
                    progressDialog.show(this@ProductDetailsActivity, getString(R.string.pelase_wait))
                } else {
                    progressDialog.dialog.dismiss()
                }
            }
        })

    }

    private fun init() {
        if (intent.extras?.containsKey(Constants.BUNDLE_KEY_PRODUCT) == true) {
            product = intent.extras?.get(Constants.BUNDLE_KEY_PRODUCT) as ProductModel
            Toast.makeText(this, "" + (product?.title), Toast.LENGTH_SHORT).show()
        }
        product?.let { setProductData(it) }
        var toolbar = binding.toolbar.toolbar
        initToolbar(toolbar, true, true, "Product Details")
    }

    private fun setProductData(productModel: ProductModel) {
        product = productModel
        productDetailsModel.copyDataFromProduct(productModel)
        bindData()
    }

    private fun bindData() {
        updateWishListIcon(AppMemory.wishListIds.contains(product?.productId))
        productDetailsModel.thumbnailUrl?.let { ImageUtils.loadImage(binding.imgProduct.context, binding.imgProduct, it) }
        val discount = (productDetailsModel.price / 100) * productDetailsModel.discount
        binding.tvDiscount.text = "${productDetailsModel.discount.toInt()}% OFF"
        binding.btnAddToCart.tag = productDetailsModel.productId
        binding.imgWishlist.tag = productDetailsModel.productId
        binding.tvPrice.text = String.format(getString(R.string.product_price), productDetailsModel.price - discount)
        binding.tvTitle.text = productDetailsModel.title
        binding.tvSavePrice.text = "Save Rs. $discount"
        binding.tvMrp.text = "MRP ${productDetailsModel.price}"
//        binding.tvCategory.text = productDetailsModel.categoryId
        binding.tvDescription.text = productDetailsModel.description
        bindSize()
        bindColor()
    }

    private fun updateWishListIcon(isInWishList:Boolean){
        if (isInWishList) {
            binding.imgWishlist.setImageResource(R.drawable.ic_heart_filled)
        }else{
            binding.imgWishlist.setImageResource(R.drawable.ic_heart_outline)
        }
    }

    private fun bindSize() {
        var flexboxLayout = binding.sizeParent
        flexboxLayout.removeAllViews()
        var layoutInflater = LayoutInflater.from(this)

        productDetailsModel.productSize?.let {
            for ((index, ProductSize) in it.withIndex()) {
                var sizeView = layoutInflater.inflate(R.layout.view_size, flexboxLayout, false) as TextView
                sizeView.text = ProductSize.sizeValue
                sizeView.tag = index
                sizeView.setOnClickListener { v ->
                    var index = v.tag as Int
                    viewModel.selectedSize = productDetailsModel.productSize?.get(index)?.sizeId
                        ?: -1
                    resetSelectionSize(binding.sizeParent)
                    v.setBackgroundResource(R.drawable.bg_rect_theme)
                    (v as TextView).setTextColor(resources.getColor(R.color.colorPrimary))
                }
                flexboxLayout.addView(sizeView)
            }
            flexboxLayout[0].performClick()
            flexboxLayout[0].setBackgroundResource(R.drawable.bg_rect_theme)
            (flexboxLayout[0] as TextView).setTextColor(resources.getColor(R.color.colorPrimary))
        }
    }

    private fun resetSelectionSize(flexboxLayout: FlexboxLayout) {
        var count = flexboxLayout.childCount
        flexboxLayout.get(0)
        var i = 0;
        while (i < count) {
            (flexboxLayout[i] as TextView).setTextColor(Color.BLACK)
            flexboxLayout[i].setBackgroundResource(R.drawable.bg_rect_black)
            i++
        }
    }

    private fun resetSelectionColor(flexboxLayout: FlexboxLayout) {
        var count = flexboxLayout.childCount
        flexboxLayout.get(0)
        var i = 0;
        while (i < count) {
            ((flexboxLayout[i] as CardView)[0] as ImageView).visibility = View.INVISIBLE
            i++
        }
    }

    private fun bindColor() {
        var flexboxLayout = binding.colorParent
        flexboxLayout.removeAllViews()
        var layoutInflater = LayoutInflater.from(this)

        productDetailsModel.productColour?.let {
            for ((index, ProductSize) in it.withIndex()) {
                var colorView = layoutInflater.inflate(R.layout.view_color, flexboxLayout, false) as CardView
                var colorCode = Color.parseColor(ProductSize.colourCode)
                colorView.setCardBackgroundColor(colorCode)
                colorView.tag = index
                colorView.setOnClickListener { v ->
                    var index = v.tag as Int
                    viewModel.selectColor = productDetailsModel.productColour?.get(index)?.colourId
                        ?: -1
                    resetSelectionColor(binding.colorParent)
                    ((v as CardView)[0] as ImageView).visibility = View.VISIBLE
                }
                flexboxLayout.addView(colorView)
            }
            flexboxLayout[0].performClick()
            ((flexboxLayout[0] as CardView)[0] as ImageView).visibility = View.VISIBLE
        }
    }

    private fun fetchDetails() {
        product?.let {
            viewModel.getProductDetails(it.productId)
        }
    }

    override fun onResume() {
        super.onResume()
        setupBedge(AppMemory.cartListIds.size)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_pdp, menu)
        val menuItem = menu!!.findItem(R.id.menu_cart)
        val actionView = menuItem.actionView
        textCartItemCount = actionView.findViewById<TextView>(R.id.cart_badge)
        actionView.setOnClickListener {
            cartMenuClicked()
        }
        setupBedge(AppMemory.cartListIds.size)
        return super.onCreateOptionsMenu(menu)
    }

    @SuppressLint("WrongConstant")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_cart -> {
                cartMenuClicked()
                return true
            }
            android.R.id.home -> {
                finish()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun cartMenuClicked() {
        if (AppMemory.userModel.userId != -1) {
            navigationRouter.navigateTo(NavigationTarget.to(NavigationTarget.CART_SCREEN))
        } else {
            navigationRouter.navigateWithResult(NavigationTarget.to(NavigationTarget.LOGIN_SCREEN))
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            binding.btnAddToCart.visibility = View.GONE
            viewModel.addToCart(binding.btnAddToCart)
        }
    }
}