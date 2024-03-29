package com.qverkk.touristrentafriend.helpers

import androidx.recyclerview.widget.RecyclerView
import com.qverkk.touristrentafriend.data.Constants
import com.qverkk.touristrentafriend.data.MessageDTO
import com.qverkk.touristrentafriend.data.UserDetails
import com.qverkk.touristrentafriend.services.UserService
import com.qverkk.touristrentafriend.ui.dashboard.ui.home.UsersAdapter
import com.qverkk.touristrentafriend.ui.dashboard.ui.messaging.chat.DirectChatAdapter
import com.qverkk.touristrentafriend.ui.dashboard.ui.messaging.chat.UserMessage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class UsersHelper {

    fun sendMessage(
        messageDTO: MessageDTO
    ) {
        val client = Retrofit.Builder()
            .baseUrl(Constants.REST_API_SERVER_IP)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = client.create(UserService::class.java)
        val call = service.sendMessage(messageDTO)

        call.enqueue(object : Callback<Boolean> {
            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                println("Failure sending message")
            }

            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                println("Success sending message")
            }

        })
    }

    fun getAllMessages(
        chatId: String,
        adapter: DirectChatAdapter,
        list: MutableList<UserMessage>,
        messagesRecycler: RecyclerView
    ) {
        val client = Retrofit.Builder()
            .baseUrl(Constants.REST_API_SERVER_IP)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = client.create(UserService::class.java)
        val call = service.getAllMessages(chatId)

        call.enqueue(object : Callback<List<MessageDTO>> {
            override fun onFailure(call: Call<List<MessageDTO>>, t: Throwable) {
                println("Failure getting all messages")
            }

            override fun onResponse(
                call: Call<List<MessageDTO>>,
                response: Response<List<MessageDTO>>
            ) {
                response.body()?.forEach {
                    list.add(UserMessage(it.fromUser, it.textBody))
                }
                adapter.notifyDataSetChanged()
                messagesRecycler.scrollToPosition(list.size)
            }

        })
    }

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