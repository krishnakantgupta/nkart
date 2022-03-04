package com.kk.nkart.data.models

import com.google.gson.annotations.SerializedName

data class ProductDetailsModel(
    var productId: Int = -1,
    var title: String = "",
    var shortDesc: String = "",
    var description: String = "",
    var imageId: String = "",
    @SerializedName("productSize")
    var productSize:List<ProductSize>?=null,
    @SerializedName("productColour")
    var productColour:List<ProductColour>?=null,
    var categoryId: Int = 1,
    var subCategoryId: Int = -1,
    var quantity: Int = 0,
    var price: Double = 0.0,
    var isFavourite: Boolean = false,
    var discount: Double = 0.0,
    var createdDate: String? = null,
    var lastUpdated: String? = null,
    var thumbnailUrl: String? = null) {

    fun copyDataFromProduct(product:ProductModel){
        shortDesc = product.shortDesc
        categoryId = product.categoryId
        subCategoryId = product.subCategoryId
        thumbnailUrl = product.thumbnailUrl
        createdDate = product.createdDate
        lastUpdated  = product.lastUpdated
        isFavourite = product.isFavourite
    }
}