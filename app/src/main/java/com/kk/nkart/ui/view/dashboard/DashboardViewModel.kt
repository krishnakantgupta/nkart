package com.kk.nkart.ui.view.dashboard

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kk.jet2articalassignment.data.api.ApiHelper
import com.kk.nkart.base.AppMemory
import com.kk.nkart.base.AppPreferences
import com.kk.nkart.base.BaseApplication
import com.kk.nkart.base.core.BaseViewModel
import com.kk.nkart.base.core.Event
import com.kk.nkart.data.api.APIRepository
import com.kk.nkart.data.models.CategoryModel
import com.kk.nkart.data.models.DashboardModel
import com.kk.nkart.data.models.ProductModel
import com.kk.nkart.navigation.NavigationTarget
import com.kk.nkart.utils.Logger
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class DashboardViewModel @Inject constructor(val appPreferences: AppPreferences, val application: BaseApplication, private val apiRepository: APIRepository) : BaseViewModel(application) {

    val categoryResponse = MutableLiveData<Event<List<CategoryModel>>>()
    val productResponse = MutableLiveData<Event<List<ProductModel>>>()
    val dashboardResponse = MutableLiveData<Event<DashboardModel>>()
    val drawerClose = MutableLiveData<Boolean>()
    val nextScreen = MutableLiveData<Event<String>>()
    val isLogin = MutableLiveData<Boolean>().apply { this.value = appPreferences.isUserLogin() }
    var pageNumber = 1

    fun getCategory() {
        compositeDisposable.add(
            apiRepository
                .getCategory()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ categoryList ->
                    categoryResponse.postValue(Event(categoryList))
                }, { throwable ->
                    Logger.e("--KK-- getCategory", "Error" + throwable.message)
                })
        )
    }

    fun getDashboardData() {
        compositeDisposable.add(
            apiRepository
                .getDashboardData(AppMemory.userModel.id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ dashboardData ->
                    dashboardResponse.postValue(Event(dashboardData))
                }, { throwable ->
                    Logger.e("--KK-- dashboardData", "Error" + throwable.message)
                })
        )
    }


    fun getProduct(){
        progressEvent.postValue(Event(true))
        compositeDisposable.add(
            apiRepository
                .getProductList(pageNumber)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ productList ->
                    productResponse.postValue(Event(productList))
                }, { throwable ->
                    Logger.e("--KK-- getProduct", "Error" + throwable.message)
                })
        )
    }

    class Factory @Inject
    constructor(private val appPreferences: AppPreferences, private val application: BaseApplication, private val apiHelper: ApiHelper) : ViewModelProvider.AndroidViewModelFactory(application) {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(DashboardViewModel::class.java)) {
                return DashboardViewModel(appPreferences, application, APIRepository(apiHelper)) as T
            }
            throw IllegalArgumentException("Unknown class name")
        }
    }

    fun loginClick(view :View){
        drawerClose()
        nextScreen.postValue(Event(NavigationTarget.LOGIN_SCREEN))
    }

    fun refreshLogin(){
        isLogin.value = appPreferences.isUserLogin()
        isLogin.postValue(isLogin.value)
    }

    private fun applyLogin(value:Boolean){
        appPreferences.setUserLogin(value)
        isLogin.value = value
        isLogin.postValue(value)
    }

    fun crossButtonClick(){
        drawerClose()
    }

    fun newCollectionClick(view :View) {
        drawerClose()
    }

    fun categoryClick(view :View) {
        drawerClose()
        nextScreen.postValue(Event(NavigationTarget.CATEGORY_SCREEN))
    }

    fun addressClick(view :View) {
        drawerClose()
        nextScreen.postValue(Event(NavigationTarget.ADDRESS_VIEW_SCREEN))
    }

    fun viewOrderClick(view :View) {
        drawerClose()
    }

    fun wishListClick(view :View) {
        drawerClose()
    }

    fun notificationClick(view :View) {
        drawerClose()
    }

    fun settingClick(view :View) {
        drawerClose()
    }

    fun helpClick(view :View) {
        drawerClose()
    }

    fun onLogoutClick() {
        drawerClose()
        applyLogin(false)
        AppMemory.resetMemory()
    }

    private fun drawerClose() {
        drawerClose.postValue(true)
    }


}

