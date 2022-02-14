package com.kk.nkart.data.models

data class WishListModel(
    val productId: Int = -1,
    val title: String = "",
    val shortDesc: String = "",
    val categoryId: Int = -1,
    val subCategoryId: Int = -1,
    val quantity: Int = 0,
    val price: Double = 0.0,
    val discount: Double = 0.0,
    val thumbnailUrl: String? = null,
) {}