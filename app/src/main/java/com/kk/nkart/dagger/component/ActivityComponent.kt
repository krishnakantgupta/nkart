package com.kk.nkart.dagger.component

import com.kk.nkart.base.core.BaseActivity
import com.kk.nkart.dagger.module.ActivityModule
import com.kk.nkart.dagger.scopes.ActivityScope
import com.kk.nkart.ui.view.authentication.login.LoginActivity
import com.kk.nkart.ui.view.authentication.registeration.RegistrationActivity
import com.kk.nkart.ui.view.category.CategoryActivity
import com.kk.nkart.ui.view.dashboard.DashBoardActivity
import com.kk.nkart.ui.view.pdp.ProductDetailsActivity
import com.kk.nkart.ui.view.plp.ProductListActivity
import com.kk.nkart.ui.view.splash.SplashActivity
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = [ActivityModule::class])
interface ActivityComponent {

    fun inject(activity: BaseActivity?)
    fun inject(activity: DashBoardActivity)
    fun inject(activity: SplashActivity)
    fun inject(activity: LoginActivity)
    fun inject(activity: RegistrationActivity)
    fun inject(activity: CategoryActivity)
    fun inject(activity: ProductListActivity)
    fun inject(activity: ProductDetailsActivity)

//    @Component.Factory
//    interface Factory {
//        // With @BindsInstance, the Context passed in will be available in the graph
//        fun create(@BindsInstance context: Context): AppComponent
//    }

}