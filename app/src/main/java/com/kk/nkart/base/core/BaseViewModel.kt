package com.kk.nkart.base.core

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kk.nkart.base.BaseApplication
import com.kk.nkart.navigation.NavigationTarget
import io.reactivex.disposables.CompositeDisposable

open class BaseViewModel(application: BaseApplication): AndroidViewModel(application) {
    protected val compositeDisposable = CompositeDisposable()
    open val backEvent = MutableLiveData<Boolean>()
    val progressEvent = MutableLiveData<Event<Boolean>>()
    val navigationToEvent = MutableLiveData<Event<String>>()
    val goBack: () -> Unit = {
        backEvent.postValue(true)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}