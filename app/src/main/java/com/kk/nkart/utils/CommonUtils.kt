package com.kk.nkart.utils

import com.kk.nkart.base.Constants
import java.text.SimpleDateFormat

object CommonUtils {
    var productDateFormatter = SimpleDateFormat(Constants.PRODUCT_DATE_FORMATTER)
    fun getDateForProduct(date: String): String {
        return date
    }
}