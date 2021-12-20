package com.kk.jet2articalassignment.data.models

import com.kk.nkart.data.models.BaseModel

data class UserInfo(
    val id: String,
    val createdAt: String,
    val blogId: String,
    val name: String,
    val lastname: String,
    val avatar: String,
    val city: String,
    val designation: String,
    val about: String
) : BaseModel() {
    fun getFullName(): String {
        return name + " " + lastname
    }
}
