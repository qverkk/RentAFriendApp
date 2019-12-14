package com.qverkk.touristrentafriend.services

import com.qverkk.touristrentafriend.data.UserOrder
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface UserOrdersService {

    @POST("/orders/create")
    fun createOrder(@Header("rentingId") rentingId: Int, @Header("rentedId") rentedId: Int): Call<Boolean>

    @GET("/orders/get/for")
    fun getOrdersFor(@Header("userId") userId: Int): Call<List<UserOrder>>
}