package com.kk.nkart.ui.view.address

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kk.jet2articalassignment.data.api.ApiHelper
import com.kk.nkart.base.AppMemory
import com.kk.nkart.base.BaseApplication
import com.kk.nkart.base.core.BaseViewModel
import com.kk.nkart.base.core.Event
import com.kk.nkart.data.api.APIRepository
import com.kk.nkart.data.models.AddressModel
import com.kk.nkart.data.models.CategoryModel
import com.kk.nkart.utils.Logger
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class AddressListingViewModel @Inject constructor(val application: BaseApplication, private val apiRepository: APIRepository) : BaseViewModel(application) {

    val addressResponse = MutableLiveData<Event<List<AddressModel>>>()

    fun getAddress() {
        compositeDisposable.add(
            apiRepository
                .getAddressList(AppMemory.userModel.userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ addressList ->
                    addressResponse.postValue(Event(addressList))
                }, { throwable ->
                    Logger.e("--KK-- getAddress", "Error" + throwable.message)
                })
        )
    }

    class Factory @Inject
    constructor(private val application: BaseApplication, private val apiHelper: ApiHelper) : ViewModelProvider.AndroidViewModelFactory(application) {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AddressListingViewModel::class.java)) {
                return AddressListingViewModel(application, APIRepository(apiHelper)) as T
            }
            throw IllegalArgumentException("Unknown class name")
        }
    }

}

