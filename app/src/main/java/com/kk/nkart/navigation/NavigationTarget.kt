/*
 * Copyright © 2017 Nedbank. All rights reserved.
 */
package com.kk.nkart.navigation

import android.os.Bundle
import android.os.Parcelable
import java.io.Serializable
import java.util.*

class NavigationTarget private constructor(val target: String) {

    companion object {
        const val DASHBOARD_SCREEN = "dashboard_screen"
        const val LOGIN_SCREEN = "login_screen"
        const val REGISTRATION_SCREEN = "registration_screen"
        const val FORGOT_PASSWORD_SCREEN = "forgot_password_screen"
        const val RESET_PASSWORD_SCREEN = "reset_password_screen"
        const val CATEGORY_SCREEN = "category_screen"
        const val PLP_SCREEN = "plp_screen"
        const val PDP_SCREEN = "pdp_screen"
        const val CART_SCREEN = "cart_screen"
        const val WISHLIST_SCREEN = "wishlist_screen"
        const val GALLERY_SCREEN = "gallery_screen"
        const val ORDER_VIEW_SCREEN = "order_view_screen"
        const val ORDER_DETAILS_SCREEN = "order_details_screen"
        const val ADDRESS_VIEW_SCREEN = "address_view_screen"
        const val ADDRESS_DETAILS_SCREEN = "address_details_screen"
        const val ACCOUNT_SCREEN = "account_screen"

        fun to(target: String): NavigationTarget {
            return NavigationTarget(target)
        }
    }
    interface EXTRAS {
        companion object {
            const val PRODUCT_ID = "product_id"
            const val USER_ID = "user_id"
            const val PARAM_ISLOGGIN = "param_isloggin"
            const val TEL = "tel:"
            const val KEY_USERNAME = "username"
        }
    }


    private val params: MutableMap<String, Any> = HashMap()
    var id: Long? = null
        private set
    private var clearStack = false
    private var intentFlagClearTopWithSingleTop = false
    private var intentFlagClearTop = false
    private var passAllData = false
    private var intentFlagNewTask = false
    fun withId(id: Long): NavigationTarget {
        this.id = id
        return this
    }

    fun withParam(name: String, value: String): NavigationTarget {
        params[name] = value
        return this
    }

    fun withParam(name: String, value: CharSequence): NavigationTarget {
        params[name] = value
        return this
    }

    fun withParam(name: String, value: Boolean): NavigationTarget {
        params[name] = value
        return this
    }

    fun withParam(name: String, value: Long): NavigationTarget {
        params[name] = value
        return this
    }

    fun withParam(name: String, value: Int): NavigationTarget {
        params[name] = value
        return this
    }

    fun withParam(name: String, value: Parcelable): NavigationTarget {
        params[name] = value
        return this
    }

    fun withParam(name: String, value: Double): NavigationTarget {
        params[name] = value
        return this
    }

    fun withParam(name: String, value: Serializable): NavigationTarget {
        params[name] = value
        return this
    }

    fun withParam(name: String, value: List<Parcelable?>): NavigationTarget {
        params[name] = value
        return this
    }

    fun withClearStack(clearStack: Boolean): NavigationTarget {
        this.clearStack = clearStack
        return this
    }

    fun withIntentFlagClearTopSingleTop(intentFlagClearTopSingleTop: Boolean): NavigationTarget {
        intentFlagClearTopWithSingleTop = intentFlagClearTopSingleTop
        return this
    }

    fun withIntentFlagClearTop(intentFlagClearTop: Boolean): NavigationTarget {
        this.intentFlagClearTop = intentFlagClearTop
        return this
    }

    fun withIntentFlagNewTask(intentFlagNewTask: Boolean): NavigationTarget {
        this.intentFlagNewTask = intentFlagNewTask
        return this
    }

    fun withAllData(withAllData: Boolean): NavigationTarget {
        passAllData = withAllData
        return this
    }

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

    fun getSerializableParam(name: String): Any {
        return getTypedValue(name, Any::class.java)!!
    }

    fun getParceableListParam(name: String): ParceableList {
        return getTypedValue(name, ParceableList::class.java)!!
    }

    fun getParams(): Bundle {
        val bundle = Bundle()
        putParams(bundle)
        return bundle
    }

    fun shouldClearStack(): Boolean {
        return clearStack
    }

    fun shouldClearTopWithSingleTop(): Boolean {
        return intentFlagClearTopWithSingleTop
    }

    fun shouldClearTop(): Boolean {
        return intentFlagClearTop
    }

    fun shouldNewTask(): Boolean {
        return intentFlagNewTask
    }

    fun shouldPassAllData(): Boolean {
        return passAllData
    }

    private fun putParams(bundle: Bundle) {
        for (key in params.keys) {
            val obj = params[key]
            if (obj is String) {
                bundle.putString(key, obj as String?)
            } else if (obj is CharSequence) {
                bundle.putCharSequence(key, obj as CharSequence?)
            } else if (obj is Long) {
                bundle.putLong(key, (obj as Long?)!!)
            } else if (obj is Int) {
                bundle.putInt(key, (obj as Int?)!!)
            } else if (obj is ParceableList) {
                bundle.putParcelableArrayList(key, obj as ArrayList<out Parcelable?>?)
            } else if (obj is Parcelable) {
                bundle.putParcelable(key, obj as Parcelable?)
            } else if (obj is Boolean) {
                bundle.putBoolean(key, (obj as Boolean?)!!)
            } else if (obj is Serializable) {
                bundle.putSerializable(key, obj as Serializable?)
            }
        }
    }

    private fun <T> getTypedValue(key: String, tClass: Class<T>): T? {
        val value = params[key]
        return if (tClass.isAssignableFrom(value!!.javaClass)) {
            value as T?
        } else null
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) {
            return true
        }
        if (o == null || javaClass != o.javaClass) {
            return false
        }
        val that = o as NavigationTarget
        return target == that.target
    }

    override fun hashCode(): Int {
        return target.hashCode()
    }

    class ParceableList : ArrayList<Parcelable?>()



}