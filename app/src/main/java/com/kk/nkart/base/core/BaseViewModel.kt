package com.kk.nkart.base.core

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class BaseViewModel: ViewModel() {
    open val backEvent = MutableLiveData<Boolean>()
    fun goBack(){
        backEvent.postValue(true)
    }
}