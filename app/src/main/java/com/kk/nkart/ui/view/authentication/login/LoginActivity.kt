package com.kk.nkart.ui.view.authentication.login

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.kk.jet2articalassignment.data.api.ApiHelper
import com.kk.nkart.R
import com.kk.nkart.base.BaseActivity
import com.kk.nkart.base.BaseApplication
import com.kk.nkart.dagger.CoreDI
import com.kk.nkart.data.api.ApiServiceImpl
import com.kk.nkart.databinding.ActivityLoginBinding
import com.kk.nkart.navigation.NavigationRouter
import com.kk.nkart.navigation.NavigationTarget
import javax.inject.Inject

class LoginActivity : BaseActivity() {


    @Inject
    lateinit var navigationRouter: NavigationRouter

//    @Inject
    internal lateinit var LoginViewModelFactory: LoginViewModel.Factory


    private lateinit var binding: ActivityLoginBinding
    private lateinit var loginViewModel: LoginViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CoreDI.getActivityComponent(this).inject(this)
        setUpViewModel()
        setUpBinding()
        setUpObserver()
    }

    private fun setUpViewModel() {
        LoginViewModelFactory = LoginViewModel.Factory(this.application as BaseApplication, ApiHelper(ApiServiceImpl()))
        loginViewModel = ViewModelProvider(this, LoginViewModelFactory).get(LoginViewModel::class.java)
        registerGoBack(loginViewModel)
    }

    private fun setUpBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        binding.loginViewModel = loginViewModel
    }

    private fun setUpObserver() {
        loginViewModel.doLoginEvent.observe(this, Observer { event ->
            event?.getContentIfNotHandled()?.let {
            }
        })
        loginViewModel.loginResponse.observe(this, Observer { event ->
            event?.getContentIfNotHandled()?.let {
                var response = it
            }
        })
        loginViewModel.progressEvent.observe(this, Observer { event ->
            event?.getContentIfNotHandled()?.let {
                if (it) {

                } else {

                }
            }
        })
        loginViewModel.loginResponse.observe(this, Observer { event ->
            event?.getContentIfNotHandled()?.let {
                var response = it
            }
        })
        loginViewModel.navigationToEvent.observe(this, Observer { event ->
            event?.getContentIfNotHandled()?.let {
                var screenName = it
                navigationRouter.navigateTo(NavigationTarget.to(screenName))
            }
        })
    }
}

