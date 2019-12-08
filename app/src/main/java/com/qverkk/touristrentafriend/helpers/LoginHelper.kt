package com.qverkk.touristrentafriend.helpers

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.qverkk.touristrentafriend.data.UserDetails
import com.qverkk.touristrentafriend.database.AppDatabase
import com.qverkk.touristrentafriend.services.UserService
import com.qverkk.touristrentafriend.ui.dashboard.BottomDashboardActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object LoginHelper {

    fun performLogin(activity: Activity?, context: Context?) {
        val database = AppDatabase.getDatabase(context!!)
        GlobalScope.launch {
            val user = database.userDao().getUser() ?: return@launch

            performLogin(
                activity,
                context,
                user.username,
                user.password,
                true
            )
        }
    }

    fun performLogin(
        activity: Activity?,
        context: Context?,
        username: String,
        password: String,
        userDatabase: Boolean = false
    ): Boolean {
        val client = Retrofit.Builder()
            .baseUrl("http://192.168.1.64:8080")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = client.create(UserService::class.java)
        val call = service.loginTest(
            username, password
        )

        call.enqueue(object : Callback<UserDetails> {
            override fun onFailure(call: Call<UserDetails>, t: Throwable) {

            }

            override fun onResponse(
                call: Call<UserDetails>,
                response: Response<UserDetails>
            ) {
                val user = response.body()
                if (user != null) {
                    val userDao = AppDatabase.getDatabase(context!!).userDao()

                    GlobalScope.launch {
                        if (!userDatabase) {
                            userDao.deleteAll()
                            userDao.insertUser(user.user.toUserDb())
                        }
                        activity?.runOnUiThread {
                            val intent = Intent(context, BottomDashboardActivity::class.java)
                            activity.startActivity(intent)
                        }
                    }
                }
            }
        })
        return false
    }
}