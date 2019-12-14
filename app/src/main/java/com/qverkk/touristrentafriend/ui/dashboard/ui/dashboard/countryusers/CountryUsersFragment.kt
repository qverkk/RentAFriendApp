package com.qverkk.touristrentafriend.ui.dashboard.ui.dashboard.countryusers


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.addCallback
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.qverkk.touristrentafriend.R
import com.qverkk.touristrentafriend.data.UserDetails
import com.qverkk.touristrentafriend.helpers.UsersHelper
import com.qverkk.touristrentafriend.ui.dashboard.ui.home.HomeViewModel
import com.qverkk.touristrentafriend.ui.dashboard.ui.home.UsersAdapter

/**
 * A simple [Fragment] subclass.
 */
class CountryUsersFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val countryName = arguments!!["countryName"] as String
        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val textView: TextView = root.findViewById(R.id.text_home)
        val recycleView: RecyclerView = root.findViewById(R.id.recycle_users_list)

        val users = mutableListOf<UserDetails>()
        val adapter = UsersAdapter(users, findNavController(), this)
        UsersHelper().getAllUserInformationFrom(users, adapter, countryName)
        recycleView.adapter = adapter
        recycleView.layoutManager = LinearLayoutManager(context)

        homeViewModel.text.observe(this, Observer {
            textView.text = it
        })

        requireActivity().title = "Users from ${arguments!!["countryName"]}"

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            findNavController().navigate(R.id.action_countryUsersFragment_to_navigation_dashboard)
        }

        return root
    }


}
