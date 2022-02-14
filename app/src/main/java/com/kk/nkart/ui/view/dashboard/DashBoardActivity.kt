package com.kk.nkart.ui.view.dashboard

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Gravity.START
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.kk.jet2articalassignment.data.api.ApiHelper
import com.kk.nkart.R
import com.kk.nkart.base.AppMemory
import com.kk.nkart.base.AppPreferences
import com.kk.nkart.base.BaseApplication
import com.kk.nkart.base.Constants
import com.kk.nkart.base.core.BaseActivity
import com.kk.nkart.dagger.CoreDI
import com.kk.nkart.data.api.ApiServiceImpl
import com.kk.nkart.data.models.DashboardModel
import com.kk.nkart.data.models.DashboardToImageModel
import com.kk.nkart.data.models.ProductModel
import com.kk.nkart.databinding.ActivityDashBoardBinding
import com.kk.nkart.databinding.ViewDashboardContentBinding
import com.kk.nkart.databinding.ViewDashboradTrendingItemBinding
import com.kk.nkart.navigation.NavigationRouter
import com.kk.nkart.navigation.NavigationTarget
import com.kk.nkart.ui.view.adapter.ImageGalleryAdapter
import com.kk.nkart.ui.view.adapter.ProductItemAdapter
import com.kk.nkart.ui.view.pdp.ProductDetailsActivity
import com.kk.nkart.ui.view.plp.ProductListActivity
import com.kk.nkart.utils.IntentUtils
import com.kk.nkart.utils.Logger
import kotlinx.android.synthetic.main.activity_dash_board.*
import kotlinx.android.synthetic.main.activity_product_list.view.*
import kotlinx.android.synthetic.main.view_dashboard_content.view.*
import kotlinx.android.synthetic.main.view_dashboard_tranding.view.*
import kotlinx.android.synthetic.main.view_dashborad_trending_item.view.*
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


class DashBoardActivity : BaseActivity() {


    @Inject
    lateinit var appPreferences: AppPreferences

    @Inject
    lateinit var navigationRouter: NavigationRouter

    internal lateinit var dashboardViewModelFactory: DashboardViewModel.Factory
    private lateinit var binding: ActivityDashBoardBinding
    private lateinit var dashBoardContentView: ViewDashboardContentBinding
    private lateinit var viewModel: DashboardViewModel
    private lateinit var drawer: DrawerLayout

    //    private lateinit var drawerView: ViewDashboardDrawerBinding
    private var adapterBestSelling: ProductItemAdapter? = null
    private var adapterNewCollection: ProductItemAdapter? = null
    private var adapterRecentItem: ProductItemAdapter? = null
    private var adapterDealOfTheDay: ProductItemAdapter? = null
    private var currentCarouselPosition = 0
    private var topImages: List<DashboardToImageModel>? = null
    val imageTopHandler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CoreDI.getActivityComponent(this).inject(this)
        setUpViewModel()
        setUpBinding()
        setUpObservers()
        init()
    }

    override fun onResume() {
        super.onResume()
        viewModel.refreshLogin()
    }

    private fun setUpViewModel() {
        dashboardViewModelFactory = DashboardViewModel.Factory(navigationRouter, appPreferences, this.application as BaseApplication, ApiHelper(ApiServiceImpl()))
        viewModel = ViewModelProvider(this, dashboardViewModelFactory).get(DashboardViewModel::class.java)
        registerGoBack(viewModel)
    }

    private fun setUpBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_dash_board)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        dashBoardContentView = binding.drawerContentView
    }

    private fun init() {
        var toolbar = dashBoardContentView.toolbar.toolbar
        initToolbar(toolbar, true, true, "nKart")
        drawer = binding.drawer
        val actionBarDrawerToggle = ActionBarDrawerToggle(this, drawer, R.string.nav_open, R.string.nav_close)
        drawer.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()

        viewModel.getCategory()
        viewModel.getDashboardData()
        if (appPreferences.isUserLogin()) {
            viewModel.getCartList(AppMemory.userModel.userId)
        }
    }

    private fun setUpObservers() {
        viewModel.drawerClose.observe(this, { closeDrawer() })
        viewModel.nextScreen.observe(this, { event ->
            event?.getContentIfNotHandled()?.let {
                nextScreen(it)
            }
        })
        viewModel.categoryResponse.observe(this, { event ->
            event?.getContentIfNotHandled()?.let {
            }
        })
        viewModel.cartListResponse.observe(this, { event ->
            event?.getContentIfNotHandled()?.let {
                setupBedge(it)
            }
        })

        viewModel.dashboardResponse.observe(this, { event ->
            event?.getContentIfNotHandled()?.let {
                applyDashboardData(it)
            }
        })
    }

    private fun applyDashboardData(dashboardModel: DashboardModel) {
        dashboardModel.dealOfTheDayList?.let {
            setAdapter(it, adapterDealOfTheDay, "Deals of the day", dashBoardContentView.dealOfTheDayLayout)
        }
        dashboardModel.bestSellingList?.let {
            setAdapter(it, adapterBestSelling, "Best Selling", dashBoardContentView.bestSellingLayout)
        }
        dashboardModel.recentViewList?.let {
            setAdapter(it, adapterRecentItem, "Recent view", dashBoardContentView.recentViewLayout)
        }
        dashboardModel.newCollectionList?.let {
            viewModel.newCollection = it
            setAdapter(it, adapterNewCollection, "New Collection", dashBoardContentView.newCollectionLayout)
        }
        topImages = dashboardModel.topImages?.let { it }
        if (topImages != null) {
            val imageArray = topImages!!.map { it.iamgeUrl }.toList()
            val adapter = ImageGalleryAdapter(this, imageArray)
            dashBoardContentView.pagerCarouselViewPager.adapter = adapter
            dashBoardContentView.pageIndicator.attachViewPager(dashBoardContentView.pagerCarouselViewPager)
//            topImageCarousalTimer.scheduleAtFixedRate(TopCarouselTimerTask(), 0, 2000)
            imageTopHandler.post(topCarouselTimerTask)
        }
    }

    private fun getAdapter(productList: List<ProductModel>): ProductItemAdapter {
        return object : ProductItemAdapter(this@DashBoardActivity, productList) {
            override fun onItemClick(data: Any?, position: Int) {
                data?.let { it ->
                    var productModel = (it as ProductModel)
                    nextScreen(NavigationTarget.PDP_SCREEN)
                }
            }
        }
    }

    private fun setAdapter(productList: List<ProductModel>, itemAdapter: ProductItemAdapter?, header: String, view: ViewDashboradTrendingItemBinding) {
        if (productList.isNotEmpty()) {
            var adapter = object : ProductItemAdapter(this@DashBoardActivity, productList) {
                override fun onItemClick(data: Any?, position: Int) {
                    data?.let { it ->
                        var productModel = (it as ProductModel)
                        navigateToProductDetailsScreen(productModel)
                    }
                }
            }
            var tag = ArrayList<Any>()
            tag.add(header)
            tag.add(productList)
            view.tvViewAll.tag = tag
            view.tvViewAll.setOnClickListener { v ->
                var tag = v.tag as ArrayList<Any>
                var navigationTarget = NavigationTarget.to(NavigationTarget.PLP_SCREEN)
                navigationTarget.withParam(Constants.BUNDLE_KEY_SUB_CATEGORY_NAME, tag[0] as String)
                navigationTarget.withParam(Constants.BUNDLE_KEY_PRODUCT_LIST, tag[1] as List<ProductModel>)
                navigationRouter.navigateTo(navigationTarget)

            }
            view.tvHeader.text = header
            view.root.visibility = View.VISIBLE
            view.recyclerViewDashboard.adapter = adapter
            view.recyclerViewDashboard.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        } else {
            view.root.visibility = View.GONE
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_dashboard, menu)

        val menuItem = menu!!.findItem(R.id.menu_cart)
        val actionView = menuItem.actionView
        textCartItemCount = actionView.findViewById<TextView>(R.id.cart_badge)
        setupBedge(AppMemory.cartListIds.size)
        actionView.setOnClickListener {
            cartMenuClicked()
        }
        return super.onCreateOptionsMenu(menu)
    }

    private fun cartMenuClicked() {
        if (appPreferences.isUserLogin()) {
            nextScreen(NavigationTarget.CART_SCREEN)
        } else {

            navigationRouter.navigateWithResult(NavigationTarget.to(NavigationTarget.LOGIN_SCREEN)).subscribe(
                { navigationResult ->
                    if (navigationResult?.isOk == true) {
                        nextScreen(NavigationTarget.CART_SCREEN)
                    }

                }, { throwable ->
                    Logger.e("KK", "ERRO", throwable)
                })
        }
    }

    @SuppressLint("WrongConstant")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_cart -> {
                cartMenuClicked()
                return true
            }
            R.id.menu_search -> {
                nextScreen(NavigationTarget.SEARCH_SCREEN)
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

    fun nextScreen(targetName: String) {
        navigationRouter.navigateTo(NavigationTarget.to(targetName))
    }

    fun navigateToProductDetailsScreen(product: ProductModel) {
        navigationRouter.navigateTo(NavigationTarget.to(NavigationTarget.PDP_SCREEN).withParam(Constants.BUNDLE_KEY_PRODUCT, product))
    }

    fun nextScreenWithParam(context: Context, cls: Class<*>?, map: HashMap<String, Any>) {
        IntentUtils.nextScreenWithParam(this, cls, map)
    }

    @SuppressLint("WrongConstant")
    fun closeDrawer() {
        drawer.closeDrawer(START, true)
    }

    fun isUserAareadyLogin(): Boolean {
        return appPreferences.isUserLogin()
    }

    private val topCarouselTimerTask = object : Runnable {
        override fun run() {
            var currentIndex = drawerContentView.pagerCarouselViewPager.currentItem
            var totalCount = drawerContentView.pagerCarouselViewPager.adapter?.count ?: 0
//        if (currentIndex + 1 >= drawerContentView.pagerCarouselViewPager.adapter?.count ?: 0) {
            drawerContentView.pagerCarouselViewPager.currentItem = currentIndex % ((totalCount - 1))
//        }
            imageTopHandler.postDelayed(this, 2000)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        imageTopHandler.removeCallbacks(topCarouselTimerTask)
    }
}