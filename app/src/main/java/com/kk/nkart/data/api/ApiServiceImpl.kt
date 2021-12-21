package com.kk.nkart.data.api

import com.kk.jet2articalassignment.data.api.ApiService
import com.kk.jet2articalassignment.data.api.NetworkClient
import com.kk.jet2articalassignment.data.models.ArticleInfo
import com.kk.nkart.base.core.Dummy
import com.kk.nkart.data.models.CategoryModel
import com.kk.nkart.data.models.UserModel
import io.reactivex.Observable

class ApiServiceImpl : ApiService {

    override fun getArticles(): Observable<List<ArticleInfo>> {
        return NetworkClient.getClient()?.getArticles() ?: Observable.just(listOf())
    }


    override fun doLogin(bodyData : String): Observable<UserModel> {
        return NetworkClient.getClient()?.doLogin(bodyData) ?: Observable.just(null)
    }

    override fun getCategory(): Observable<List<CategoryModel>> {
        return  Observable.just(Dummy.getCategoryJSON()) //NetworkClient.getClient()?.getCategory() ?: Observable.just(listOf())
    }
}