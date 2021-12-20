package com.kk.nkart.ui.view.authentication.registeration

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.kk.nkart.R
import com.kk.nkart.base.BaseActivity
import com.kk.nkart.dagger.CoreDI
import com.kk.nkart.databinding.ActivityRegistrationBinding
import com.kk.nkart.navigation.NavigationRouter
import com.kk.nkart.navigation.NavigationTarget
import javax.inject.Inject

class RegistrationActivity : BaseActivity() {
    @Inject
    lateinit var navigationRouter: NavigationRouter

    private lateinit var signUpViewModel: RegistrationViewModel
    private lateinit var binding: ActivityRegistrationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CoreDI.getActivityComponent(this).inject(this)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_registration)
        signUpViewModel = ViewModelProvider(this).get(RegistrationViewModel::class.java)
//        binding.signUpViewModel = signUpViewModel
        setContentView(binding.root)
        registerGoBack(signUpViewModel)
    }

    fun onRegister() {
        navigationRouter.navigateTo(NavigationTarget.to(NavigationTarget.DASHBOARD_SCREEN))
    }
}