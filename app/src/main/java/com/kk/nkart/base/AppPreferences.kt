package com.kk.nkart.base

import android.content.SharedPreferences
import javax.inject.Inject

class AppPreferences @Inject constructor(private val pref: SharedPreferences) {

    companion object {
        private const val KEY_FRESH_INSTALL = "key_fresh_install"
        private const val KEY_IS_LOGIN = "key_is_login"
        private const val KEY_IS_LOGIN_DETAILS = "key_is_login_details"
    }


    fun isFreshInstall(): Boolean {
        return pref.getBoolean(KEY_FRESH_INSTALL, true)
    }

    fun setAppAlreadyInUse() {
        pref.edit().apply { putBoolean(KEY_FRESH_INSTALL, false) }.apply()
    }

    fun isUserLogin(): Boolean {
        return pref.getBoolean(KEY_IS_LOGIN, false)
    }

    fun setUserLogin(isLogin: Boolean) {
        pref.edit().apply { putBoolean(KEY_IS_LOGIN, isLogin) }.apply()
    }

    fun saveuserCredential(details: String) {
        pref.edit().apply { putString(KEY_IS_LOGIN_DETAILS, details) }.apply()
    }

    fun logout() {
        pref.edit().apply {
            putBoolean(KEY_IS_LOGIN, false)
            remove(KEY_IS_LOGIN_DETAILS)
        }.apply()
    }

    fun getUserCredential(): String? {
        return pref.getString(KEY_IS_LOGIN_DETAILS, null)
    }


}