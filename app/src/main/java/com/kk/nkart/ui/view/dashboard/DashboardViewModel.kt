package com.kk.nkart.ui.view.dashboard

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kk.jet2articalassignment.data.api.ApiHelper
import com.kk.nkart.base.AppMemory
import com.kk.nkart.base.AppPreferences
import com.kk.nkart.base.BaseApplication
import com.kk.nkart.base.Constants
import com.kk.nkart.base.core.BaseViewModel
import com.kk.nkart.base.core.Event
import com.kk.nkart.data.api.APIRepository
import com.kk.nkart.data.models.CategoryModel
import com.kk.nkart.data.models.DashboardModel
import com.kk.nkart.data.models.ProductModel
import com.kk.nkart.navigation.NavigationRouter
import com.kk.nkart.navigation.NavigationTarget
import com.kk.nkart.utils.Logger
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class DashboardViewModel @Inject constructor(val navigationRouter: NavigationRouter, val appPreferences: AppPreferences, val application: BaseApplication, private val apiRepository: APIRepository) : BaseViewModel(application) {

    val cartListResponse = MutableLiveData<Event<Int>>()
    val categoryResponse = MutableLiveData<Event<List<CategoryModel>>>()
    val productResponse = MutableLiveData<Event<List<ProductModel>>>()
    val dashboardResponse = MutableLiveData<Event<DashboardModel>>()
    val drawerClose = MutableLiveData<Boolean>()
    val nextScreen = MutableLiveData<Event<String>>()
    val isLogin = MutableLiveData<Boolean>().apply { this.value = appPreferences.isUserLogin() }
    var userName= MutableLiveData<String>().apply { this.value = AppMemory.userModel.getFullName() }
    var emailAddress= MutableLiveData<String>().apply { this.value = AppMemory.userModel.email }
    var pageNumber = 1
    var newCollection: List<ProductModel> = listOf()

    fun getCategory() {
        Logger.v("--KK-- getCategory", "Start")
        compositeDisposable.add(
            apiRepository
                .getCategory()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ categoryList ->
                    AppMemory.categoryList = categoryList
                    categoryResponse.postValue(Event(categoryList))
                    Logger.e("--KK-- getCategory", "Done" + categoryList)
                }, { throwable ->
                    Logger.e("--KK-- getCategory", "Error" + throwable.message)
                })
        )
    }


    fun getCartList(userID: Int) {
        Logger.v("--KK-- getCartList", "Start")
        compositeDisposable.add(
            apiRepository
                .getCartList(userID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ cartList ->
                    Logger.v("--KK-- getCartList", "Done" + cartList)
                    AppMemory.cartListIds.clear()
                    if (!cartList.isNullOrEmpty()) {
                        cartList.forEach { it -> AppMemory.cartListIds.add(it.productId) }
                    }
                    cartListResponse.postValue(Event(AppMemory.cartListIds.size))
                }, { throwable ->
                    Logger.e("--KK-- getCartList", "Error" + throwable.message)
                    cartListResponse.postValue(Event(0))
                })
        )
    }

    fun getDashboardData() {
        compositeDisposable.add(
            apiRepository
                .getDashboardData(AppMemory.userModel.userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ dashboardData ->
                    dashboardResponse.postValue(Event(dashboardData))
                }, { throwable ->
                    Logger.e("--KK-- dashboardData", "Error" + throwable.message)
                })
        )
    }


    fun getProduct() {
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
    constructor(private val navigationRouter: NavigationRouter, private val appPreferences: AppPreferences, private val application: BaseApplication, private val apiHelper: ApiHelper) : ViewModelProvider.AndroidViewModelFactory(application) {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(DashboardViewModel::class.java)) {
                return DashboardViewModel(navigationRouter, appPreferences, application, APIRepository(apiHelper)) as T
            }
            throw IllegalArgumentException("Unknown class name")
        }
    }

    fun loginClick(view: View) {
        drawerClose()
        nextScreen.postValue(Event(NavigationTarget.LOGIN_SCREEN))
    }

//    fun getUserName(): String {
//        if (appPreferences.isUserLogin()) {
//            return AppMemory.userModel.getFullName()
//        }
//        return ""
//    }
//
//    fun getEmilAddress(): String {
//        if (appPreferences.isUserLogin()) {
//            return AppMemory.userModel.email
//        }
//        return ""
//    }

    fun refreshLogin() {
        isLogin.value = appPreferences.isUserLogin()
        isLogin.postValue(isLogin.value)
        if (AppMemory.categoryList?.isEmpty() == true && isLogin.value == true) {
            getCartList(AppMemory.userModel.userId)
        }
        if(isLogin.value == true){
            userName.value = AppMemory.userModel.firstName
            emailAddress.value= AppMemory.userModel.email
            userName.postValue(userName.value)
            emailAddress.postValue(emailAddress.value)
        }
        getDashboardData()
    }

    fun crossButtonClick() {
        drawerClose()
    }

    fun newCollectionClick(view: View) {
        drawerClose()
        var navigationTarget = NavigationTarget.to(NavigationTarget.PLP_SCREEN)
        navigationTarget.withParam(Constants.BUNDLE_KEY_SUB_CATEGORY_NAME, "New Collection")
        navigationTarget.withParam(Constants.BUNDLE_KEY_PRODUCT_LIST, newCollection)
        navigationRouter.navigateTo(navigationTarget)
    }

    fun categoryClick(view: View) {
        drawerClose()
        nextScreen.postValue(Event(NavigationTarget.CATEGORY_SCREEN))
    }

    fun addressClick(view: View) {
        drawerClose()
        nextScreen.postValue(Event(NavigationTarget.ADDRESS_VIEW_SCREEN))
    }

    fun viewOrderClick(view: View) {
        drawerClose()
    }

    fun wishListClick(view: View) {
        drawerClose()
        nextScreen.postValue(Event(NavigationTarget.WISHLIST_SCREEN))
    }

    fun notificationClick(view: View) {
        drawerClose()
    }

    fun settingClick(view: View) {
        drawerClose()
    }

    fun helpClick(view: View) {
        drawerClose()
    }

    fun onLogoutClick() {
        drawerClose()
        appPreferences.logout()
        isLogin.value = false
        isLogin.postValue(false)
        AppMemory.resetMemory()
    }

    private fun drawerClose() {
        drawerClose.postValue(true)
    }

}

