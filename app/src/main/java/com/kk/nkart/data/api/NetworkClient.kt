package com.kk.jet2articalassignment.data.api

import android.util.Log
import com.kk.nkart.data.api.SimpleLoggingInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object NetworkClient {

    private var client: NetworkApiService? = null

    fun getClient(): NetworkApiService? {
        if (client == null) {
            client = createClient()
        }
        return client
    }

    fun resetClient() {
        client = null
    }

    private fun createClient(): NetworkApiService? {
        val NETWORK_TIMEOUT: Long = 60
        val READ_TIMEOUT: Long = 10
        try {

            val interceptor = SimpleLoggingInterceptor()
//            interceptor.level = HttpLoggingInterceptor.Level.BASIC

            val client = OkHttpClient.Builder()
                .connectTimeout(NETWORK_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT, TimeUnit.MINUTES)
                .addInterceptor(getLogging())
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl(APIConstants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()

            return retrofit.create(NetworkApiService::class.java)
        } catch (e: Exception) {
            Log.e("NetworkClient", e.message + "")
        }
        return null
    }

    private fun getLogging(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC)
        return logging
    }
}