package com.kk.nkart.dagger

import android.app.Application
import com.kk.nkart.base.core.BaseActivity
import com.kk.nkart.dagger.component.ActivityComponent
import com.kk.nkart.dagger.component.AppComponent
import com.kk.nkart.dagger.component.DaggerAppComponent
import com.kk.nkart.dagger.module.ActivityModule
import com.kk.nkart.dagger.module.AppModule

//@Singleton
class CoreDI {

    companion object {
        private var applicationComponent: AppComponent? = null

        @JvmStatic
        fun getApplicationComponent(application: Application?): AppComponent {
            if (applicationComponent == null) {
                applicationComponent = DaggerAppComponent.builder().appModule(AppModule(application!!)).build()
            }
            return applicationComponent!!
        }

        @JvmStatic
        fun getActivityComponent(activity: BaseActivity) : ActivityComponent{
            return getApplicationComponent(activity.application).plus(ActivityModule(activity))
        }

//        @JvmStatic
//        fun getFragmentComponent(fragment: BaseFragment): AppComponent {
//            return getApplicationComponent(fragment.activity?.application)
//        }
    }
}