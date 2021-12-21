package com.kk.jet2articalassignment.data.api

class APIConstants {
    companion object {
//        https://da24-122-162-210-251.ngrok.io/nKart/products
//        https://da24-122-162-210-251.ngrok.io/nKart/getProductCategories
//        https://da24-122-162-210-251.ngrok.io/nKart/productDetails?productId=1
//        https://da24-122-162-210-251.ngrok.io/nKart/getAddress?userId=1
//        https://da24-122-162-210-251.ngrok.io/nKart/getWishlistProducts?userId=1
//        https://da24-122-162-210-251.ngrok.io/nKart/getCartlistProducts?userId=1
//        https://da24-122-162-210-251.ngrok.io/nKart/addWishlistProducts?productId=1&userId=1
//        https://da24-122-162-210-251.ngrok.io/nKart/addProductToCart?productId=1&userId=1&sizeId=1&colorId=1&quantity
//        https://da24-122-162-210-251.ngrok.io/nKart/deleteCartProduct?productId=1&userId=1
//        https://da24-122-162-210-251.ngrok.io/nKart/deleteWishlistProduct?productId=1&userId=1



        const val BASE_URL: String = "https://jsonplaceholder.typicode.com/todos/"

        const val GET_ARTICLE: String = "1"
        const val GET_LOGIN: String = "/nKart/authenticateUser"
        const val GET_REGISTRATION: String = "/nKart/registerUser"
        const val GET_CATEGORY: String = "nKart/getProductCategories"
        const val GET_PRODUCT_LIST: String = "/nKart/products"
        const val GET_PRODUCT_DETAILS: String = "/nKart/getAddress"
        const val GET_ADDRESS: String = "/nKart/getAddress"
        const val GET_WISHLIST: String = "/nKart/getWishlistProducts"
        const val GET_CART_LIST: String = "/nKart/getCartlistProducts"

    }
}


//
//PostRequest:
//https://da24-122-162-210-251.ngrok.io/nKart/authenticateUser
//BodyParam:
//{
//    "username":"amit.kumar01",
//    "password":"nagarro@123"
//}
//
//https://da24-122-162-210-251.ngrok.io/nKart/registerUser
//BodyParam:
//{
//    "username":"HarshVishavkarma",
//    "password":"Harsh@123456",
//    "salutation":"Mr",
//    "firstName":"Harsh",
//    "lastName":"Vishavkarma",
//    "email":"harsh.karma01@nagarro.com",
//    "mobile":"9999765342"
//}
//
//I have Added one more user:
//"username":"krishnakant"
//"password":"nagarro@1234"
//
//


