package com.qverkk.touristrentafriend.helpers

import android.widget.Button
import com.qverkk.touristrentafriend.data.Constants
import com.qverkk.touristrentafriend.data.UserOrder
import com.qverkk.touristrentafriend.services.UserOrdersService
import com.qverkk.touristrentafriend.ui.dashboard.ui.messaging.MessagesAdapter
import com.qverkk.touristrentafriend.ui.login.helpers.UserAsyncTask
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RentUserHelper {

    fun isUserRented(
        rentingUserId: Int,
        rentedUserId: Int,
        rentButton: Button
    ) {
        isRented(getOrderService(), rentingUserId, rentedUserId, rentButton)
    }

    private fun isRented(
        orderService: UserOrdersService,
        rentingUserId: Int,
        rentedUserId: Int,
        rentButton: Button
    ) {
        val call = orderService.isUserRented(rentingUserId, rentedUserId)
        call.enqueue(object : Callback<Boolean> {
            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                println("Failed to check if user is rented")
            }

            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                if (response.body() == true) {
                    rentButton.text = "Rented"
                    rentButton.isEnabled = false
                }
            }
        })
    }

    fun postUserOrderBetween(
        rentingUserId: Int,
        rentedUserId: Int,
        rentButton: Button
    ) {
        postOrder(getOrderService(), rentingUserId, rentedUserId, rentButton)
    }

    fun getAllOrdersForUser(
        orders: MutableList<UserOrder>,
        adapter: MessagesAdapter
    ) {
        getOrders(getOrderService(), orders, adapter)
    }

    private fun getOrders(
        orderService: UserOrdersService,
        orders: MutableList<UserOrder>,
        adapter: MessagesAdapter
    ) {
        val userDb = UserAsyncTask().execute().get()
        val call = orderService.getOrdersFor(userDb.userId)
        call.enqueue(object : Callback<List<UserOrder>> {
            override fun onFailure(call: Call<List<UserOrder>>, t: Throwable) {
                println("Failure getting list of users")
            }

            override fun onResponse(
                call: Call<List<UserOrder>>,
                response: Response<List<UserOrder>>
            ) {
                val body = response.body()
                if (body == null || body.isEmpty()) {
                    return
                }
                body.forEach {
                    orders.add(it)
                }
                adapter.notifyItemRangeInserted(0, body.size)
            }
        })
    }

    private fun postOrder(
        service: UserOrdersService,
        rentingUserId: Int,
        rentedUserId: Int,
        rentButton: Button
    ) {
        val call = service.createOrder(rentingUserId, rentedUserId)
        call.enqueue(object : Callback<Boolean> {
            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                println("Failure posting order")
                println(call.request().headers())
            }

            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                println(response.body())
                if (response.body() == true) {
                    rentButton.isEnabled = false
                    rentButton.text = "Rented!"
                }
            }

        })
    }

    private fun getOrderService(): UserOrdersService {
        val client = Retrofit.Builder()
            .baseUrl(Constants.REST_API_SERVER_IP)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return client.create(UserOrdersService::class.java)
    }
}