package com.kk.nkart.ui.view.plp

import android.os.Bundle
import android.os.PersistableBundle
import com.kk.nkart.base.BaseActivity
import com.kk.nkart.databinding.ActivityProductListBinding

class ProductListActivity : BaseActivity() {

    private lateinit var binding: ActivityProductListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductListBinding.inflate(layoutInflater)

        var window = window;
//        window.AddFlags(Android.Views.WindowManagerFlags.DrawsSystemBarBackgrounds);
//        window.ClearFlags(Android.Views.WindowManagerFlags.TranslucentStatus);
//        window.SetStatusBarColor(color.ToPlatformColor());
    }
}