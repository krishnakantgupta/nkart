package com.kk.nkart.data.api

import com.kk.jet2articalassignment.data.api.ApiHelper
import com.kk.jet2articalassignment.data.models.ArticleInfo
import com.kk.jet2articalassignment.data.models.UserInfo
import io.reactivex.Observable

class APIRepository(private val apiHelper: ApiHelper) {

    fun getArticles(): Observable<List<ArticleInfo>> {
        return apiHelper.getArticles()
    }
}