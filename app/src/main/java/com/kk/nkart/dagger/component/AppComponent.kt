package com.kk.nkart.dagger.component

import com.kk.nkart.dagger.module.ActivityModule
import com.kk.nkart.dagger.module.AppModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    fun plus(activityModule: ActivityModule): ActivityComponent

}