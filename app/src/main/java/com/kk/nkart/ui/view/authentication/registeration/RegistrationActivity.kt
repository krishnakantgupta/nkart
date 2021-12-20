package com.kk.nkart.ui.view.authentication.registeration

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.kk.jet2articalassignment.data.api.ApiHelper
import com.kk.nkart.R
import com.kk.nkart.base.BaseActivity
import com.kk.nkart.base.BaseApplication
import com.kk.nkart.dagger.CoreDI
import com.kk.nkart.data.api.ApiServiceImpl
import com.kk.nkart.databinding.ActivityRegistrationBinding
import com.kk.nkart.navigation.NavigationRouter
import com.kk.nkart.navigation.NavigationTarget
import com.kk.nkart.ui.view.authentication.login.LoginViewModel
import javax.inject.Inject

class RegistrationActivity : BaseActivity() {

    internal lateinit var registrationViewModelFactory: RegistrationViewModel.Factory


    @Inject
    lateinit var navigationRouter: NavigationRouter

    private lateinit var viewModel: RegistrationViewModel
    private lateinit var binding: ActivityRegistrationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CoreDI.getActivityComponent(this).inject(this)
        setUpViewModel()
        setUpBinding()
        setUpObserver()
    }

    private fun setUpViewModel() {
        registrationViewModelFactory = RegistrationViewModel.Factory(this.application as BaseApplication, ApiHelper(ApiServiceImpl()))
        viewModel = ViewModelProvider(this, registrationViewModelFactory).get(RegistrationViewModel::class.java)
        registerGoBack(viewModel)
    }

    private fun setUpBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_registration)
        binding.signUpViewModel = viewModel
    }

    private fun setUpObserver() {
        viewModel.progressEvent.observe(this, Observer { event ->
            event?.getContentIfNotHandled()?.let {
                if (it) {

                } else {

                }
            }
        })
    }

}