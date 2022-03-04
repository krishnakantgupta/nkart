package com.kk.nkart.navigation

import com.kk.nkart.ui.view.address.AddressListingActivity
import com.kk.nkart.ui.view.cart.CartActivity
import com.kk.nkart.ui.view.authentication.login.LoginActivity
import com.kk.nkart.ui.view.authentication.registeration.RegistrationActivity
import com.kk.nkart.ui.view.category.CategoryActivity
import com.kk.nkart.ui.view.dashboard.DashBoardActivity
import com.kk.nkart.ui.view.pdp.ProductDetailsActivity
import com.kk.nkart.ui.view.plp.ProductListActivity
import com.kk.nkart.ui.view.wishlist.WishListActivity

class AppNavigator : BaseNavigator() {
    init {
        forTarget(NavigationTarget.DASHBOARD_SCREEN).openActivity(DashBoardActivity::class.java)
        forTarget(NavigationTarget.LOGIN_SCREEN).openActivity(LoginActivity::class.java)
        forTarget(NavigationTarget.REGISTRATION_SCREEN).openActivity(RegistrationActivity::class.java)
        forTarget(NavigationTarget.PDP_SCREEN).openActivity(ProductDetailsActivity::class.java)
        forTarget(NavigationTarget.CATEGORY_SCREEN).openActivity(CategoryActivity::class.java)

        forTarget(NavigationTarget.FORGOT_PASSWORD_SCREEN).openActivity(DashBoardActivity::class.java)
        forTarget(NavigationTarget.PLP_SCREEN).openActivity(ProductListActivity::class.java)

        forTarget(NavigationTarget.CART_SCREEN).openActivity(CartActivity::class.java)
        forTarget(NavigationTarget.WISHLIST_SCREEN).openActivity(WishListActivity::class.java)
        forTarget(NavigationTarget.GALLERY_SCREEN).openActivity(DashBoardActivity::class.java)
        forTarget(NavigationTarget.ORDER_VIEW_SCREEN).openActivity(DashBoardActivity::class.java)
        forTarget(NavigationTarget.ORDER_DETAILS_SCREEN).openActivity(DashBoardActivity::class.java)
        forTarget(NavigationTarget.ADDRESS_VIEW_SCREEN).openActivity(AddressListingActivity::class.java)
        forTarget(NavigationTarget.ADDRESS_DETAILS_SCREEN).openActivity(DashBoardActivity::class.java)
        forTarget(NavigationTarget.ACCOUNT_SCREEN).openActivity(DashBoardActivity::class.java)

    }
}