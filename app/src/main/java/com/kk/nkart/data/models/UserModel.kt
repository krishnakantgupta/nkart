package com.kk.nkart.data.models

data class UserModel(
    val userId: Int = -1,
    val userName: String = "",
    val salutation: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val createdDate: String = "",
    val lastUpdated: String = "",
    val mobile: String = "",
    val passwordHash: String? = null,
    val lastLogin: String? = null
) : BaseModel() {
    fun getFullName(): String {
        return "$salutation $firstName $lastName"
    }
}