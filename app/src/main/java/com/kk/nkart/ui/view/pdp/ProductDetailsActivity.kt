package com.kk.nkart.ui.view.pdp

import android.os.Bundle
import com.kk.nkart.base.core.BaseActivity
import com.kk.nkart.dagger.CoreDI
import com.kk.nkart.databinding.ActivityProductDetailsBinding

class ProductDetailsActivity : BaseActivity() {

    private lateinit var binding: ActivityProductDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CoreDI.getActivityComponent(this).inject(this)
        binding = ActivityProductDetailsBinding.inflate(layoutInflater)//DataBindingUtil.setContentView(this, R.layout.activity_product_details)
        setContentView(binding.root)
    }
}