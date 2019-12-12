package com.qverkk.touristrentafriend.ui.login


import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.qverkk.touristrentafriend.R
import com.qverkk.touristrentafriend.helpers.LoginHelper
import kotlinx.android.synthetic.main.fragment_main_login.*

/**
 * A simple [Fragment] subclass.
 */
class MainLoginFragment : Fragment(), View.OnClickListener {

    private lateinit var navController: NavController
    private val INTERNET_PERMISSION = 10003
    private var savedView: View? = null

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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (canLogin()) {
            LoginHelper.performLogin(activity, context)
        }
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            btn_mainlogin_login.id -> {
                savedView = v
                if (canLogin()) {
                    navController.navigate(R.id.action_mainLoginFragment_to_loginFragment)
                }
            }
            btn_mainlogin_register.id -> {
                savedView = v
                if (canLogin()) {
                    navController.navigate(R.id.action_mainLoginFragment_to_registerFragment)
                }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            INTERNET_PERMISSION -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    if (savedView != null) {
                        onClick(savedView)
                    } else {
                        LoginHelper.performLogin(activity, context)
                    }
                }
            }
        }
    }

    private fun canLogin(): Boolean {
        if (ContextCompat.checkSelfPermission(
                context!!,
                Manifest.permission.INTERNET
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(arrayOf(Manifest.permission.INTERNET), INTERNET_PERMISSION)
            return false
        }
        return true
    }
}
