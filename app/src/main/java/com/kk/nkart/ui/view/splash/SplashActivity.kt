package com.kk.nkart.ui.view.splash

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.FrameLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.afollestad.viewpagerdots.DotsIndicator
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.kk.jet2articalassignment.data.api.APIConstants
import com.kk.jet2articalassignment.data.api.ApiHelper
import com.kk.nkart.R
import com.kk.nkart.base.AppMemory
import com.kk.nkart.base.AppPreferences
import com.kk.nkart.base.BaseApplication
import com.kk.nkart.base.Constants
import com.kk.nkart.base.core.BaseActivity
import com.kk.nkart.dagger.CoreDI
import com.kk.nkart.data.api.ApiServiceImpl
import com.kk.nkart.databinding.ActivitySplashBinding
import com.kk.nkart.navigation.NavigationRouter
import com.kk.nkart.navigation.NavigationTarget
import com.kk.nkart.ui.view.adapter.InfoPagerAdapter
import javax.inject.Inject

class SplashActivity : BaseActivity() {

    private lateinit var remoteConfig: FirebaseRemoteConfig

    @Inject
    lateinit var appPreferences: AppPreferences

    @Inject
    lateinit var navigationRouter: NavigationRouter

    internal lateinit var splashViewModelFactory: SplashViewModel.Factory
    private lateinit var splashViewModel: SplashViewModel

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
        setUpViewModel()
        setUpObserver()
        remoteConfig = Firebase.remoteConfig
        remoteConfig.fetch().addOnCompleteListener(this, object : OnCompleteListener<Void> {
            override fun onComplete(task: Task<Void>) {
                if (task.isSuccessful) {
                    remoteConfig.activate()
                    APIConstants.BASE_URL = remoteConfig.getString("ngrok_url")
                    appPreferences.getUserCredential()?.let {
                        splashViewModel.doLogin(it)
                    } ?: init(Constants.SPLASH_TIMEOUT)
                }
            }
        })
    }

    private fun setUpObserver() {
        splashViewModel.loginResponse.observe(this, Observer { event ->
            event?.getContentIfNotHandled()?.let {
                if (it != null) {
                    var response = it
                    AppMemory.userModel = it
                    appPreferences.setUserLogin(true)
                } else {
                    appPreferences.logout()
                }
                init(500L)
            }
        })
    }

    private fun init(time: Long) {
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
            } else {
                moveToDashboard()
            }
        }, time)
    }

    private fun setUpViewModel() {
        splashViewModelFactory = SplashViewModel.Factory(appPreferences, this.application as BaseApplication, ApiHelper(ApiServiceImpl()))
        splashViewModel = ViewModelProvider(this, splashViewModelFactory).get(SplashViewModel::class.java)
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

    private fun setAppAlreadyLaunch() {
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