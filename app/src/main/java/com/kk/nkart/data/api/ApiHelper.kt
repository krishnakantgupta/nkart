package com.kk.jet2articalassignment.data.api

import com.kk.nkart.data.requestModels.LoginRequestModel
import javax.inject.Inject

class ApiHelper @Inject constructor(private val apiService: ApiService) {

    fun getArticles() = apiService.getArticles()

    fun doLogin(bodyData: LoginRequestModel) = apiService.doLogin(bodyData)

    fun doRegister(bodyData: String) = apiService.doRegister(bodyData)

    fun getCategory() = apiService.getCategory()

    fun getProductList(pageNumber: Int) = apiService.getProductList(pageNumber)

    fun getProductListForSubCategory(subCategoryId: Int) = apiService.getProductListForSubCategory(subCategoryId)

    fun getAddressList(userID: Int) = apiService.getAddressList(userID)

    fun getProductDetails(productId: Int) = apiService.getProductDetails(productId)

    fun getDashboardData(userID: Int) = apiService.getDashboardData(userID)

    fun getCartList(userID: Int) = apiService.getCartList(userID)

    fun getWishList(userID: Int) = apiService.getWishList(userID)

    fun addToCart(productId: Int, userId: Int, sizeId: Int, colorId: Int, quantity: Int) =
        apiService.addToCart(productId, userId, sizeId, colorId, quantity)

    fun deleteFromCart(productId: Int, userId: Int) = apiService.deleteFromCart(productId, userId)

    fun addToWishlist(productId: Int, userId: Int) = apiService.addToWishlist(productId, userId)

    fun deleteFromWishlist(productId: Int, userId: Int) = apiService.deleteFromWishlist(productId, userId)


}