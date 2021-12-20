package com.kk.nkart.dagger.module

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.annotation.NonNull
import dagger.Module
import dagger.Provides
import org.jetbrains.annotations.NotNull
import javax.inject.Singleton

@Module
class AppModule(private var application: Application) {


    @Singleton
    @Provides
    fun provideApplication(): Application? {
        return application
    }


    @Provides
    @Singleton
    @NonNull
    fun provideSharedPreferences(): SharedPreferences {
        return application.getSharedPreferences("com.kk.nkart", Context.MODE_PRIVATE)
    }


}