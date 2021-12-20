package com.kk.jet2articalassignment.data.api

import com.kk.jet2articalassignment.data.models.ArticleInfo
import com.kk.nkart.data.models.UserModel
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST


interface NetworkApiService {
    //    fun getArticles(@Query("page") pageNo: Int, @Query("limit") limit: Int =10 ): Observable<List<ArticleInfo>>
    @GET(APIConstants.GET_ARTICLE)
    fun getArticles(): Observable<List<ArticleInfo>>

    @POST(APIConstants.GET_LOGIN)
    fun doLogin(@Body bodyData:String): Observable<UserModel>

}