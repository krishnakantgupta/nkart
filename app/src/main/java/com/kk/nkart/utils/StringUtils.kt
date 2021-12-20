package com.kk.nkart.utils

object StringUtils {
    fun getDiscountedPrice(price: Double, discount: Double): Double {
        return (price * (discount / 100))
    }
}