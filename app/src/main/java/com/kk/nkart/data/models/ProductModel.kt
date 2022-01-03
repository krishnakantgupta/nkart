package com.kk.nkart.data.models

import android.os.Parcelable
import com.kk.nkart.base.Constants
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ProductModel(var productId: Int = -1,
                        var title: String = "",
                        var shortDesc: String = "",
                        var categoryId: Int = 1,
                        var subCategoryId: Int = -1,
                        var quantity: Int = 0,
                        var price: Double = 0.0,
                        var isFavourite: Boolean = false,
                        var discount: Double = 0.0,
                        var createdDate: String? = null,
                        var lastUpdated: String? = null,
                        var thumbnailUrl: String? = null): Parcelable {
    fun getFormattedDate(date: String) {
        Constants.PRODUCT_DATE_FORMATTER
    }
}