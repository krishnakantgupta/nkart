package com.kk.nkart.ui.view.splash

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.FrameLayout
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.afollestad.viewpagerdots.DotsIndicator
import com.kk.nkart.R
import com.kk.nkart.base.AppPreferences
import com.kk.nkart.base.BaseActivity
import com.kk.nkart.base.Constants
import com.kk.nkart.dagger.CoreDI
import com.kk.nkart.databinding.ActivitySplashBinding
import com.kk.nkart.navigation.NavigationRouter
import com.kk.nkart.navigation.NavigationTarget
import com.kk.nkart.ui.view.adapter.InfoPagerAdapter
import javax.inject.Inject

class SplashActivity : BaseActivity() {

    @Inject
    lateinit var appPreferences : AppPreferences

    @Inject
    lateinit var navigationRouter: NavigationRouter

    private lateinit var binding: ActivitySplashBinding
    private lateinit var viewPager: ViewPager
    private lateinit var bottomFrame: FrameLayout
    private lateinit var btnSkip: View
    private lateinit var btnNext: View
    private lateinit var dotIndicator: DotsIndicator
    private var isLastPageVisited: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CoreDI.getActivityComponent(this).inject(this)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
//        val binding: ActivitySplashBinding = DataBindingUtil.setContentView(this, R.layout.activity_splash)
//        val authViewModel = AuthViewModel() //ViewModelProviders.of(this).get(AuthViewModel.class);
//
//        binding.setViewModel(authViewModel)
//        binding.executePendingBindings()
//        authViewModel.setActivity(this)
        Handler().postDelayed({
            if (appPreferences.isFreshInstall()) {
                binding.viewPager.visibility = View.VISIBLE
                binding.bottomContiner.visibility = View.VISIBLE
                var adapter = InfoPagerAdapter(getContext());
                binding.viewPager.adapter = adapter
                binding.pageIndicator.attachViewPager(binding.viewPager)
                binding.btnNext.setOnClickListener { showNextPage() }
                binding.btnSkip.setOnClickListener { setAppAlreadyLaunch() }
                binding.viewPager.addOnPageChangeListener(onPageChangeListener())
            }else{
                moveToDashboard()
            }
        }, Constants.SPLASH_TIMEOUT)
    }

    private fun onPageChangeListener(): OnPageChangeListener {
        return object : OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                if (position + 1 == binding.viewPager.adapter?.count) {
                    isLastPageVisited = true
                    binding.btnNext.text = getString(R.string.start_shopping)
                } else {
                    binding.btnNext.text = getString(R.string.next)
                    isLastPageVisited = false
                }
            }

            override fun onPageScrollStateChanged(state: Int) {
            }

        }
    }

    private fun showNextPage() {
        if (!isLastPageVisited) {
            binding.viewPager.setCurrentItem(binding.viewPager.currentItem + 1, true)
        } else {
            setAppAlreadyLaunch()
        }
    }
    private fun setAppAlreadyLaunch(){
        appPreferences.setAppAlreadyInUse()
        moveToDashboard()
    }

    private fun moveToDashboard() {
        navigationRouter.navigateTo(NavigationTarget.to(NavigationTarget.DASHBOARD_SCREEN))
        finish()
    }

    private fun getContext(): Context {
        return this
    }

}