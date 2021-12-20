package com.kk.nkart.data.api

import com.kk.jet2articalassignment.data.api.ApiService
import com.kk.jet2articalassignment.data.api.NetworkClient
import com.kk.jet2articalassignment.data.models.ArticleInfo
import com.kk.nkart.data.models.UserModel
import io.reactivex.Observable

class ApiServiceImpl : ApiService {

    override fun getArticles(): Observable<List<ArticleInfo>> {
        return NetworkClient.getClient()?.getArticles() ?: Observable.just(listOf())
    }

    override fun doLogin(bodyData : String): Observable<UserModel> {
        return NetworkClient.getClient()?.doLogin(bodyData) ?: Observable.just(null)
    }

}