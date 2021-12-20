package com.kk.nkart.data.models

data class UserModel(var id: String? = null,
                     var createdAt: String? = null,
                     var name: String? = null,
                     var lastname: String? = null,
                     var avatar: String? = null,
                     var mobile: String? = null,
                     var email: String? = null,
                     var password: String? = null
) : BaseModel() {
    fun getFullName(): String {
        return name + " " + lastname
    }
}