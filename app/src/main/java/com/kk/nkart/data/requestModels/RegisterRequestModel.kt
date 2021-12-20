package com.kk.nkart.data.requestModels

import com.google.gson.Gson

data class RegisterRequestModel(var username: String? = null,
                                var password: String? = null,
                                var salutation: String? = "",
                                var firstName: String? = null,
                                var lastName: String? = null,
                                var email: String? = null,
                                var mobile: String? = null){
    fun getJSON():String{
        return  Gson().toJson(this)
    }
}
