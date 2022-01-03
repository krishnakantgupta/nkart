package com.kk.jet2articalassignment.data.api

import com.kk.jet2articalassignment.data.models.ArticleInfo
import com.kk.nkart.data.models.*
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query


interface NetworkApiService {
    //    fun getArticles(@Query("page") pageNo: Int, @Query("limit") limit: Int =10 ): Observable<List<ArticleInfo>>
    @GET(APIConstants.GET_ARTICLE)
    fun getArticles(): Observable<List<ArticleInfo>>

    @POST(APIConstants.GET_LOGIN)
    fun doLogin(@Body bodyData: String): Observable<UserModel>

    @GET(APIConstants.GET_CATEGORY)
    fun getCategory(): Observable<List<CategoryModel>>


    @GET(APIConstants.GET_ADDRESS)
    fun getAddressList(@Query("userId") userId: Int): Observable<List<AddressModel>>

    @GET(APIConstants.GET_PRODUCT_LIST)
    fun getProductList(pageNumber: Int): Observable<List<ProductModel>>

    @GET(APIConstants.GET_DASHBOARD)
    fun getDashboardData(@Query("userId")userId: Int): Observable<DashboardModel>


//    @GET(APIConstants.GET_WISHLIST)
//    fun getProductList(pageNumber: Int): Observable<List<ProductModel>>
//
//    @GET(APIConstants.GET_CART_LIST)
//    fun getProductList(pageNumber: Int): Observable<List<ProductModel>>
}