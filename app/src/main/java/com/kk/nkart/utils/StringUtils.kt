package com.kk.nkart.utils

import java.util.regex.Pattern

object StringUtils {
    val EMAIL_ADDRESS_PATTERN: Pattern = Pattern.compile("^(.+)@(\\S+)$")

    fun getDiscountedPrice(price: Double, discount: Double): Double {
        return (price * (discount / 100))
    }
    fun isNotEmptyOrNotNull(value :String?):Boolean{
        return value?.trim()?.isNotEmpty() ?: false
    }
    fun isEmpty(value :String?):Boolean{
        return value == null || value.trim().isEmpty()
    }
    fun isEmailValid(value:String):Boolean{
        return  EMAIL_ADDRESS_PATTERN.matcher(value).matches()
    }
}