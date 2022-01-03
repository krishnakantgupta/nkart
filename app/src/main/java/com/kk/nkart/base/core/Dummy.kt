package com.kk.nkart.base.core

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.kk.nkart.base.BaseApplication
import com.kk.nkart.data.models.*
import com.kk.nkart.utils.CommonUtils

object Dummy {
    fun getCategoryJSON(): List<CategoryModel> {
        var inputStream = BaseApplication.appContext.assets.open("category.json")
        var json = CommonUtils.streamToString(inputStream)
        var categoryList = Gson().fromJson<List<CategoryModel>>(json, object : TypeToken<List<CategoryModel>>() {}.type)
        return categoryList
    }

    fun getProductJSON(): List<ProductModel> {
        var inputStream = BaseApplication.appContext.assets.open("product.json")
        var json = CommonUtils.streamToString(inputStream)
        var productList = Gson().fromJson<List<ProductModel>>(json, object : TypeToken<List<ProductModel>>() {}.type)
        return productList
    }

    fun getProductDetailsJSON(): ProductDetailsModel {
        var inputStream = BaseApplication.appContext.assets.open("product_detials.json")
        var json = CommonUtils.streamToString(inputStream)
        var productList = Gson().fromJson(json, ProductDetailsModel::class.java)
        return productList
    }

    fun getAddressJSON(): List<AddressModel> {
        var inputStream = BaseApplication.appContext.assets.open("address.json")
        var json = CommonUtils.streamToString(inputStream)
        var addressList = Gson().fromJson<List<AddressModel>>(json, object : TypeToken<List<AddressModel>>() {}.type)
        return addressList
    }

    fun getDashboardJSON(): DashboardModel {
        var inputStream = BaseApplication.appContext.assets.open("dashboard.json")
        var json = CommonUtils.streamToString(inputStream)
        var dashboardData = Gson().fromJson(json, DashboardModel::class.java)
        return dashboardData
    }
}