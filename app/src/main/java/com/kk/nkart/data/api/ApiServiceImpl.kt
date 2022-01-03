package com.kk.nkart.data.api

import com.kk.jet2articalassignment.data.api.ApiService
import com.kk.jet2articalassignment.data.api.NetworkClient
import com.kk.jet2articalassignment.data.models.ArticleInfo
import com.kk.nkart.base.AppMemory
import com.kk.nkart.base.core.Dummy
import com.kk.nkart.data.models.*
import io.reactivex.Observable

class ApiServiceImpl : ApiService {

    override fun getArticles(): Observable<List<ArticleInfo>> {
        return NetworkClient.getClient()?.getArticles() ?: Observable.just(listOf())
    }


    override fun doLogin(bodyData: String): Observable<UserModel> {
        return NetworkClient.getClient()?.doLogin(bodyData) ?: Observable.just(null)
    }

    override fun getCategory(): Observable<List<CategoryModel>> {
        if (AppMemory.categoryList != null) {
            return Observable.just(AppMemory.categoryList)
        }
        AppMemory.categoryList = Dummy.getCategoryJSON()
        return Observable.just(AppMemory.categoryList) //NetworkClient.getClient()?.getCategory() ?: Observable.just(listOf())
    }

    override fun getProductList(pageNumber: Int): Observable<List<ProductModel>> {
        return Observable.just(Dummy.getProductJSON())
        //return NetworkClient.getClient()?.getProductList(pageNumber) ?: Observable.just(listOf())
    }

    override fun getAddressList(userId: Int): Observable<List<AddressModel>> {
        return Observable.just(Dummy.getAddressJSON())
//        return NetworkClient.getClient()?.getAddressList(userId) ?: Observable.just(listOf())
    }

    override fun getProductDetails(productID: Int): Observable<ProductDetailsModel> {
        return Observable.just(Dummy.getProductDetailsJSON())
//        return NetworkClient.getClient()?.getAddressList(userId) ?: Observable.just(listOf())
    }

    override fun getDashboardData(userID: Int): Observable<DashboardModel> {
        return Observable.just(Dummy.getDashboardJSON())
    }

}