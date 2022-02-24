package com.kk.nkart.ui.view.wishlist

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
import com.kk.nkart.data.models.CartModel
import com.kk.nkart.data.models.ProductDetailsModel
import com.kk.nkart.data.models.WishListModel
import com.kk.nkart.utils.Logger
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class WishListViewModel @Inject constructor(val appPreferences: AppPreferences, val application: BaseApplication, private val apiRepository: APIRepository) : BaseViewModel(application) {

    val wishListResponse = MutableLiveData<Event<List<WishListModel>?>>()
    val deleteFromWishlistResponse = MutableLiveData<Event<Int>>()
    val addToCardResponse = MutableLiveData<Event<Int>>()
    val isLogin = MutableLiveData<Boolean>().apply { this.value = appPreferences.isUserLogin() }
    var quantity = 1


    fun getWishList(userID: Int) {
        Logger.v("--KK-- getWishList", "Start")
        progressEvent.postValue(Event(true))
        compositeDisposable.add(
            apiRepository
                .getWishList(userID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ wishList ->
                    Logger.v("--KK-- getWishList", "Done" + wishList)
                    AppMemory.cartListIds.clear()
                    if (!wishList.isNullOrEmpty()) {
                        AppMemory.wishListIds.clear()
                        wishList.forEach { it -> AppMemory.wishListIds.add(it.productId) }
                    }
                    wishListResponse.postValue(Event(wishList))
                    progressEvent.postValue(Event(false))
                }, { throwable ->
                    Logger.e("--KK-- getWishList", "Error" + throwable.message)
                    progressEvent.postValue(Event(false))
                    wishListResponse.postValue(Event(null))
                })
        )
    }

    fun addToCart(wishListModel: WishListModel, position: Int) {
        var productId = wishListModel.productId
        progressEvent.postValue(Event(true))
        compositeDisposable.add(
            apiRepository
                .addToCart(productId, AppMemory.userModel.userId, 1, 1,1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ success ->
                    Logger.v("--KK-- addToCart", "Done" + success)
                    AppMemory.cartListIds.add(wishListModel.productId)
                    addToCardResponse.postValue(Event(position))
                    deleteFromWishList(wishListModel,position)
                }, { throwable ->
                    Logger.e("--KK-- addToCart", "Error" + throwable.message)
                    progressEvent.postValue(Event(false))
                    addToCardResponse.postValue(Event(-1))
                })
        )
    }

    //    DeleteToWishList
    fun deleteFromWishList(model: WishListModel, position: Int) {
        var productId = model.productId
        progressEvent.postValue(Event(true))
        compositeDisposable.add(
            apiRepository
                .deleteFromWishlist(productId, AppMemory.userModel.userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ success ->
                    Logger.v("--KK-- deleteFromWishList", "Done" + success)
                    deleteFromWishlistResponse.postValue(Event(position))
                    progressEvent.postValue(Event(false))
                }, { throwable ->
                    Logger.e("--KK-- deleteFromWishList", "Error" + throwable.message)
                    progressEvent.postValue(Event(false))
                    deleteFromWishlistResponse.postValue(Event(-1))
                })
        )
    }


    class Factory @Inject
    constructor(private val appPreferences: AppPreferences, private val application: BaseApplication, private val apiHelper: ApiHelper) : ViewModelProvider.AndroidViewModelFactory(application) {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(WishListViewModel::class.java)) {
                return WishListViewModel(appPreferences, application, APIRepository(apiHelper)) as T
            }
            throw IllegalArgumentException("Unknown class name")
        }
    }
}

