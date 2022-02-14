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
        return Observable.just(Dummy.getLoginJSON())
//        return NetworkClient.getClient()?.doLogin(bodyData) ?: Observable.just(null)
    }

    override fun doRegister(bodyData: String): Observable<UserModel> {
        return Observable.just(Dummy.getRegistrationJSON())
//        return NetworkClient.getClient()?.doLogin(bodyData) ?: Observable.just(null)
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
        return NetworkClient.getClient()?.getAddressList(userId) ?: Observable.just(listOf())
    }

    override fun getProductDetails(productID: Int): Observable<ProductDetailsModel> {
        return Observable.just(Dummy.getProductDetailsJSON())
//        return NetworkClient.getClient()?.getProductDetails(productID) ?: Observable.just(ProductDetailsModel())
    }

    override fun getDashboardData(userID: Int): Observable<DashboardModel> {
        return Observable.just(Dummy.getDashboardJSON())
//        return NetworkClient.getClient()?.getDashboardData(userID) ?: Observable.just(DashboardModel())
    }


    override fun getCartList(userID: Int): Observable<List<CartModel>> {
        return Observable.just(Dummy.getCartJSON())
//        return NetworkClient.getClient()?.getCartList(userID) ?: Observable.just(listOf())
    }

    override fun getWishList(userID: Int): Observable<List<WishListModel>> {
        return Observable.just(Dummy.getWishListJSON())
//        return NetworkClient.getClient()?.getWishList(userID) ?: Observable.just(listOf())
    }
    override fun addToCart(productId: Int, userId: Int, sizeId: Int, colorId: Int, quantity: Int): Observable<Boolean> {
        return NetworkClient.getClient()?.addToCart(productId,userId,sizeId,colorId,quantity) ?: Observable.just(false)
    }

    override fun deleteFromCart(productId: Int, userId: Int): Observable<Boolean> {
//        return Observable.just(true)
        return NetworkClient.getClient()?.deleteFromCart(productId,userId)?:Observable.just(false)
    }

    override fun deleteFromWishlist(productId: Int, userId: Int): Observable<Boolean> {
//        return Observable.just(true)
        return NetworkClient.getClient()?.deleteFromWishlist(productId,userId)?:Observable.just(false)
    }

    override fun addToWishlist(productId: Int, userId: Int): Observable<Boolean> {
//        return Observable.just(true)
        return  NetworkClient.getClient()?.addToWishlist(productId,userId)?:Observable.just(false)
    }


}