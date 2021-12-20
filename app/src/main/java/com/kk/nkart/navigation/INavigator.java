/*
 * Copyright © 2017 Nedbank. All rights reserved.
 */

package com.kk.nkart.navigation;

import android.app.Activity;

import io.reactivex.Observable;

public interface INavigator {
    boolean navigateTo(final NavigationTarget navigationTarget, final Activity activity);

    Observable<NavigationResult> navigateWithResult(final NavigationTarget navigationTarget, final Activity activity);
}
