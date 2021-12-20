package com.kk.nkart.data.models

import com.google.gson.annotations.SerializedName

data class SubCategoryModel(
    @SerializedName("categoryId")
    var categoryId: Int = -1,

    @SerializedName("subCategoryId")
    var subCategoryId: Int = -1,

    @SerializedName("subCategoryName")
    var subCategoryName: String? = null
)