package com.kk.nkart.base

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject

class AppPreferences @Inject constructor(private val pref: SharedPreferences) {

    companion object {
        private const val KEY_FRESH_INSTALL = "key_fresh_install"
        private const val KEY_IS_LOGIN = "key_is_login"
    }


    fun isFreshInstall(): Boolean {
        return pref.getBoolean(Companion.KEY_FRESH_INSTALL, true)
    }

    fun setAppAlreadyInUse() {
        pref.edit().apply { putBoolean(Companion.KEY_FRESH_INSTALL, false) }.apply()
    }

    fun isUserLogin(): Boolean {
        return pref.getBoolean(Companion.KEY_IS_LOGIN, false)
    }

    fun setUserLogin(isLogin: Boolean) {
        pref.edit().apply { putBoolean(Companion.KEY_IS_LOGIN, isLogin) }.apply()
    }


}