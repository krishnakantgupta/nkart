package com.kk.nkart.ui.view.authentication.login

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kk.jet2articalassignment.data.api.ApiHelper
import com.kk.nkart.R
import com.kk.nkart.base.AppMemory
import com.kk.nkart.base.AppPreferences
import com.kk.nkart.base.BaseApplication
import com.kk.nkart.base.core.BaseViewModel
import com.kk.nkart.base.core.Event
import com.kk.nkart.data.api.APIRepository
import com.kk.nkart.data.models.UserModel
import com.kk.nkart.data.requestModels.LoginRequestModel
import com.kk.nkart.navigation.NavigationTarget
import com.kk.nkart.utils.Logger
import com.kk.nkart.utils.StringUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class LoginViewModel @Inject constructor(val appPreferences: AppPreferences, val application: BaseApplication, private val apiRepository: APIRepository) : BaseViewModel(application) {

    val loginResponse = MutableLiveData<Event<UserModel?>>()
    val doLoginEvent = MutableLiveData<Event<LoginRequestModel>>()

    val emailText = MutableLiveData<String>().apply { value = "" }
    val passwordText = MutableLiveData<String>().apply { value = "" }

    private fun doLogin(loginRequestModel: LoginRequestModel) {
        Logger.v("--KK-- Login", "Start")
        compositeDisposable.add(
            apiRepository
                .doLogin(loginRequestModel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ userList ->
                    if(userList.email!=null) {
                        appPreferences.saveuserCredential(loginRequestModel.getJSON())
                        Logger.v("--KK-- Login", "Done" + userList)
                        loginResponse.postValue(Event(userList))
                    }else{
                        loginResponse.postValue(Event(null))
                    }
                }, { throwable ->
                    Logger.e("--KK-- Login", "Error" + throwable.message)
                    loginResponse.postValue(Event(null))
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
        var errorMessage: String? = null
        if (isEmpty(emailText)) {
            errorMessage = getString(R.string.error_email_empty)
        } else if (!StringUtils.isEmailValid(emailText.value ?: "")) {
            errorMessage = getString(R.string.error_email_invalid)
        } else if (isEmpty(passwordText)) {
            errorMessage = getString(R.string.error_password_empty)
        } else if (passwordText.value?.length!! < 6) {
            errorMessage = getString(R.string.error_password_length)
        }
        if (errorMessage != null) {
            Toast.makeText(application.baseContext, errorMessage, Toast.LENGTH_SHORT).show()
        } else {
            var requestModel = LoginRequestModel(emailText.value, passwordText.value)
            doLogin(requestModel)
        }
    }

    private fun getString(resourceId: Int): String {
        return application.resources.getString(resourceId)
    }

    private fun isEmpty(field: MutableLiveData<String>): Boolean {
        return StringUtils.isEmpty(field.value)
    }


    fun signUpClicked() {
        navigationToEvent.postValue(Event(NavigationTarget.REGISTRATION_SCREEN))
    }

    fun forgotPasswordClicked() {
//        navigationToEvent.postValue(Event(NavigationTarget.FORGOT_PASSWORD_SCREEN))
    }

    class Factory @Inject
    constructor(private val appPreferences: AppPreferences, private val application: BaseApplication, private val apiHelper: ApiHelper) : ViewModelProvider.AndroidViewModelFactory(application) {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
                return LoginViewModel(appPreferences, application, APIRepository(apiHelper)) as T
            }
            throw IllegalArgumentException("Unknown class name")
        }

    }
}

