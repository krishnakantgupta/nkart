package com.kk.nkart.ui.view.pdp

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
import com.kk.nkart.data.models.ProductDetailsModel
import com.kk.nkart.utils.Logger
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class ProductDetailsViewModel @Inject constructor(val appPreferences: AppPreferences, val application: BaseApplication, private val apiRepository: APIRepository) : BaseViewModel(application) {

    val productDetailsResponse = MutableLiveData<Event<ProductDetailsModel?>>()
    val addToCardResponse = MutableLiveData<Event<Boolean?>>()
    val wishListUpdate = MutableLiveData<Event<Boolean>>()
    val isLogin = MutableLiveData<Boolean>().apply { this.value = appPreferences.isUserLogin() }

    var selectedSize = -1
    var selectColor = -1
    var quantity = 1


    fun getProductDetails(productId: Int) {
        Logger.v("--KK-- getProductDetails", "Start")
        progressEvent.postValue(Event(true))
        compositeDisposable.add(
            apiRepository
                .getProductDetails(productId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ productDetails ->
                    Logger.v("--KK-- getProductDetails", "Done" + productDetails)
                    productDetailsResponse.postValue(Event(productDetails))
                    progressEvent.postValue(Event(false))
                }, { throwable ->
                    Logger.e("--KK-- getProductDetails", "Error" + throwable.message)
                    progressEvent.postValue(Event(false))
                    productDetailsResponse.postValue(Event(null))
                })
        )
    }

    fun wishlist(v: View) {
        var productId = v.tag  as Int
        progressEvent.postValue(Event(true))
        if(AppMemory.wishListIds.contains(productId)){
            removeFromWishList(productId)
        }else{
            addToWishList(productId)
        }

    }

    private fun removeFromWishList(productId :Int ){
        compositeDisposable.add(
            apiRepository
                .addToWishlist(productId, AppMemory.userModel.userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ sucess ->
                    Logger.v("--KK-- wishlist", "Done" + sucess)
                    AppMemory.wishListIds.remove(productId)
                    wishListUpdate.postValue(Event(false))
                    progressEvent.postValue(Event(false))
                }, { throwable ->
                    Logger.e("--KK-- wishlist", "Error" + throwable.message)
                    progressEvent.postValue(Event(false))
                })
        )
    }

    private fun addToWishList(productId :Int ){
        compositeDisposable.add(
            apiRepository
                .addToWishlist(productId, AppMemory.userModel.userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ sucess ->
                    Logger.v("--KK-- wishlist", "Done" + sucess)
                    AppMemory.wishListIds.add(productId)
                    wishListUpdate.postValue(Event(true))
                    progressEvent.postValue(Event(false))
                }, { throwable ->
                    Logger.e("--KK-- wishlist", "Error" + throwable.message)
                    progressEvent.postValue(Event(false))
                })
        )
    }

    fun addToCart(v: View) {
        var productId = v.tag  as Int
        progressEvent.postValue(Event(true))
        compositeDisposable.add(
            apiRepository
                .addToCart(productId, AppMemory.userModel.userId, selectedSize, selectColor,1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ success ->
                    Logger.v("--KK-- addToCart", "Done" + success)
                    addToCardResponse.postValue(Event(success))
                    progressEvent.postValue(Event(false))
                }, { throwable ->
                    Logger.e("--KK-- addToCart", "Error" + throwable.message)
                    progressEvent.postValue(Event(false))
                    addToCardResponse.postValue(Event(false))
                })
        )
    }


    class Factory @Inject
    constructor(private val appPreferences: AppPreferences, private val application: BaseApplication, private val apiHelper: ApiHelper) : ViewModelProvider.AndroidViewModelFactory(application) {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ProductDetailsViewModel::class.java)) {
                return ProductDetailsViewModel(appPreferences, application, APIRepository(apiHelper)) as T
            }
            throw IllegalArgumentException("Unknown class name")
        }
    }
}

