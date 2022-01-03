package com.kk.jet2articalassignment.data.api

import com.kk.jet2articalassignment.data.models.ArticleInfo
import com.kk.nkart.data.models.*
import io.reactivex.Observable

interface ApiService {

    fun getArticles(): Observable<List<ArticleInfo>>
    fun doLogin(bodyData : String): Observable<UserModel>
    fun getCategory() : Observable<List<CategoryModel>>
    fun getProductList(pageNumber:Int) : Observable<List<ProductModel>>
    fun getAddressList(userID: Int): Observable<List<AddressModel>>
    fun getProductDetails(productId: Int): Observable<ProductDetailsModel>
    fun getDashboardData(userID: Int):Observable<DashboardModel>

}