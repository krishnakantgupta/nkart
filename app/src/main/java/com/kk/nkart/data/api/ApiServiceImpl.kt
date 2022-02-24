package com.kk.nkart.data.api

import com.kk.jet2articalassignment.data.api.ApiService
import com.kk.jet2articalassignment.data.api.NetworkClient
import com.kk.jet2articalassignment.data.models.ArticleInfo
import com.kk.nkart.base.AppMemory
import com.kk.nkart.base.core.Dummy
import com.kk.nkart.data.models.*
import com.kk.nkart.data.requestModels.LoginRequestModel
import io.reactivex.Observable

class ApiServiceImpl : ApiService {

    private val isOffLine = false
    override fun getArticles(): Observable<List<ArticleInfo>> {
        return NetworkClient.getClient()?.getArticles() ?: Observable.just(listOf())
    }


    override fun doLogin(bodyData: LoginRequestModel): Observable<UserModel> {
        if (isOffLine)
            return Observable.just(Dummy.getLoginJSON())
        else {
            return NetworkClient.getClient()?.doLogin(bodyData) ?: Observable.just(UserModel())
        }
    }

    override fun doRegister(bodyData: String): Observable<UserModel> {
//        if (isOffLine)
            return Observable.just(Dummy.getRegistrationJSON())
//        else
//            return NetworkClient.getClient()?.doLogin(bodyData) ?: Observable.just(UserModel())
    }

    override fun getCategory(): Observable<List<CategoryModel>> {
        if (AppMemory.categoryList?.isEmpty() == false) {
            return Observable.just(AppMemory.categoryList)
        }
        return NetworkClient.getClient()?.getCategory() ?: Observable.just(listOf())
    }

    override fun getProductList(pageNumber: Int): Observable<List<ProductModel>> {
        if (isOffLine)
            return Observable.just(Dummy.getProductJSON())
        else
            return NetworkClient.getClient()?.getProductList(pageNumber)
                ?: Observable.just(listOf())
    }

    override fun getProductListForSubCategory(subCategoryId: Int): Observable<List<ProductModel>> {
        if (isOffLine)
            return Observable.just(Dummy.getProductJSON())
        else
            return NetworkClient.getClient()?.getProductListForSubCategory(subCategoryId)
                ?: Observable.just(listOf())
    }

    override fun getAddressList(userId: Int): Observable<List<AddressModel>> {
        if (isOffLine)
            return Observable.just(Dummy.getAddressJSON())
        else
            return NetworkClient.getClient()?.getAddressList(userId) ?: Observable.just(listOf())
    }

    override fun getProductDetails(productID: Int): Observable<ProductDetailsModel> {
        if (isOffLine)
            return Observable.just(Dummy.getProductDetailsJSON())
        else
            return NetworkClient.getClient()?.getProductDetails(productID)
                ?: Observable.just(ProductDetailsModel())
    }

    override fun getDashboardData(userID: Int): Observable<DashboardModel> {
        if (isOffLine)
            return Observable.just(Dummy.getDashboardJSON())
        else {
            if(userID >0) {
                return NetworkClient.getClient()?.getDashboardData(userID)
                    ?: Observable.just(DashboardModel())
            }else{
                return NetworkClient.getClient()?.getDashboardData()
                    ?: Observable.just(DashboardModel())
            }
        }
    }


    override fun getCartList(userID: Int): Observable<List<CartModel>> {
        if (isOffLine)
            return Observable.just(Dummy.getCartJSON())
        else
            return NetworkClient.getClient()?.getCartList(userID) ?: Observable.just(listOf())
    }

    override fun getWishList(userID: Int): Observable<List<WishListModel>> {
        if (isOffLine)
            return Observable.just(Dummy.getWishListJSON())
        else
            return NetworkClient.getClient()?.getWishList(userID) ?: Observable.just(listOf())
    }

    override fun addToCart(productId: Int, userId: Int, sizeId: Int, colorId: Int, quantity: Int): Observable<Boolean> {
        return NetworkClient.getClient()?.addToCart(productId, userId, sizeId, colorId, quantity)
            ?: Observable.just(false)
    }

    override fun deleteFromCart(productId: Int, userId: Int): Observable<Boolean> {
        if (isOffLine)
            return Observable.just(true)
        else
            return NetworkClient.getClient()?.deleteFromCart(productId, userId)
                ?: Observable.just(false)
    }

    override fun deleteFromWishlist(productId: Int, userId: Int): Observable<Boolean> {
        if (isOffLine)
            return Observable.just(true)
        else
            return NetworkClient.getClient()?.deleteFromWishlist(productId, userId)
                ?: Observable.just(false)
    }

    override fun addToWishlist(productId: Int, userId: Int): Observable<Boolean> {
        if (isOffLine)
            return Observable.just(true)
        else
            return NetworkClient.getClient()?.addToWishlist(productId, userId)
                ?: Observable.just(false)
    }




}