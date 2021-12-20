/*
 * Copyright © 2017 Nedbank. All rights reserved.
 */
package com.kk.nkart.navigation

import android.app.Activity
import com.kk.nkart.utils.Logger
import io.reactivex.Observable
import java.util.*
import javax.inject.Inject

class NavigationRouter @Inject constructor(private val activity: Activity) {
    fun navigateTo(navigationTarget: NavigationTarget): Boolean {
        for (navigator in navigators) {
            if (navigator.navigateTo(navigationTarget, activity)) {
                return true
            }
        }
        Logger.e("NavigationRouter", "Don't know how to perform navigation to " + navigationTarget.target)
        return false
    }

    fun navigateWithResult(navigationTarget: NavigationTarget?): Observable<NavigationResult> {
        for (navigator in navigators) {
            val navigationResultObservable = navigator.navigateWithResult(navigationTarget, activity)
            if (navigationResultObservable != null) {
                return navigationResultObservable
            }
        }
        return Observable.just(NavigationResult())
    }

    companion object {
        private val navigators: MutableList<INavigator> = ArrayList()
        fun registerNavigator(navigator: INavigator) {
            navigators.add(navigator)
        }
    }
}