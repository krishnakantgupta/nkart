package com.kk.nkart.dagger.module

import android.app.Activity
import android.app.Application
import android.content.SharedPreferences
import androidx.annotation.NonNull
import com.kk.nkart.base.AppPreferences
import com.kk.nkart.dagger.scopes.ActivityScope
import com.kk.nkart.navigation.NavigationRouter
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ActivityModule(private var activity: Activity) {


    @Provides
    fun provideActivity(): Activity {
        return activity
    }

    @Provides
    @ActivityScope
    @NonNull
    fun providesNavigationRouter(): NavigationRouter {
        return NavigationRouter(activity)
    }


    @Singleton
    @NonNull
    open fun provideAppPreferences(sharedPreferences: SharedPreferences): AppPreferences {
        return AppPreferences(sharedPreferences)
    }
}