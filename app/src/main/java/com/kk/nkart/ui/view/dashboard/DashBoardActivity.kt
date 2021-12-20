package com.kk.nkart.ui.view.dashboard

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.Gravity.START
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import com.kk.nkart.R
import com.kk.nkart.base.BaseActivity
import com.kk.nkart.dagger.CoreDI
import com.kk.nkart.databinding.ActivityDashBoardBinding
import com.kk.nkart.databinding.ViewDashboardDrawerBinding
import com.kk.nkart.navigation.NavigationRouter
import com.kk.nkart.navigation.NavigationTarget
import com.kk.nkart.ui.view.CartActivity
import com.kk.nkart.ui.view.authentication.login.LoginActivity
import com.kk.nkart.ui.view.category.CategoryActivity
import com.kk.nkart.ui.view.pdp.ProductDetailsActivity
import com.kk.nkart.ui.view.plp.ProductListActivity
import com.kk.nkart.utils.IntentUtils
import javax.inject.Inject

class DashBoardActivity : BaseActivity() {


//    @Inject
//    lateinit var appPreferences: AppPreferences

    @Inject
    lateinit var navigationRouter: NavigationRouter

    private lateinit var binding: ActivityDashBoardBinding
    private lateinit var drawer: DrawerLayout
//    private lateinit var drawerView: ViewDashboardDrawerBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CoreDI.getActivityComponent(this).inject(this)

        binding = ActivityDashBoardBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        var toolbar = binding.drawerContentView.toolbar.toolbar
        initToolbar(toolbar, true, true, "nKart")
        drawer = binding.drawer
        val actionBarDrawerToggle = ActionBarDrawerToggle(this, drawer, R.string.nav_open, R.string.nav_close)
        drawer.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()
//        drawerView = binding.drawerView
//        drawerView.menuCategory.setOnClickListener { v->onCategoryClick(v) }

    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_dashboard, menu)
        return super.onCreateOptionsMenu(menu)
    }

    @SuppressLint("WrongConstant")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_cart -> {
                return true
            }
            android.R.id.home -> {
                if (!drawer.isDrawerOpen(START)) {
                    drawer.openDrawer(START, true)
                }
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    fun navigateToCart() {
        nextScreen(this, CartActivity::class.java)
    }


    fun navigateToCategory() {
        nextScreen(this, CategoryActivity::class.java)
    }

    fun navigateToLogin() {
        nextScreen(this, LoginActivity::class.java)
    }


    fun navigateToPlp(type: Int) {
        var map = HashMap<String, Any>()
        map.put("type", type)
        nextScreenWithParam(this, ProductListActivity::class.java, map)
    }

    fun navigateToPdp(productId: Int) {
        var map = HashMap<String, Any>()
        map.put("productId", productId)
        nextScreenWithParam(this, ProductDetailsActivity::class.java, map)
    }

    fun navigateToOrder() {

    }

    fun nextScreen(context: Context, cls: Class<*>?) {
        IntentUtils.nextScreen(this, cls)
    }

    fun nextScreenWithParam(context: Context, cls: Class<*>?, map: HashMap<String, Any>) {
        IntentUtils.nextScreenWithParam(this, cls, map)
    }


    fun onDrawerCrossClick(view: View) {
        closeDrawer()
    }

    @SuppressLint("WrongConstant")
    fun closeDrawer() {
        drawer.closeDrawer(START, true)
    }

    fun loginClick(view: View) {
        navigationRouter.navigateTo(NavigationTarget.to(NavigationTarget.LOGIN_SCREEN))
    }

    fun onCategoryClick(view: View) {
        Toast.makeText(this, "Category Clicked", Toast.LENGTH_SHORT).show()
        navigationRouter.navigateTo(NavigationTarget.to(NavigationTarget.PDP_SCREEN))
    }

    fun onNewCollectionClick(view: View) {
        navigateToPlp(0)
    }

    fun isLogin(): Boolean {
        return true
    }

    fun onAccountClick(view: View) {}
    fun onOrderClick(view: View) {}
    fun onWishListClick(view: View) {}

}