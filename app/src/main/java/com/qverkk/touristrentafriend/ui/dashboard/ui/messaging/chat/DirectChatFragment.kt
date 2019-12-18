package com.qverkk.touristrentafriend.ui.dashboard.ui.messaging.chat


import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.messaging.FirebaseMessaging
import com.qverkk.touristrentafriend.R
import com.qverkk.touristrentafriend.data.MessageDTO
import com.qverkk.touristrentafriend.database.user.UserDb
import com.qverkk.touristrentafriend.helpers.UsersHelper
import com.qverkk.touristrentafriend.ui.login.helpers.UserAsyncTask


/**
 * A simple [Fragment] subclass.
 */
class DirectChatFragment : Fragment() {
    private val messages = mutableListOf<UserMessage>()
    private val adapter = DirectChatAdapter(messages)
    private lateinit var messagesRecycler: RecyclerView

    private val onNotice = object : BroadcastReceiver() {
        override fun onReceive(contxt: Context?, intent: Intent) {
            val fromUser = intent.getStringExtra("from")
            val bodyText = intent.getStringExtra("body")
            if (fromUser != null && bodyText != null) {
                messages.add(
                    UserMessage(
                        fromUser.toInt(),
                        bodyText
                    )
                )
                adapter.notifyDataSetChanged()
                messagesRecycler.scrollToPosition(messages.size)
            } else {
                Toast.makeText(context, "No user or body defined", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_direct_chat, container, false)
        messagesRecycler = root.findViewById(R.id.recycle_direct_messages)
        val messageTextView: EditText = root.findViewById(R.id.text_direct_message_text)
        val sendButton: Button = root.findViewById(R.id.button_direct_send)
        val chatId = arguments!!["chatId"] as String
        val sendingToUser = arguments!!["toUser"] as Int

        FirebaseMessaging.getInstance().subscribeToTopic(chatId)

        val user = UserAsyncTask().execute().get()

        messagesRecycler.adapter = adapter
        messagesRecycler.layoutManager = LinearLayoutManager(context)

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            findNavController().navigate(R.id.action_directChatFragment_to_navigation_message)
        }

        UsersHelper().getAllMessages(chatId, adapter, messages, messagesRecycler)
        sendMessage(sendButton, chatId, user, messageTextView, sendingToUser)

        val intentFilter = IntentFilter("com.google.firebase.MESSAGING_EVENT")
        LocalBroadcastManager.getInstance(requireContext()).registerReceiver(onNotice, intentFilter)

        return root
    }

    private fun sendMessage(
        sendButton: Button,
        chatId: String,
        user: UserDb,
        messageTextView: EditText,
        toUser: Int
    ) {
        sendButton.setOnClickListener {
            UsersHelper().sendMessage(
                MessageDTO(
                    0,
                    user.firstName,
                    user.lastName,
                    chatId,
                    user.userId,
                    toUser,
                    messageTextView.text.toString()
                )
            )
            messageTextView.text.clear()
        }
    }
}
