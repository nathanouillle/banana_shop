package com.example.onlinepurchase.activity.networking

import retrofit2.Call
import retrofit2.http.GET

interface ProductService {
    @GET("v1/a0df10b4-e573-4be2-b427-ead8bbfa1d62")
    fun getProductList(): Call<ProductListDTO>

}