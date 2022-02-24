package com.kk.nkart.ui.view.plp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kk.jet2articalassignment.data.api.ApiHelper
import com.kk.nkart.base.AppPreferences
import com.kk.nkart.base.BaseApplication
import com.kk.nkart.base.core.BaseViewModel
import com.kk.nkart.base.core.Event
import com.kk.nkart.data.api.APIRepository
import com.kk.nkart.data.models.ProductDetailsModel
import com.kk.nkart.data.models.ProductModel
import com.kk.nkart.utils.Logger
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class ProductListingViewModel @Inject constructor(val appPreferences: AppPreferences, val application: BaseApplication, private val apiRepository: APIRepository) : BaseViewModel(application) {

    val productDetailsResponse = MutableLiveData<Event<ProductDetailsModel?>>()
    val productListReponse = MutableLiveData<Event<List<ProductModel>?>>()
    val isLogin = MutableLiveData<Boolean>().apply { this.value = appPreferences.isUserLogin() }


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

    fun fetchProductList(subCategoryId: Int) {
        Logger.v("--KK-- fetchProductList", "Start")
        progressEvent.postValue(Event(true))
        compositeDisposable.add(
            apiRepository
                .getProductListForSubCategory(subCategoryId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ productList ->
                    Logger.v("--KK-- fetchProductList", "Done " + productList)
                    productListReponse.postValue(Event(productList))
                    progressEvent.postValue(Event(false))
                }, { throwable ->
                    Logger.e("--KK-- fetchProductList", "Error" + throwable.message)
                    progressEvent.postValue(Event(false))
                    productListReponse.postValue(Event(null))
                })
        )

    }


    class Factory @Inject
    constructor(private val appPreferences: AppPreferences, private val application: BaseApplication, private val apiHelper: ApiHelper) : ViewModelProvider.AndroidViewModelFactory(application) {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ProductListingViewModel::class.java)) {
                return ProductListingViewModel(appPreferences, application, APIRepository(apiHelper)) as T
            }
            throw IllegalArgumentException("Unknown class name")
        }
    }
}

