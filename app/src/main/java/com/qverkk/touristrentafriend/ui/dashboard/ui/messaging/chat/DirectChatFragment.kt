package com.qverkk.touristrentafriend.ui.dashboard.ui.messaging.chat


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.qverkk.touristrentafriend.R
import com.qverkk.touristrentafriend.data.UserOrder
import com.qverkk.touristrentafriend.ui.dashboard.ui.messaging.MessagesAdapter

/**
 * A simple [Fragment] subclass.
 */
class DirectChatFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_direct_chat, container, false)
        val messagesRecycler: RecyclerView = root.findViewById(R.id.recycle_direct_messages)


        val messages = mutableListOf<UserOrder>()
        val adapter = MessagesAdapter(messages, findNavController())


        messagesRecycler.adapter = adapter
        messagesRecycler.layoutManager = LinearLayoutManager(context)

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            findNavController().navigate(R.id.action_directChatFragment_to_navigation_message)
        }

        return root
    }
}
