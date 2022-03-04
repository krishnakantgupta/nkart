package com.kk.nkart.data.models

import com.google.gson.annotations.SerializedName

data class DashboardToImageModel(
    @SerializedName("url")
    var iamgeUrl: String = "",

    @SerializedName("isProductListing")
    var isProductListing: Boolean = true,

    @SerializedName("productId")
    var productId: Int = -1,

    @SerializedName("subCategoryId")
    var subCategoryId: Int = -1
)