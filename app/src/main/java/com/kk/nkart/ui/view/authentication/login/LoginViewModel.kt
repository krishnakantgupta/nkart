package com.kk.nkart.ui.view.authentication.login

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kk.jet2articalassignment.data.api.ApiHelper
import com.kk.jet2articalassignment.data.models.ArticleInfo
import com.kk.nkart.base.AppMemory
import com.kk.nkart.base.BaseApplication
import com.kk.nkart.base.core.BaseViewModel
import com.kk.nkart.base.core.Event
import com.kk.nkart.data.api.APIRepository
import com.kk.nkart.data.requestModels.LoginRequestModel
import com.kk.nkart.navigation.NavigationTarget
import com.kk.nkart.utils.Logger
import com.kk.nkart.utils.StringUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class LoginViewModel @Inject constructor(val application: BaseApplication,private val apiRepository: APIRepository) : BaseViewModel(application) {

    val loginResponse = MutableLiveData<Event<List<ArticleInfo>>>()
    val doLoginEvent = MutableLiveData<Event<LoginRequestModel>>()

    val emailText = MutableLiveData<String>().apply { value = "" }
    val passwordText = MutableLiveData<String>().apply { value = "" }

    private fun doLogin(loginRequestModel: LoginRequestModel) {
        Logger.v("--KK-- Login", "Start")
        compositeDisposable.add(
            apiRepository
//                .doLogin(loginRequestModel.getJSON())
                .getArticles()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ userList ->
//                    AppMemory.userModel = userList
                    Logger.v("--KK-- Login", "Done" + userList)
                    loginResponse.postValue(Event(userList))
                }, { throwable ->
                    Logger.e("--KK-- Login", "Error" + throwable.message)
//                    loginResponse.postValue(
//                        LoadingStatusDetails.error(
//                            "Error:" + throwable.message,
//                            null
//                        )
//                    )
                })
        )
    }


    fun emailTextChanged(s: CharSequence, start: kotlin.Int, before: kotlin.Int, count: kotlin.Int) {
        emailText.value = s.toString()
    }

    fun passwordTextChanged(s: CharSequence, start: kotlin.Int, before: kotlin.Int, count: kotlin.Int) {
        passwordText.value = s.toString()
    }

    fun loginClick() {
        if (StringUtils.isNotEmptyOrNotNull(emailText.value) && StringUtils.isNotEmptyOrNotNull(passwordText.value)) {
            var requestModel =  LoginRequestModel(emailText.value, passwordText.value)
            doLogin(requestModel)
        }else{
            //Show error
        }
    }

    fun signUpClicked() {
        navigationToEvent.postValue(Event(NavigationTarget.REGISTRATION_SCREEN))
    }

    fun forgotPasswordClicked() {
//        navigationToEvent.postValue(Event(NavigationTarget.FORGOT_PASSWORD_SCREEN))
    }

    class Factory @Inject
    constructor(private val application: BaseApplication, private val apiHelper: ApiHelper) :  ViewModelProvider.AndroidViewModelFactory(application) {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
                return LoginViewModel(application, APIRepository(apiHelper)) as T
            }
            throw IllegalArgumentException("Unknown class name")
        }

    }
}

