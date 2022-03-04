package com.kk.nkart.data.api

import com.kk.jet2articalassignment.data.api.ApiHelper
import com.kk.jet2articalassignment.data.models.ArticleInfo
import com.kk.nkart.data.models.*
import com.kk.nkart.data.requestModels.LoginRequestModel
import io.reactivex.Observable

class APIRepository(private val apiHelper: ApiHelper) {

    fun getArticles(): Observable<List<ArticleInfo>> {
        return apiHelper.getArticles()
    }

    fun doLogin(bodyData: LoginRequestModel): Observable<UserModel> {
        return apiHelper.doLogin(bodyData)
    }


    fun doRegister(bodyData: String): Observable<UserModel> {
        return apiHelper.doRegister(bodyData)
    }

    fun getCategory(): Observable<List<CategoryModel>> {
        return apiHelper.getCategory()
    }

    fun getProductList(pageNumber: Int): Observable<List<ProductModel>> {
        return apiHelper.getProductList(pageNumber)
    }

    fun getProductListForSubCategory(subCategoryId: Int): Observable<List<ProductModel>> {
        return apiHelper.getProductListForSubCategory(subCategoryId)
    }

    fun getAddressList(userID: Int): Observable<List<AddressModel>> {
        return apiHelper.getAddressList(userID)
    }

    fun getProductDetails(productId: Int): Observable<ProductDetailsModel> {
        return apiHelper.getProductDetails(productId)
    }

    fun addToCart(productId: Int, userId: Int, sizeId: Int, colorId: Int, quantity: Int): Observable<Boolean> {
        return apiHelper.addToCart(productId, userId, sizeId, colorId, quantity)
    }

    fun deleteFromCart(productId: Int, userId: Int): Observable<Boolean> {
        return apiHelper.deleteFromCart(productId, userId)
    }

    fun addToWishlist(productId: Int, userId: Int): Observable<Boolean> {
        return apiHelper.addToWishlist(productId, userId)
    }
    fun deleteFromWishlist(productId: Int, userId: Int): Observable<Boolean> {
        return apiHelper.deleteFromWishlist(productId, userId)
    }

    fun getDashboardData(userID: Int): Observable<DashboardModel> {
        return apiHelper.getDashboardData(userID)
    }


    fun getCartList(userID: Int): Observable<List<CartModel>> {
        return apiHelper.getCartList(userID)
    }

    fun getWishList(userID: Int): Observable<List<WishListModel>> {
        return apiHelper.getWishList(userID)
    }
}