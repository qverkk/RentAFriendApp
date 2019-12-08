package com.qverkk.touristrentafriend.ui.login


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.qverkk.touristrentafriend.R
import com.qverkk.touristrentafriend.data.User
import com.qverkk.touristrentafriend.data.UserDetails
import com.qverkk.touristrentafriend.database.AppDatabase
import com.qverkk.touristrentafriend.services.UserService
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * A simple [Fragment] subclass.
 */
class LoginFragment : Fragment(), View.OnClickListener {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_login_login.setOnClickListener(this)
        btn_login_forgotpassword.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            btn_login_login.id -> {
//                val data = Data.Builder()
//                    .putString("username", editText_login_username.text.toString())
//                    .putString("password", editText_login_password.text.toString())
//                    .build()
//
//                val constraints = Constraints.Builder()
//                    .setRequiredNetworkType(NetworkType.CONNECTED)
//                    .build()
//
//                val worker = OneTimeWorkRequest.Builder(LoginWorker::class.java)
//                    .setInputData(data)
//                    .setConstraints(constraints)
//                    .build()
//
//                WorkManager.getInstance().enqueueUniqueWork("TEST", ExistingWorkPolicy.KEEP, worker)
                val client = Retrofit.Builder()
                    .baseUrl("http://192.168.1.64:8080")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

                val service = client.create(UserService::class.java)
                val call = service.loginTest(
                    editText_login_username.text.toString(),
                    editText_login_password.text.toString()
                )

                call.enqueue(object : Callback<UserDetails> {
                    override fun onFailure(call: Call<UserDetails>, t: Throwable) {

                    }

                    override fun onResponse(call: Call<UserDetails>, response: Response<UserDetails>) {
                        val user = response.body()
                        if (user != null) {

                            val userDao = AppDatabase.getDatabase(context!!).userDao()

                            GlobalScope.launch {
                                userDao.deleteAll()
                                println(user.user)
                                println(user.information)
                                userDao.insertUser(user.user.toUserDb())
                            }

                            println("Should login")
                        } else {
                            println("Shouldnt login")
                        }
                    }
                })
            }
            btn_login_forgotpassword.id -> {
                editText_login_username.error = "This user doesn't exist"
            }
        }
    }

}
