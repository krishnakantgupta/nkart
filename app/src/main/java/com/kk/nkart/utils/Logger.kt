/*
 * Copyright © 2017 Nedbank. All rights reserved.
 */
package com.kk.nkart.utils

import android.util.Log

/**
 * Created by priyadhingra on 12/5/2017.
 */
object Logger {
    private const val requiredLog = true
    fun d(tag: String, value: String) {
        log(Log.DEBUG, tag, value, null)
    }

    fun e(tag: String, value: String) {
        log(Log.ERROR, tag, value, null)
    }

    fun e(tag: String, value: String, e: Throwable?) {
        log(Log.ERROR, tag, value, e)
    }

    fun i(tag: String, value: String) {
        log(Log.INFO, tag, value, null)
    }

    fun i(tag: String, value: String, e: Throwable?) {
        log(Log.INFO, tag, value, e)
    }

    fun v(tag: String, value: String) {
        log(Log.VERBOSE, tag, value, null)
    }

    fun w(tag: String, value: String) {
        log(Log.WARN, tag, value, null)
    }

    private fun log(level: Int, tag: String, value: String, e: Throwable?) {
        if (!requiredLog) {
            return
        }
        when (level) {
            Log.DEBUG -> Log.d(tag, value, e)
            Log.ERROR -> Log.e(tag, value, e)
            Log.INFO -> Log.i(tag, value, e)
            Log.VERBOSE -> Log.v(tag, value, e)
            Log.WARN -> Log.w(tag, value, e)
        }
    }
}