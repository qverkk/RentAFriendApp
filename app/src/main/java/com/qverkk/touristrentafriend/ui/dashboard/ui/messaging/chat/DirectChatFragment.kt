package com.qverkk.touristrentafriend.ui.dashboard.ui.messaging.chat


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.qverkk.touristrentafriend.R

/**
 * A simple [Fragment] subclass.
 */
class DirectChatFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_direct_chat, container, false)
    }
}
