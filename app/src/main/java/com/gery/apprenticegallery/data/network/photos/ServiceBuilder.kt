package com.gery.apprenticegallery.data.network.photos

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit


import retrofit2.converter.gson.GsonConverterFactory

const val api_key = "563492ad6f91700001000001676a082655e6480ca3d6f53f8ead72b2"
const val BASE_URL = "https://api.pexels.com/"

object ServiceBuilder {
    private val client = OkHttpClient
        .Builder()
        .addNetworkInterceptor { chain ->
            val original: Request = chain.request()
            val requestBuilder: Request.Builder = original.newBuilder()
                .header(
                    "Authorization",
                    api_key
                )
            val request: Request = requestBuilder.build()
            chain.proceed(request)
        }
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()
        .create(PhotosEndpoints::class.java)

    fun buildService(): PhotosEndpoints {
        return retrofit
    }
}