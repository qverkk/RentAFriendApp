package com.qverkk.touristrentafriend.ui.login


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.qverkk.touristrentafriend.R
import com.qverkk.touristrentafriend.helpers.LoginHelper
import kotlinx.android.synthetic.main.fragment_login.*

/**
 * A simple [Fragment] subclass.
 */
class LoginFragment : Fragment(), View.OnClickListener {

    private lateinit var navigation: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navigation = Navigation.findNavController(view)
        btn_login_login.setOnClickListener(this)
        btn_login_forgotpassword.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            btn_login_login.id -> {
                LoginHelper.performLogin(
                    activity,
                    context,
                    editText_login_username.text.toString(),
                    editText_login_password.text.toString()
                )
//                val client = Retrofit.Builder()
//                    .baseUrl("http://192.168.1.64:8080")
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .build()
//
//                val service = client.create(UserService::class.java)
//                val call = service.loginTest(
//                    editText_login_username.text.toString(),
//                    editText_login_password.text.toString()
//                )
//
//                call.enqueue(object : Callback<UserDetails> {
//                    override fun onFailure(call: Call<UserDetails>, t: Throwable) {
//
//                    }
//
//                    override fun onResponse(
//                        call: Call<UserDetails>,
//                        response: Response<UserDetails>
//                    ) {
//                        val user = response.body()
//                        if (user != null) {
//
//                            val userDao = AppDatabase.getDatabase(context!!).userDao()
//
//                            GlobalScope.launch {
//                                userDao.deleteAll()
//                                userDao.insertUser(user.user.toUserDb())
//                                activity?.runOnUiThread {
//                                    val intent = Intent(context, BottomDashboardActivity::class.java)
//                                    startActivity(intent)
//                                }
//                            }
//
//                            println("Should login")
//                        } else {
//                            println("Shouldnt login")
//                        }
//                    }
//                })
            }
            btn_login_forgotpassword.id -> {
                editText_login_username.error = "This user doesn't exist"
            }
        }
    }

}
