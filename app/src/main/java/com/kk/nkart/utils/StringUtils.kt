package com.kk.nkart.utils

object StringUtils {
    fun getDiscountedPrice(price: Double, discount: Double): Double {
        return (price * (discount / 100))
    }
    fun isNotEmptyOrNotNull(value :String?):Boolean{
        return value?.trim()?.isNotEmpty() ?: false
    }
    fun isEmpty(value :String?):Boolean{
        return value == null || value.trim().isEmpty()
    }
}