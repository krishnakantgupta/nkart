package com.kk.nkart.ui.view.authentication.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kk.jet2articalassignment.data.api.ApiHelper
import com.kk.jet2articalassignment.data.models.ArticleInfo
import com.kk.nkart.base.core.BaseViewModel
import com.kk.nkart.data.LoadingStatusDetails
import com.kk.nkart.data.api.APIRepository
import com.kk.nkart.data.requestModels.LoginRequestModel
import com.kk.nkart.utils.Logger
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


class LoginViewModel(private val apiRepository: APIRepository) : BaseViewModel() {

    private val loginResponse = MutableLiveData<LoadingStatusDetails<List<ArticleInfo>>>()
    val loginEvent = MutableLiveData<LoginRequestModel>()
    private val compositeDisposable = CompositeDisposable()

    fun doLogin(){
        Logger.v("--KK-- Login", "Start")
        compositeDisposable.add(
            apiRepository
                .getArticles()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ userList ->
                    Logger.v("--KK-- Login", "Done" + userList)
                    loginResponse.postValue(LoadingStatusDetails.success(userList))
                }, { throwable ->
                    Logger.v("--KK-- Login", "Done" + throwable.message)
                    loginResponse.postValue(
                        LoadingStatusDetails.error(
                            "Error:" + throwable.message,
                            null
                        )
                    )
                })
        )
    }


    class Factory(private val apiHelper: ApiHelper) : ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
                return LoginViewModel(APIRepository(apiHelper)) as T
            }
            throw IllegalArgumentException("Unknown class name")
        }

    }
}

