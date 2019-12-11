package com.qverkk.touristrentafriend.helpers

import com.qverkk.touristrentafriend.data.Picture
import com.qverkk.touristrentafriend.data.User
import com.qverkk.touristrentafriend.services.UserPictureService
import com.qverkk.touristrentafriend.ui.dashboard.ui.currentUser.CurrentUserViewModel
import com.qverkk.touristrentafriend.ui.login.helpers.UserAsyncTask
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PicturesHelper {

    fun getUserProfilePicture(
        user: User,
        currentUserViewModel: CurrentUserViewModel
    ): Picture? {
        val client = Retrofit.Builder()
            .baseUrl("http://192.168.1.64:8080")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = client.create(UserPictureService::class.java)

        return getImage(service, currentUserViewModel)
    }

    fun postUserProfilePicture(picture: ByteArray) {
        val client = Retrofit.Builder()
            .baseUrl("http://192.168.1.64:8080")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = client.create(UserPictureService::class.java)

        postPicture(service, picture)
    }

    private fun postPicture(service: UserPictureService, picture: ByteArray) {
        val userDb = UserAsyncTask().execute().get()
        val call = service.addPictureToProfile(userDb.userId, picture)
        call.enqueue(object : Callback<Boolean> {
            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                println("Failure uploading image")
                println(call.request().headers())
            }

            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                println(response.body())
            }

        })
    }

    private fun getImage(
        service: UserPictureService,
        currentUserViewModel: CurrentUserViewModel
    ): Picture? {
        val userDb = UserAsyncTask().execute().get()
        val call = service.getUserProfile(userDb.userId)
        var result: Picture? = null
        call.enqueue(object : Callback<Picture> {
            override fun onFailure(call: Call<Picture>, t: Throwable) {
                println("Failure getting image")
            }

            override fun onResponse(call: Call<Picture>, response: Response<Picture>) {
                println("OK")
                val userProfile = response.body() ?: return
                result = userProfile
                println(result?.imageBase64)
                if (result != null) {
                    val uri = result!!.imageBase64
                    println(uri)
                    currentUserViewModel.changeImageUri(GZIPCompression.decompress(uri))
                }
            }

        })
        return result
    }
}