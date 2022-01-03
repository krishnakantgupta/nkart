package com.kk.jet2articalassignment.data.api

import javax.inject.Inject

class ApiHelper @Inject constructor(private val apiService: ApiService) {

    fun getArticles() = apiService.getArticles()

    fun doLogin(bodyData : String) = apiService.doLogin(bodyData)

    fun getCategory() = apiService.getCategory()

    fun getProductList(pageNumber : Int) = apiService.getProductList(pageNumber)

    fun getAddressList(userID: Int) =  apiService.getAddressList(userID)

    fun getProductDetails(productId: Int) =  apiService.getProductDetails(productId)

    fun getDashboardData(userID: Int) =  apiService.getDashboardData(userID)
}