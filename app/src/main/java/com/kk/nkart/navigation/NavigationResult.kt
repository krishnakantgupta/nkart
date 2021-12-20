/*
 * Copyright © 2017 Nedbank. All rights reserved.
 */
package com.kk.nkart.navigation

import android.content.Intent
import android.app.Activity
import android.os.Bundle
import android.os.Parcelable
import java.util.HashMap

open class NavigationResult {
    private val params: MutableMap<String, Any?> = HashMap()
    var isOk = false
        private set
    var resultCode = 0
        private set

    constructor(isOK: Boolean, params: Map<String, Any?>?) {
        isOk = isOK
        this.params.putAll(params!!)
    }

    constructor(resultCode: Int, data: Intent?) {
        isOk = resultCode == Activity.RESULT_OK
        this.resultCode = resultCode
        if (data != null) {
            val extras = data.extras
            if (extras != null) {
                val keys = extras.keySet()
                for (key in keys) {
                    val value = extras[key]
                    params[key] = value
                }
            }
        }
    }

    constructor() {}

    fun getStringParam(name: String): String {
        return getTypedValue(name, String::class.java)!!
    }

    fun getLongParam(name: String): Long {
        return getTypedValue(name, Long::class.java)!!
    }

    fun getIntParam(name: String): Int {
        return getTypedValue(name, Int::class.java)!!
    }

    fun getBooleanParam(name: String): Boolean {
        return getTypedValue(name, Boolean::class.java)!!
    }

    fun getParcelableParam(name: String): Parcelable {
        return getTypedValue(name, Parcelable::class.java)!!
    }

    private fun <T> getTypedValue(key: String, tClass: Class<T>): T? {
        val value = params[key] ?: return null
        return if (tClass.isAssignableFrom(value.javaClass)) {
            value as T
        } else null
    }

    fun getParams(): Map<String, Any?> {
        return params
    }
}