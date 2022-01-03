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
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.kk.jet2articalassignment.data.api.ApiHelper
import com.kk.nkart.R
import com.kk.nkart.base.AppPreferences
import com.kk.nkart.base.BaseApplication
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
import kotlinx.android.synthetic.main.activity_dash_board.*
import kotlinx.android.synthetic.main.activity_product_list.view.*
import kotlinx.android.synthetic.main.view_dashboard_content.view.*
import kotlinx.android.synthetic.main.view_dashboard_tranding.view.*
import kotlinx.android.synthetic.main.view_dashborad_trending_item.view.*
import java.util.*
import javax.inject.Inject
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
        dashboardViewModelFactory = DashboardViewModel.Factory(appPreferences, this.application as BaseApplication, ApiHelper(ApiServiceImpl()))
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
        viewModel.dashboardResponse.observe(this, { event ->
            event?.getContentIfNotHandled()?.let {
                applyDashboardData(it)
            }
        })
    }


    private fun applyDashboardData(dashboardModel: DashboardModel) {
        dashboardModel.dealOfTheDayList?.let {
            setAdapter(it, "Deals of the day", dashBoardContentView.dealOfTheDayLayout)
        }
        dashboardModel.bestSellingList?.let {
            setAdapter(it, "Best Selling", dashBoardContentView.bestSellingLayout)
        }
        dashboardModel.recentViewList?.let {
            setAdapter(it, "Recent view", dashBoardContentView.recentViewLayout)
        }
        dashboardModel.newCollectionList?.let {
            setAdapter(it, "New Collection", dashBoardContentView.newCollectionLayout)
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

    private fun setAdapter(productList: List<ProductModel>, header: String, view: ViewDashboradTrendingItemBinding) {
        if (productList.isNotEmpty()) {
            adapterRecentItem = object : ProductItemAdapter(this@DashBoardActivity, productList) {
                override fun onItemClick(data: Any?, position: Int) {
                    data?.let { it ->
                        var productModel = (it as ProductModel)
                        nextScreen(NavigationTarget.PDP_SCREEN)
                    }
                }
            }
            view.tvViewAll.tag = header
            view.tvViewAll.setOnClickListener { v->
                var tag = v.tag as String

            }
            view.tvHeader.text = header
            view.root.visibility = View.VISIBLE
            view.recyclerViewDashboard.adapter = adapterRecentItem
            view.recyclerViewDashboard.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        } else {
            view.root.visibility = View.GONE
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_dashboard, menu)
        return super.onCreateOptionsMenu(menu)
    }

    @SuppressLint("WrongConstant")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_cart -> {
                nextScreen(NavigationTarget.CART_SCREEN)
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
        navigationRouter.navigateTo(NavigationTarget.to(NavigationTarget.PDP_SCREEN).withParam("product", product))
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