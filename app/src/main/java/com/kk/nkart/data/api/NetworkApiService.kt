package com.kk.jet2articalassignment.data.api

import com.kk.jet2articalassignment.data.models.ArticleInfo
import com.kk.nkart.data.models.*
import com.kk.nkart.data.requestModels.LoginRequestModel
import io.reactivex.Observable
import retrofit2.http.*


interface NetworkApiService {
    //    fun getArticles(@Query("page") pageNo: Int, @Query("limit") limit: Int =10 ): Observable<List<ArticleInfo>>
    @GET(APIConstants.GET_ARTICLE)
    fun getArticles(): Observable<List<ArticleInfo>>

    @Headers("Content-Type: application/json")
    @POST(APIConstants.GET_LOGIN)
    fun doLogin(@Body loginRequestModel: LoginRequestModel): Observable<UserModel>

    @GET(APIConstants.GET_CATEGORY)
    fun getCategory(): Observable<List<CategoryModel>>


    @GET(APIConstants.GET_ADDRESS)
    fun getAddressList(@Query("userId") userId: Int): Observable<List<AddressModel>>

    @GET(APIConstants.GET_PRODUCT_LIST)
    fun getProductList(pageNumber: Int): Observable<List<ProductModel>>


    @GET(APIConstants.GET_PRODUCT_LIST_FOR_SUB_CATEGORY)
    fun getProductListForSubCategory(@Query("id") subcategoryId: Int): Observable<List<ProductModel>>


    @GET(APIConstants.GET_PRODUCT_DETAILS)
    fun getProductDetails(@Query("productId") productId: Int): Observable<ProductDetailsModel>

    @GET(APIConstants.GET_DASHBOARD)
    fun getDashboardData(@Query("userId") userId: Int): Observable<DashboardModel>

    @GET(APIConstants.GET_DASHBOARD)
    fun getDashboardData(): Observable<DashboardModel>

    @GET(APIConstants.GET_CART_LIST)
    fun getCartList(@Query("userId") userId: Int): Observable<List<CartModel>>

    @GET(APIConstants.GET_WISHLIST)
    fun getWishList(@Query("userId") userId: Int): Observable<List<WishListModel>>

    @GET(APIConstants.ADD_TO_CART)
    fun addToCart(@Query("productId") productId: Int, @Query("userId") userId: Int, @Query("sizeId") sizeId: Int, @Query("colorId") colorId: Int, @Query("quantity") quantity: Int): Observable<Boolean>

    @GET(APIConstants.DELETE_FROM_CART)
    fun deleteFromCart(@Query("productId") productId: Int, @Query("userId") userId: Int): Observable<Boolean>

    @GET(APIConstants.ADD_TO_WISHLIST)
    fun addToWishlist(@Query("productId") productId: Int, @Query("userId") userId: Int): Observable<Boolean>

    @GET(APIConstants.DELETE_FROM_WISHLIST)
    fun deleteFromWishlist(@Query("productId") productId: Int, @Query("userId") userId: Int): Observable<Boolean>

//    @GET(APIConstants.GET_WISHLIST)
//    fun getProductList(pageNumber: Int): Observable<List<ProductModel>>
//
//    @GET(APIConstants.GET_CART_LIST)
//    fun getProductList(pageNumber: Int): Observable<List<ProductModel>>
}