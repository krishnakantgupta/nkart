package com.kk.nkart.ui.view.category

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kk.jet2articalassignment.data.api.ApiHelper
import com.kk.nkart.base.BaseApplication
import com.kk.nkart.base.core.BaseViewModel
import com.kk.nkart.base.core.Event
import com.kk.nkart.data.api.APIRepository
import com.kk.nkart.data.models.CategoryModel
import com.kk.nkart.utils.Logger
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class CategoryViewModel @Inject constructor(val application: BaseApplication, private val apiRepository: APIRepository) : BaseViewModel(application) {

    val categoryResponse = MutableLiveData<Event<List<CategoryModel>>>()

    fun getCategory() {
        Logger.v("--KK-- getCategory", "Start")
        progressEvent.postValue(Event(true))
        compositeDisposable.add(
            apiRepository
                .getCategory()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ categoryList ->
                    Logger.v("--KK-- Login", "Done" + categoryList)
                    categoryResponse.postValue(Event(categoryList))
                    progressEvent.postValue(Event(false))
                }, { throwable ->
                    Logger.e("--KK-- Login", "Error" + throwable.message)
                    progressEvent.postValue(Event(false))
//                    loginResponse.postValue(LoadingStatusDetails.error("Error:" + throwable.message,null))
                })
        )
    }

    class Factory @Inject
    constructor(private val application: BaseApplication, private val apiHelper: ApiHelper) : ViewModelProvider.AndroidViewModelFactory(application) {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(CategoryViewModel::class.java)) {
                return CategoryViewModel(application, APIRepository(apiHelper)) as T
            }
            throw IllegalArgumentException("Unknown class name")
        }
    }
}

