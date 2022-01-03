package com.kk.nkart.ui.view.category

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.kk.jet2articalassignment.data.api.ApiHelper
import com.kk.nkart.R
import com.kk.nkart.base.BaseApplication
import com.kk.nkart.base.core.BaseActivity
import com.kk.nkart.dagger.CoreDI
import com.kk.nkart.data.api.ApiServiceImpl
import com.kk.nkart.data.models.CategoryModel
import com.kk.nkart.data.models.SubCategoryModel
import com.kk.nkart.databinding.ActivityCategoryBinding
import com.kk.nkart.navigation.NavigationRouter
import com.kk.nkart.navigation.NavigationTarget
import com.kk.nkart.ui.common.IRecyclerItemClickListener
import com.kk.nkart.ui.common.ProgressDialog
import com.kk.nkart.ui.view.adapter.CategoryAdapter
import com.kk.nkart.ui.view.adapter.SubCategoryAdapter
import javax.inject.Inject

class CategoryActivity : BaseActivity() {

    @Inject
    lateinit var navigationRouter: NavigationRouter

    //    @Inject
    internal lateinit var categoryViewModelFactory: CategoryViewModel.Factory

    private lateinit var binding: ActivityCategoryBinding
    private lateinit var viewModel: CategoryViewModel

    private val progressDialog = ProgressDialog()
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var subCategoryAdapter: SubCategoryAdapter

    private var categoryList: List<CategoryModel>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CoreDI.getActivityComponent(this).inject(this)
        setUpViewModel()
        setUpBinding()
        setUpObserver()
        init()
    }


    private fun setUpViewModel() {
        categoryViewModelFactory = CategoryViewModel.Factory(this.application as BaseApplication, ApiHelper(ApiServiceImpl()))
        viewModel = ViewModelProvider(this, categoryViewModelFactory).get(CategoryViewModel::class.java)
        registerGoBack(viewModel)
    }

    private fun setUpBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_category)
        binding.viewModel = viewModel
    }

    private fun setUpObserver() {
        viewModel.categoryResponse.observe(this, Observer { event ->
            event?.getContentIfNotHandled()?.let {
                categoryList = it
                categoryAdapter.setData(categoryList)
                subCategoryAdapter.setData(getAllSubCategory())
            }
        })
        viewModel.progressEvent.observe(this, Observer { event ->
            event?.getContentIfNotHandled()?.let {
                if (it) {
                    progressDialog.show(this@CategoryActivity, getString(R.string.pelase_wait))
                } else {
                    progressDialog.dialog.dismiss()
                }
            }
        })

    }

    private fun init() {
        categoryAdapter = CategoryAdapter(this@CategoryActivity, object : IRecyclerItemClickListener {
            override fun onItemClick(data: Any?, position: Int) {
                if (position == 0) {
                    subCategoryAdapter.setData(getAllSubCategory())
                } else {
                    subCategoryAdapter.setData(categoryList!![position - 1].subCategoriesList)
                }
            }
        })
        subCategoryAdapter = SubCategoryAdapter(this@CategoryActivity, object : IRecyclerItemClickListener {
            override fun onItemClick(data: Any?, position: Int) {
                navigationRouter.navigateTo(NavigationTarget.to(NavigationTarget.PLP_SCREEN))
            }
        })
        binding.recyclerViewCategory.adapter = categoryAdapter
        binding.recyclerViewSubCategory.adapter = subCategoryAdapter
        binding.recyclerViewCategory.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerViewSubCategory.layoutManager = LinearLayoutManager(this)
        viewModel.getCategory()

    }

    private fun getAllSubCategory(): List<SubCategoryModel> {
        var allSubCategory = ArrayList<SubCategoryModel>()
        categoryList?.forEach { it ->
            if (it.subCategoriesList != null) {
                allSubCategory.addAll(it.subCategoriesList!!)
            }
        }
        return allSubCategory
    }
}