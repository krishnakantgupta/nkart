package com.kk.nkart.ui.view.authentication.login

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.kk.jet2articalassignment.data.api.ApiHelper
import com.kk.nkart.R
import com.kk.nkart.base.BaseActivity
import com.kk.nkart.dagger.CoreDI
import com.kk.nkart.data.api.ApiServiceImpl
import com.kk.nkart.databinding.ActivityLoginBinding
import com.kk.nkart.navigation.NavigationRouter
import com.kk.nkart.navigation.NavigationTarget
import javax.inject.Inject

class LoginActivity : BaseActivity() {


    @Inject
    lateinit var navigationRouter: NavigationRouter


    private lateinit var binding: ActivityLoginBinding
    private lateinit var loginViewModel: LoginViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CoreDI.getActivityComponent(this).inject(this)
        setUpViewModel()
        setUpBinding()
//        loginViewModel.loginEvent.observe(this, Observer {
//            val loginRequestModel = it
//            navigationRouter.navigateTo(NavigationTarget.to(NavigationTarget.REGISTRATION_SCREEN))
//        })
        registerGoBack(loginViewModel)
        loginViewModel.doLogin()
    }

    private fun setUpViewModel() {
        loginViewModel = ViewModelProvider(this, LoginViewModel.Factory(ApiHelper(ApiServiceImpl()))).get(LoginViewModel::class.java)
    }

    private fun setUpBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        binding.loginViewModel = loginViewModel
    }


    fun getActivityContext(): Context {
        return this.application
    }

    fun doLoginClick(view: View) {
//        navigationRouter.navigateTo(NavigationTarget.to(NavigationTarget.DASHBOARD_SCREEN))
        loginViewModel.doLogin()
    }

    fun goBackClick(view: View) {
        finish()
    }

    fun signUpClick(view: View) {
        navigationRouter.navigateTo(NavigationTarget.to(NavigationTarget.REGISTRATION_SCREEN))
    }
}

