package com.qverkk.touristrentafriend.services

import com.qverkk.touristrentafriend.data.MessageDTO
import com.qverkk.touristrentafriend.data.User
import com.qverkk.touristrentafriend.data.UserDetails
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface UserService {

    @POST("/users/login")
    fun login(@Header("username") username: String, @Header("password") password: String): Call<User>

    @POST("/users/logintest")
    fun loginTest(@Header("username") username: String, @Header("password") password: String): Call<UserDetails>

    @POST("/users/register")
    fun register(@Body userWithInformation: UserDetails): Call<UserDetails>

    @GET("/all")
    fun all(): Call<List<User>>

    @GET("/users/all/information")
    fun getAllUsersWithInformation(): Call<List<UserDetails>>

    @GET("/users/all/information/country")
    fun getAllUsersWithInformationFrom(@Header("countryName") countryName: String): Call<List<UserDetails>>

    @POST("/users/send")
    fun sendMessage(@Body message: MessageDTO): Call<Boolean>
}