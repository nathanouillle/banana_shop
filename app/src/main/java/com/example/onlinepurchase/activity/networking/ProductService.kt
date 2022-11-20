package com.example.onlinepurchase.activity.networking

import retrofit2.Call
import retrofit2.http.GET

interface ProductService {
    @GET("v1/c13b6ad4-e03d-4e67-ab2a-6ea7ea0a9e4f")
    fun getProductList(): Call<ProductListDTO>

}