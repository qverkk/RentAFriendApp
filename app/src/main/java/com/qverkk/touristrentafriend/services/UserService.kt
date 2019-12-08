package com.qverkk.touristrentafriend.services

import com.qverkk.touristrentafriend.data.User
import com.qverkk.touristrentafriend.data.UserDetails
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface UserService {

    @POST("/users/login")
    fun login(@Header("username") username: String, @Header("password") password: String): Call<User>

    @POST("/users/logintest")
    fun loginTest(@Header("username") username: String, @Header("password") password: String): Call<UserDetails>

    @GET("/all")
    fun all(): Call<List<User>>
}