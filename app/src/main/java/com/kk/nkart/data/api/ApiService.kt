package com.kk.jet2articalassignment.data.api

import com.kk.jet2articalassignment.data.models.ArticleInfo
import com.kk.nkart.data.models.CategoryModel
import com.kk.nkart.data.models.UserModel
import io.reactivex.Observable

interface ApiService {

    fun getArticles(): Observable<List<ArticleInfo>>
    fun doLogin(bodyData : String): Observable<UserModel>
    fun getCategory() : Observable<List<CategoryModel>>
}