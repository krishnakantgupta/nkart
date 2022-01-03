package com.kk.nkart.data.models

import com.google.gson.annotations.SerializedName

data class DashboardModel(
    @SerializedName("topImages")
    var topImages: List<DashboardToImageModel>? = null,

    @SerializedName("newCollection")
    var newCollectionList: List<ProductModel>? = null,

    @SerializedName("bestSelling")
    var bestSellingList: List<ProductModel>? = null,

    @SerializedName("recentView")
    var recentViewList: List<ProductModel>? = null,

    @SerializedName("dealOfTheDay")
    var dealOfTheDayList: List<ProductModel>? = null
)