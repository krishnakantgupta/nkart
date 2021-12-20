package com.kk.nkart.utils

import android.content.Context
import android.content.Intent
import android.os.Parcelable
import com.kk.nkart.data.models.BaseModel

object IntentUtils {

    fun nextScreen(context: Context, cls: Class<*>?) {
        context.startActivity(Intent(context, cls))
    }

    fun nextScreenWithParam(context: Context, cls: Class<*>?, map: HashMap<String, Any>) {
        var intent = Intent(context, cls)
        for (element in map) {
            when (element.value) {
                is String -> intent.putExtra(element.key as String, element.value as String)
                is Int -> intent.putExtra(element.key as String, element.value as Int)
                is Long -> intent.putExtra(element.key as String, element.value as Long)
                is Double -> intent.putExtra(element.key as String, element.value as Double)
                is BaseModel -> intent.putExtra(element.key as String, element.value as Parcelable)
            }
        }

        context.startActivity(intent)
    }

    private fun getType(value: Any) {
        when (value) {
            is String -> String
            is Int -> Int
            is Long -> Long
            is Double -> Double
        }
    }
}