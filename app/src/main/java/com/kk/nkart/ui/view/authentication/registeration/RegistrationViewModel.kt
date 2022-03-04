package com.kk.nkart.ui.view.authentication.registeration

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import com.kk.jet2articalassignment.data.api.ApiHelper
import com.kk.jet2articalassignment.data.models.ArticleInfo
import com.kk.nkart.R
import com.kk.nkart.base.BaseApplication
import com.kk.nkart.base.core.BaseViewModel
import com.kk.nkart.base.core.Event
import com.kk.nkart.data.api.APIRepository
import com.kk.nkart.data.models.UserModel
import com.kk.nkart.data.requestModels.LoginRequestModel
import com.kk.nkart.data.requestModels.RegisterRequestModel
import com.kk.nkart.utils.Logger
import com.kk.nkart.utils.StringUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class RegistrationViewModel @Inject constructor(val application: BaseApplication, private val apiRepository: APIRepository) : BaseViewModel(application) {
    val registerResponse = MutableLiveData<Event<UserModel?>>()
    val doRegisterEvent = MutableLiveData<Event<RegisterRequestModel>>()

    val firstNameText = MutableLiveData<String>().apply { value = "" }
    val lastNameText = MutableLiveData<String>().apply { value = "" }
    val emailText = MutableLiveData<String>().apply { value = "" }
    val mobileText = MutableLiveData<String>().apply { value = "" }
    val passwordText = MutableLiveData<String>().apply { value = "12345678" }
    val confirmPasswordText = MutableLiveData<String>().apply { value = "12345678" }


    fun firstNameTextChanged(s: CharSequence, start: kotlin.Int, before: kotlin.Int, count: kotlin.Int) {
        firstNameText.value = s.toString()
    }

    fun lastNameTextChanged(s: CharSequence, start: kotlin.Int, before: kotlin.Int, count: kotlin.Int) {
        lastNameText.value = s.toString()
    }

    fun emailTextChanged(s: CharSequence, start: kotlin.Int, before: kotlin.Int, count: kotlin.Int) {
        emailText.value = s.toString()
    }

    fun passwordTextChanged(s: CharSequence, start: kotlin.Int, before: kotlin.Int, count: kotlin.Int) {
        passwordText.value = s.toString()
    }

    fun confirmPasswordTextChanged(s: CharSequence, start: kotlin.Int, before: kotlin.Int, count: kotlin.Int) {
        confirmPasswordText.value = s.toString()
    }

    fun mobileTextChanged(s: CharSequence, start: kotlin.Int, before: kotlin.Int, count: kotlin.Int) {
        mobileText.value = s.toString()
    }

    fun registerClick() {
        var errorMessage: String? = null
        if (isEmpty(firstNameText)) {
            errorMessage = getString(R.string.error_firstname)
        } else if (isEmpty(lastNameText)) {
            errorMessage = getString(R.string.error_lastname)
        } else if (isEmpty(emailText)) {
            errorMessage = getString(R.string.error_email_empty)
        }  else if (StringUtils.isEmailValid(emailText.value?:"")) {
            errorMessage = getString(R.string.error_email_invalid)
        }else if (isEmpty(mobileText)) {
            errorMessage = getString(R.string.error_mobile_empty)
        } else if (isEmpty(passwordText)) {
            errorMessage = getString(R.string.error_password_empty)
        } else if (isEmpty(confirmPasswordText)) {
            errorMessage = getString(R.string.error_confirm_password_empty)
        } else if (!passwordText.value.equals(confirmPasswordText.value)) {
            errorMessage = getString(R.string.error_password_not_match)
        } else if (passwordText.value?.length!! < 6) {
            errorMessage = getString(R.string.error_password_length)
        }
        if (errorMessage != null) {
            Toast.makeText(application.baseContext, errorMessage,Toast.LENGTH_SHORT).show()
        } else {
            doRegister()
        }
    }
    private fun getString(resourceId:Int):String{
        return application.resources.getString(resourceId)
    }

    private fun doRegister() {
        var registerRequestModel = RegisterRequestModel(emailText.value, passwordText.value, salutation = "", firstNameText.value, lastNameText.value, emailText.value, mobileText.value)
        var bodyRequest = Gson().toJson(registerRequestModel)
        compositeDisposable.add(
            apiRepository
                .doRegister(bodyRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ userList ->
//                    AppMemory.userModel = userList
                    Logger.v("--KK-- doRegister", "Done" + userList)
                    registerResponse.postValue(Event(userList))
                }, { throwable ->
                    Logger.e("--KK-- doRegister", "Error" + throwable.message)
                    registerResponse.postValue(Event(null))
                })
        )
    }

    private fun isEmpty(field: MutableLiveData<String>): Boolean {
        return StringUtils.isEmpty(field.value)
    }


    class Factory @Inject
    constructor(private val application: BaseApplication, private val apiHelper: ApiHelper) : ViewModelProvider.AndroidViewModelFactory(application) {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(RegistrationViewModel::class.java)) {
                return RegistrationViewModel(application, APIRepository(apiHelper)) as T
            }
            throw IllegalArgumentException("Unknown class name")
        }

    }
}