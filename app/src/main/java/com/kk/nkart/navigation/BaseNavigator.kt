/*
 * Copyright © 2017 Nedbank. All rights reserved.
 */
package com.kk.nkart.navigation

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import com.kk.nkart.base.core.BaseActivity
import io.reactivex.Observable
import java.io.Serializable
import java.util.*

abstract class BaseNavigator : INavigator {
    private val navigationRoutes: MutableList<NavigationRoute> = ArrayList()
    protected fun forTarget(target: String): NavigationRoute {
        val route = NavigationRoute(target)
        navigationRoutes.add(route)
        return route
    }

    override fun navigateTo(navigationTarget: NavigationTarget, activity: Activity): Boolean {
        for (navigationRoute in navigationRoutes) {
            if (navigationRoute.matches(navigationTarget)) {
                navigationRoute.navigateTo(navigationTarget, activity)
                return true
            }
        }
        return false
    }

    override fun navigateWithResult(navigationTarget: NavigationTarget, activity: Activity): Observable<NavigationResult>? {
        for (navigationRoute in navigationRoutes) {
            if (navigationRoute.matches(navigationTarget)) {
                return navigationRoute.navigateWithResult(navigationTarget, activity)
            }
        }
        return null
    }

    protected inner class NavigationRoute(private val target: String) {
        private var activityClass: Class<out Activity>? = null
        private var useId = false
        private var idExtraKey: String? = null
        private val params: MutableMap<String, Any> = HashMap()
        fun withId(): NavigationRoute {
            useId = true
            return this
        }

        fun openActivity(activityClass: Class<out Activity>?): NavigationRoute {
            this.activityClass = activityClass
            return this
        }

        fun putIdAsExtra(key: String?): NavigationRoute {
            idExtraKey = key
            return this
        }

        fun withParam(name: String, value: String): NavigationRoute {
            params[name] = value
            return this
        }

        fun withParam(name: String, value: Boolean): NavigationRoute {
            params[name] = value
            return this
        }

        fun withParam(name: String, value: Long): NavigationRoute {
            params[name] = value
            return this
        }

        fun withParam(name: String, value: Int): NavigationRoute {
            params[name] = value
            return this
        }

        fun withParam(name: String, value: Parcelable): NavigationRoute {
            params[name] = value
            return this
        }

        fun withParam(name: String, value: Serializable): NavigationRoute {
            params[name] = value
            return this
        }

        fun matches(navigationTarget: NavigationTarget): Boolean {
            return navigationTarget.target == target
        }

        fun navigateTo(navigationTarget: NavigationTarget, activity: Activity) {
            val intent = createIntent(navigationTarget, activity)
            activity.startActivity(intent)
        }

        fun putParams(bundle: Bundle) {
            for (key in params.keys) {
                val obj = params[key]
                if (obj is String) {
                    bundle.putString(key, obj as String?)
                } else if (obj is Long) {
                    bundle.putLong(key, (obj as Long?)!!)
                } else if (obj is Int) {
                    bundle.putInt(key, (obj as Int?)!!)
                } else if (obj is Parcelable) {
                    bundle.putParcelable(key, obj as Parcelable?)
                } else if (obj is Boolean) {
                    bundle.putBoolean(key, (obj as Boolean?)!!)
                } else if (obj is Serializable) {
                    bundle.putSerializable(key, obj as Serializable?)
                }
            }
        }

        fun getParams(): Bundle {
            val bundle = Bundle()
            putParams(bundle)
            return bundle
        }

        fun navigateWithResult(navigationTarget: NavigationTarget, activity: Activity): Observable<NavigationResult> {
            val baseActivity = activity as BaseActivity
            val intent = createIntent(navigationTarget, activity)
            return baseActivity.startActivityForResult(intent)?.map{ activityResult -> NavigationResult(activityResult.first, activityResult.second)}!!
        }

        fun createIntent(navigationTarget: NavigationTarget, activity: Activity): Intent {
            val intent = Intent(activity, activityClass)
            if (useId && navigationTarget.id != null && idExtraKey != null) {
                intent.putExtra(idExtraKey, navigationTarget.id)
            }
            if (navigationTarget.shouldPassAllData()) {
                val previousIntent = activity.intent
                if (previousIntent != null) {
                    intent.putExtras(previousIntent)
                }
            }
            intent.putExtras(navigationTarget.getParams())
            intent.putExtras(getParams())
            if (navigationTarget.shouldNewTask()) {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
            }
            if (navigationTarget.shouldClearStack()) {
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            }
            if (navigationTarget.shouldClearTopWithSingleTop()) {
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
            }
            if (navigationTarget.shouldClearTop()) {
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            }
            return intent
        }
    }
}