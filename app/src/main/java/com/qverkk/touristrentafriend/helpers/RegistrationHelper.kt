package com.qverkk.touristrentafriend.helpers

import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.navigation.NavController
import com.qverkk.touristrentafriend.R
import com.qverkk.touristrentafriend.data.Constants
import com.qverkk.touristrentafriend.data.User
import com.qverkk.touristrentafriend.data.UserDetails
import com.qverkk.touristrentafriend.data.UserInformation
import com.qverkk.touristrentafriend.database.AppDatabase
import com.qverkk.touristrentafriend.services.UserService
import com.qverkk.touristrentafriend.ui.login.getInformationFromErrorResponse
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.math.BigDecimal

class RegistrationHelper(
    private val context: Context,
    private val navController: NavController,
    private val activity: Activity?,
    private val firstName: String,
    private val lastName: String,
    private val birthDate: String,
    private val username: String,
    private val password: String,
    private val description: String,
    private val price: BigDecimal,
    private val country: String,
    private val city: String
) {

    fun register() {
        val client = Retrofit.Builder()
            .baseUrl(Constants.REST_API_SERVER_IP)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = client.create(UserService::class.java)
        val user = User(
            0,
            firstName,
            lastName,
            birthDate,
            username,
            password
        )

        val information = UserInformation(
            0,
            0,
            description,
            price,
            country,
            city
        )

        val details = UserDetails(
            user,
            information
        )
        performRegistration(service, details)
    }

    private fun performRegistration(
        service: UserService,
        details: UserDetails
    ) {
        val call = service.register(
            details
        )

        call.enqueue(object : Callback<UserDetails> {
            override fun onFailure(call: Call<UserDetails>, t: Throwable) {
                Toast.makeText(context, "Failed to communicate with server", Toast.LENGTH_LONG)
                    .show()
            }

            override fun onResponse(call: Call<UserDetails>, response: Response<UserDetails>) {
                val userResponse = response.body()
                if (userResponse == null) {
                    Toast.makeText(
                        context,
                        getInformationFromErrorResponse(response),
                        Toast.LENGTH_LONG
                    ).show()
                    return
                }

                val database = AppDatabase.getDatabase(context!!.applicationContext)
                Toast.makeText(context, "Adding stuff to local database", Toast.LENGTH_LONG).show()

                GlobalScope.launch {
                    database.userDao().deleteAll()
                    database.userInformationDao().deleteAll()

                    database.userDao().insertUser(userResponse.user.toUserDb())
                    database.userInformationDao()
                        .insert(userResponse.information.toInformationDB())

                    navController.navigate(R.id.action_registerFragment_to_mainLoginFragment)
                    activity?.runOnUiThread {
                        Toast.makeText(
                            context,
                            "Congratulations, you have signed in!",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        })
    }
}