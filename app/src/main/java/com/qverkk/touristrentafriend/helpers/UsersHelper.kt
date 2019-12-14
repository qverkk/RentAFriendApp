package com.qverkk.touristrentafriend.helpers

import com.qverkk.touristrentafriend.data.Constants
import com.qverkk.touristrentafriend.data.UserDetails
import com.qverkk.touristrentafriend.services.UserService
import com.qverkk.touristrentafriend.ui.dashboard.ui.home.UsersAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class UsersHelper {

    fun getAllUserInformation(
        list: MutableList<UserDetails>,
        adapter: UsersAdapter
    ) {
        val client = Retrofit.Builder()
            .baseUrl(Constants.REST_API_SERVER_IP)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = client.create(UserService::class.java)
        val call = service.getAllUsersWithInformation()

        call.enqueue(object : Callback<List<UserDetails>> {
            override fun onFailure(call: Call<List<UserDetails>>, t: Throwable) {
                println("Failed to get user informations")
            }

            override fun onResponse(
                call: Call<List<UserDetails>>,
                response: Response<List<UserDetails>>
            ) {
                val body = response.body()
                if (body != null) {
                    val curItemSize = adapter.itemCount
                    list.addAll(body)
                    adapter.notifyItemRangeInserted(curItemSize, body.size)
                }
            }

        })
    }

    fun getAllUserInformationFrom(
        list: MutableList<UserDetails>,
        adapter: UsersAdapter,
        countryName: String
    ) {
        val client = Retrofit.Builder()
            .baseUrl(Constants.REST_API_SERVER_IP)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = client.create(UserService::class.java)
        val call = service.getAllUsersWithInformationFrom(countryName)

        call.enqueue(object : Callback<List<UserDetails>> {
            override fun onFailure(call: Call<List<UserDetails>>, t: Throwable) {
                println("Failed to get user informations")
            }

            override fun onResponse(
                call: Call<List<UserDetails>>,
                response: Response<List<UserDetails>>
            ) {
                val body = response.body()
                if (body != null) {
                    val curItemSize = adapter.itemCount
                    list.addAll(body)
                    adapter.notifyItemRangeInserted(curItemSize, body.size)
                }
            }

        })
    }
}