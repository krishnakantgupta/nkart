package com.kk.nkart.data.api

import com.kk.jet2articalassignment.data.api.ApiHelper
import com.kk.jet2articalassignment.data.models.ArticleInfo
import com.kk.nkart.data.models.CategoryModel
import com.kk.nkart.data.models.UserModel
import io.reactivex.Observable
import javax.inject.Inject

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

}