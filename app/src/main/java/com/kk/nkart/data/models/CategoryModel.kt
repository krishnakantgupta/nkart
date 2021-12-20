package com.kk.nkart.data.models

import com.google.gson.annotations.SerializedName

data class CategoryModel(

    @SerializedName("categoryId")
    var categoryId: Int = -1,

    @SerializedName("categoryName")
    var categoryName: String? = null,

    @SerializedName("subCategories")
    var subCategoriesList: List<SubCategoryModel>? = null
)
