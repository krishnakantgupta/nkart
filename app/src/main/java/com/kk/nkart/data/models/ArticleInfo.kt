package com.kk.jet2articalassignment.data.models

import com.google.gson.annotations.SerializedName

data class ArticleInfo(
//    val id: String,
//    val createdAt: String,
//    val content: String,
//    val comments: Long = 0,
//    val likes: Long = 0,
//    @SerializedName("user") val userInfo: List<UserInfo>? = null

    val userId : Int,
    val id: Int,
    val title: String="",
    val completed :Boolean = false
)
//{
//    fun getUserName(): String? {
//        return getUser()?.getFullName()
//    }
//
//    fun getUser(): UserInfo? {
//        if (userInfo?.isNotEmpty()!!)
//            return userInfo.get(0)
//        return null
//    }
//}
