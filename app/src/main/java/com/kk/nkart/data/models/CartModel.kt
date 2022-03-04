package com.kk.nkart.data.models

import android.graphics.Color
import com.google.gson.annotations.SerializedName

data class CartModel(
    val productId: Int = -1,
    val title: String = "",
    val description: String = "",
    val quantity: Int = 0,
    val imageId: String = "",
    val price: Double = 0.0,
    val discount: Double = 0.0,
    @SerializedName("selectedProductColour")
    val selectedProductColour: ProductColour? = null,
    @SerializedName("selectedProductSize")
    val selectedProductSize: ProductSize? = null,

    ) {
    fun getSize(): String? {
        return selectedProductSize?.let { it.sizeValue }
    }

    fun getColor(): Int? {
        return selectedProductColour?.let { Color.parseColor(it.colourCode) }
    }
}