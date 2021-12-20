package com.kk.nkart.data.models

import com.kk.nkart.base.Constants

data class ProductModel(var productId: Int = -1,
                        var title: String = "",
                        var shortDesc: String = "",
                        var categoryId: Int = 1,
                        var subCategoryId: Int = -1,
                        var quantity: Int = 0,
                        var price: Double = 0.0,
                        var discount: Double = 0.0,
                        var createdDate: String? = null,
                        var lastUpdated: String? = null,
                        var thumbnailUrl: String? = null) {
    fun getFormattedDate(date: String) {
        Constants.PRODUCT_DATE_FORMATTER
    }
}