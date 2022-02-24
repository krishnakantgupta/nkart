package com.kk.nkart.ui.view.cart

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
import com.kk.nkart.data.models.CartModel
import com.kk.nkart.navigation.NavigationRouter
import com.kk.nkart.navigation.NavigationTarget
import com.kk.nkart.utils.Logger
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class CartViewModel @Inject constructor(val navigationRouter: NavigationRouter, val appPreferences: AppPreferences, val application: BaseApplication, private val apiRepository: APIRepository) : BaseViewModel(application) {

    val cartListResponse = MutableLiveData<Event<List<CartModel>?>>()
    val deleteFromcartResponse = MutableLiveData<Event<Int>>()
    val modeToWishList = MutableLiveData<Event<Int>>()
    val isLogin = MutableLiveData<Boolean>().apply { this.value = appPreferences.isUserLogin() }

    var selectedSize = -1
    var selectColor = -1
    var quantity = 1


    fun getCartList(userID: Int) {
        Logger.v("--KK-- getCartList", "Start")
        progressEvent.postValue(Event(true))
        compositeDisposable.add(
            apiRepository
                .getCartList(userID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ cartList ->
                    Logger.v("--KK-- getCartList", "Done" + cartList)
                    AppMemory.cartListIds.clear()
                    if (!cartList.isNullOrEmpty()) {
                        AppMemory.cartListIds.clear()
                        cartList.forEach { it -> AppMemory.cartListIds.add(it.productId) }
                    }
                    cartListResponse.postValue(Event(cartList))
                    progressEvent.postValue(Event(false))
                }, { throwable ->
                    Logger.e("--KK-- getCartList", "Error" + throwable.message)
                    progressEvent.postValue(Event(false))
                    cartListResponse.postValue(Event(null))
                })
        )
    }

    fun deleteFromCart(model: CartModel, position: Int) {
        var productId = model.productId
        progressEvent.postValue(Event(true))
        compositeDisposable.add(
            apiRepository
                .deleteFromCart(productId, AppMemory.userModel.userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ cartmodel ->
                    Logger.v("--KK-- deleteFromCart", "Done" + cartmodel)
                    AppMemory.cartListIds.remove(model.productId)
                    deleteFromcartResponse.postValue(Event(position))
                    progressEvent.postValue(Event(false))
                }, { throwable ->
                    Logger.e("--KK-- deleteFromCart", "Error" + throwable.message)
                    progressEvent.postValue(Event(false))
                    deleteFromcartResponse.postValue(Event(-1))
                })
        )
    }

    fun moveToWishList(model: CartModel, position: Int) {
        var productId = model.productId
        progressEvent.postValue(Event(true))
        compositeDisposable.add(
            apiRepository
                .addToWishlist(productId, AppMemory.userModel.userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ sucess ->
                    Logger.v("--KK-- deleteFromCart", "Done" + sucess)
                    AppMemory.cartListIds.remove(model.productId)
                    AppMemory.wishListIds.add(model.productId)
                    deleteFromCart(model, position)
//                    modeToWishList.postValue(Event(position))
//                    progressEvent.postValue(Event(false))
                }, { throwable ->
                    Logger.e("--KK-- deleteFromCart", "Error" + throwable.message)
                    progressEvent.postValue(Event(false))
                    modeToWishList.postValue(Event(-1))
                })
        )
    }


    class Factory @Inject
    constructor(private  val navigationRouter: NavigationRouter, private val appPreferences: AppPreferences, private val application: BaseApplication, private val apiHelper: ApiHelper) : ViewModelProvider.AndroidViewModelFactory(application) {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(CartViewModel::class.java)) {
                return CartViewModel(navigationRouter, appPreferences, application, APIRepository(apiHelper)) as T
            }
            throw IllegalArgumentException("Unknown class name")
        }
    }


    fun navigateToDashboard(){
        navigationRouter.navigateTo(NavigationTarget.to(NavigationTarget.DASHBOARD_SCREEN).withIntentFlagClearTopSingleTop(true))
    }
}

