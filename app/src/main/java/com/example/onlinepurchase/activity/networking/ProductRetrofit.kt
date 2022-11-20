package com.example.onlinepurchase.activity.networking

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ProductRetrofit {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://mocki.io/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val productRetrofitService: ProductService = retrofit.create(ProductService::class.java)
}