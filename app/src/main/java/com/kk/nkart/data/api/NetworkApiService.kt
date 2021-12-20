package com.kk.jet2articalassignment.data.api

import com.kk.jet2articalassignment.data.models.ArticleInfo
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkApiService {
    @GET(APIConstants.GET_ARTICLE)
//    fun getArticles(@Query("page") pageNo: Int, @Query("limit") limit: Int =10 ): Observable<List<ArticleInfo>>
    fun getArticles(): Observable<List<ArticleInfo>>
}