package com.qverkk.touristrentafriend.services

import com.qverkk.touristrentafriend.data.Picture
import com.qverkk.touristrentafriend.data.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface UserPictureService {
    @POST("/pictures/update/pictures")
    fun updateToProfilePicture(@Header("user") userId: Int, @Header("picture") picture: String): Call<Boolean>

    @POST("/pictures/userprofile")
    fun getUserProfile(@Body userId: Int): Call<Picture>

    @GET("/pictures/get")
    fun getAllForUser(@Header("user") user: User): Call<List<Picture>>

    @POST("/pictures/user/add")
    fun addPictureToProfile(@Header("user") userId: Int, @Body picture: ByteArray): Call<Boolean>
}