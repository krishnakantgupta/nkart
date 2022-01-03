package com.kk.nkart.base

import com.kk.nkart.data.models.CategoryModel
import com.kk.nkart.data.models.ProductModel
import com.kk.nkart.data.models.UserModel

object AppMemory {
    var categoryList: List<CategoryModel>? = null
    var wishList: ArrayList<ProductModel> = ArrayList()
    var wishListIds: ArrayList<Int> = ArrayList()
    var userModel: UserModel = UserModel()

    fun resetMemory() {
        wishList.clear()
        wishListIds.clear()
        userModel = UserModel()
    }
}