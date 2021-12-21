package com.kk.nkart.base.core

import android.app.Activity
import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.kk.nkart.base.BaseApplication
import com.kk.nkart.data.models.CategoryModel
import com.kk.nkart.utils.CommonUtils

object Dummy {
    fun getCategoryJSON(): List<CategoryModel>  {
        var inputStream = BaseApplication.appContext.assets.open("category.json")
        var json = CommonUtils.streamToString(inputStream)
        var categoryList = Gson().fromJson<List<CategoryModel>>(json, object : TypeToken<List<CategoryModel>>() {}.type)
        return categoryList
    }
}