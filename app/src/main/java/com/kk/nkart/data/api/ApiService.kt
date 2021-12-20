package com.kk.jet2articalassignment.data.api

import com.kk.jet2articalassignment.data.models.ArticleInfo
import io.reactivex.Observable

interface ApiService {
    fun getArticles(): Observable<List<ArticleInfo>>
}