package com.qverkk.touristrentafriend.ui.login.helpers

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import okhttp3.OkHttpClient
import okhttp3.Request

class LoginWorker(
    context: Context,
    workerParameters: WorkerParameters
) : Worker(context, workerParameters) {

    override fun doWork(): Result {
        return try {
//            val client = Retrofit.Builder()
//                .baseUrl("http://localhost:8080")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build()
//
//            val service = client.create(UserService::class.java)
//            val call = service.login(
//                inputData.getString("username")!!,
//                inputData.getString("password")!!
//            )
//
////            val request = call.execute()
////            if (request.isSuccessful) {
////                val user = request.body()
////                println(user)
////                return Result.success()
////            } else {
////                return Result.failure()
////            }
//            call.enqueue(object : Callback<User> {
//                override fun onFailure(call: Call<User>, t: Throwable) {
//                    println(call.request().url().toString())
//                    println(call.request().headers().get("username"))
//                    println(call.request().headers().get("password"))
//                }
//
//                override fun onResponse(call: Call<User>, response: Response<User>) {
//                    println(response.code())
//                }
//
//            })
            val client = OkHttpClient.Builder()
                .build()
            val request = Request.Builder()
                .url("http://192.168.1.64:8080/users/all")
                .get()
                .build()
            val response = client.newCall(request).execute()
            println(response.body()?.string())
            return Result.failure()
        } catch (e: Exception) {
            e.printStackTrace()
            return Result.failure()
        }
    }

}
