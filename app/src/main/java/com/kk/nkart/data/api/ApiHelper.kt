package com.kk.jet2articalassignment.data.api

import javax.inject.Inject

class ApiHelper @Inject constructor(private val apiService: ApiService) {

    fun getArticles() = apiService.getArticles()

}