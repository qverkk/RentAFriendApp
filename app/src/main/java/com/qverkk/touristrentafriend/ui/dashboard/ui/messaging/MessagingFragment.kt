package com.qverkk.touristrentafriend.ui.dashboard.ui.messaging


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.qverkk.touristrentafriend.R
import com.qverkk.touristrentafriend.data.UserOrder
import com.qverkk.touristrentafriend.helpers.RentUserHelper

/**
 * A simple [Fragment] subclass.
 */
class MessagingFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_messaging, container, false)
        val messagesRecycler: RecyclerView = root.findViewById(R.id.recycle_messaging_messages)

        val messages = mutableListOf<UserOrder>()
        val adapter = MessagesAdapter(messages, findNavController())

        RentUserHelper().getAllOrdersForUser(messages, adapter)

        messagesRecycler.adapter = adapter
        messagesRecycler.layoutManager = LinearLayoutManager(context)

        return root
    }


}
