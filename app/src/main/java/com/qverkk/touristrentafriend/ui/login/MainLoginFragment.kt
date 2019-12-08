package com.qverkk.touristrentafriend.ui.login


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.qverkk.touristrentafriend.R
import kotlinx.android.synthetic.main.fragment_main_login.*

/**
 * A simple [Fragment] subclass.
 */
class MainLoginFragment : Fragment(), View.OnClickListener {

    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        btn_mainlogin_login.setOnClickListener(this)
        btn_mainlogin_register.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            btn_mainlogin_login.id -> navController.navigate(R.id.action_mainLoginFragment_to_loginFragment)
            btn_mainlogin_register.id -> navController.navigate(R.id.action_mainLoginFragment_to_registerFragment)
        }
    }

}
