package com.kk.nkart.data.requestModels

import com.google.gson.Gson

data class LoginRequestModel(var username: String? = null,
                             var password: String? = null){
    fun getJSON():String{
        return  Gson().toJson(this)
    }
}