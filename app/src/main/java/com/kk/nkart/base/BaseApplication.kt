package com.kk.nkart.base

import android.app.Application
import android.content.Context
import com.kk.nkart.dagger.CoreDI
import com.kk.nkart.navigation.AppNavigator
import com.kk.nkart.navigation.NavigationRouter

class BaseApplication : Application() {


    companion object {
        lateinit var appContext: Context
    }


    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
        setUpDI()
        setupNavigation()
    }

    private fun setUpDI() {
        CoreDI.getApplicationComponent(this)
    }

    private fun setupNavigation() {
        NavigationRouter.registerNavigator(AppNavigator())
    }


}