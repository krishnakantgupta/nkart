package com.kk.nkart.ui.view.splash

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import com.kk.jet2articalassignment.data.api.ApiHelper
import com.kk.nkart.base.AppPreferences
import com.kk.nkart.base.BaseApplication
import com.kk.nkart.base.core.BaseViewModel
import com.kk.nkart.base.core.Event
import com.kk.nkart.data.api.APIRepository
import com.kk.nkart.data.models.UserModel
import com.kk.nkart.data.requestModels.LoginRequestModel
import com.kk.nkart.utils.Logger
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class SplashViewModel @Inject constructor(val appPreferences: AppPreferences, val application: BaseApplication, private val apiRepository: APIRepository) : BaseViewModel(application) {

    val loginResponse = MutableLiveData<Event<UserModel?>>()

    fun doLogin(loginRequest: String) {
        Logger.v("--KK-- Splash Login", "Start")
        compositeDisposable.add(
            apiRepository
                .doLogin(Gson().fromJson(loginRequest, LoginRequestModel::class.java))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ user ->
                    Logger.v("--KK-- Splash Login", "Done" + user)
                    if (user.email != null) {
                        loginResponse.postValue(Event(user))
                    } else {
                        loginResponse.postValue(Event(null))
                    }
                }, { throwable ->
                    Logger.e("--KK-- Splash Login", "Error" + throwable.message)
                    loginResponse.postValue(Event(null))
                })
        )
    }


    class Factory @Inject
    constructor(private val appPreferences: AppPreferences, private val application: BaseApplication, private val apiHelper: ApiHelper) : ViewModelProvider.AndroidViewModelFactory(application) {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(SplashViewModel::class.java)) {
                return SplashViewModel(appPreferences, application, APIRepository(apiHelper)) as T
            }
            throw IllegalArgumentException("Unknown class name")
        }

    }
}

