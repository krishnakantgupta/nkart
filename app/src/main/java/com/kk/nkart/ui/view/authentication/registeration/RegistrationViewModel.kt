package com.kk.nkart.ui.view.authentication.registeration

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kk.jet2articalassignment.data.api.ApiHelper
import com.kk.jet2articalassignment.data.models.ArticleInfo
import com.kk.nkart.base.BaseApplication
import com.kk.nkart.base.core.BaseViewModel
import com.kk.nkart.base.core.Event
import com.kk.nkart.data.api.APIRepository
import com.kk.nkart.data.requestModels.LoginRequestModel
import com.kk.nkart.utils.StringUtils
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class RegistrationViewModel @Inject constructor(val application: BaseApplication, private val apiRepository: APIRepository) : BaseViewModel(application) {
    val registerResponse = MutableLiveData<Event<List<ArticleInfo>>>()
    val doRegisterEvent = MutableLiveData<Event<LoginRequestModel>>()

    val firstNameText = MutableLiveData<String>().apply { value = "" }
    val lastNameText = MutableLiveData<String>().apply { value = "" }
    val emailText = MutableLiveData<String>().apply { value = "" }
    val mobileText = MutableLiveData<String>().apply { value = "" }
    val passwordText = MutableLiveData<String>().apply { value = "" }
    val confirmPasswordText = MutableLiveData<String>().apply { value = "" }


    private val compositeDisposable = CompositeDisposable()

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
            errorMessage = ""
        } else if (isEmpty(lastNameText)) {
            errorMessage = ""
        } else if (isEmpty(emailText)) {
            errorMessage = ""
        } else if (isEmpty(mobileText)) {
            errorMessage = ""
        } else if (isEmpty(passwordText)) {
            errorMessage = ""
        } else if (isEmpty(confirmPasswordText)) {
            errorMessage = ""
        } else if (!passwordText.value.equals(confirmPasswordText.value)) {
            errorMessage = ""
        } else if (passwordText.value?.length!! < 6) {
            errorMessage = ""
        }
        if (errorMessage != null) {
            //show error popup
        } else {
            doRegister()
        }
    }

    private fun doRegister() {

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