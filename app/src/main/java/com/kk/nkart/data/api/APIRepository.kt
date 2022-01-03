package com.kk.nkart.data.api

import com.kk.jet2articalassignment.data.api.ApiHelper
import com.kk.jet2articalassignment.data.models.ArticleInfo
import com.kk.nkart.data.models.*
import io.reactivex.Observable

class APIRepository(private val apiHelper: ApiHelper) {

    fun getArticles(): Observable<List<ArticleInfo>> {
        return apiHelper.getArticles()
    }

    fun doLogin(bodyData: String): Observable<UserModel> {
        return apiHelper.doLogin(bodyData)
    }

    fun getCategory(): Observable<List<CategoryModel>> {
        return apiHelper.getCategory()
    }

    fun getProductList(pageNumber: Int): Observable<List<ProductModel>> {
        return apiHelper.getProductList(pageNumber)
    }

    fun getAddressList(userID: Int): Observable<List<AddressModel>> {
        return apiHelper.getAddressList(userID)
    }

    fun getProductDetails(productId: Int): Observable<ProductDetailsModel> {
        return apiHelper.getProductDetails(productId)
    }

    fun getDashboardData(userID: Int):Observable<DashboardModel>{
        return apiHelper.getDashboardData(userID)
    }

}