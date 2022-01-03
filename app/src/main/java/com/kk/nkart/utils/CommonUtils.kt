package com.kk.nkart.utils

import android.R.string
import com.kk.nkart.base.Constants
import java.io.IOException
import java.io.InputStream
import java.text.SimpleDateFormat


object CommonUtils {
    var productDateFormatter = SimpleDateFormat(Constants.PRODUCT_DATE_FORMATTER)
    fun getDateForProduct(date: String): String {
        return date
    }

    fun streamToString(inputStream:InputStream):String{
        var data = ""
        try {
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            data = String(buffer)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return data
    }
}

val size =
val buffer =

data = String(buffer)