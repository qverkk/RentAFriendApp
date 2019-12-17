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
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.messaging.FirebaseMessaging
import com.qverkk.touristrentafriend.R
import com.qverkk.touristrentafriend.data.MessageDTO
import com.qverkk.touristrentafriend.database.user.UserDb
import com.qverkk.touristrentafriend.helpers.UsersHelper
import com.qverkk.touristrentafriend.ui.login.helpers.UserAsyncTask
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.json.JSONObject
import java.io.File
import java.io.FileInputStream
import java.nio.file.Files
import java.nio.file.Paths


/**
 * A simple [Fragment] subclass.
 */
class DirectChatFragment : Fragment() {

    private val serverKey = "AAAA6_hDyBQ:APA91bEZ5SsRZ1ZwsP9K39A1pS2IiVmF2_Z3KcWhAT5Sd9bhefZM_1g1qZ7aBuA_CzdDyvrtAWdkdILHt3IUNGZEegHzh5jQlZWvv6wRoOKluGqB7o3_6YISG5737jnmjEUoZijpNrbr"

    private val messages = mutableListOf<UserMessage>()
    private val adapter = DirectChatAdapter(messages)

    private val onNotice = object : BroadcastReceiver() {
        override fun onReceive(contxt: Context?, intent: Intent) {
            messages.add(
                UserMessage(
                    intent.getStringExtra("from")!!,
                    intent.getStringExtra("body")!!
                )
            )
            adapter.notifyDataSetChanged()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_direct_chat, container, false)
        val messagesRecycler: RecyclerView = root.findViewById(R.id.recycle_direct_messages)
        val messageTextView: EditText = root.findViewById(R.id.text_direct_message_text)
        val sendButton: Button = root.findViewById(R.id.button_direct_send)
        val chatId = arguments!!["chatId"] as String

        FirebaseMessaging.getInstance().subscribeToTopic(chatId)

        val user = UserAsyncTask().execute().get()

        messagesRecycler.adapter = adapter
        messagesRecycler.layoutManager = LinearLayoutManager(context)

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            findNavController().navigate(R.id.action_directChatFragment_to_navigation_message)
//            FirebaseMessaging.getInstance().unsubscribeFromTopic(chatId)
        }
//2!sJDNDYdf08KevlRxiOWd4L#9GtJ9B1@LF^L2nqwSX$nf14By
        sendMessage(sendButton, chatId, user, messageTextView)

        val intentFilter = IntentFilter("com.google.firebase.MESSAGING_EVENT")
        LocalBroadcastManager.getInstance(requireContext()).registerReceiver(onNotice, intentFilter)

        return root
    }

    private fun sendMessage(
        sendButton: Button,
        chatId: String,
        user: UserDb,
        messageTextView: EditText
    ) {

        sendButton.setOnClickListener {
            UsersHelper().sendMessage(
                MessageDTO(user.firstName, user.lastName, chatId, user.userId.toString(), messageTextView.text.toString())
            )
//            val client = OkHttpClient()
//
//            val json = JSONObject()
//            json.put("to", "/topics/uniqueqverkk123")
//            val notificationObj = JSONObject()
//            notificationObj.put("title", "New message")
//            notificationObj.put(
//                "body",
//                "Mew message from ${user.firstName} ${user.lastName}"
//            )
//            json.put("notification", notificationObj)
//            val text = JSONObject()
//            text.put("fromUser", "${user.userId}")
//            text.put("textBody", "${messageTextView.text}")
//            json.put("data", text)
//            println(json.toString())
//
////            val result = JSONObject()
////            val message = JSONObject()
////            val data = JSONObject()
////            data.put("fromUser", user.userId.toString())
////            data.put("textBody", messageTextView.text.toString())
////
////            val messageResult = JSONObject()
////            messageResult.put("data", data)
////            messageResult.put("topic", "TEST")
////            message.put("message", messageResult)
//
//
//            val requestBody = RequestBody.create(
//                MediaType.parse("application/json; charset=utf-8"),
//                json.toString()
//            )
////            val request = Request.Builder()
////                .url("https://fcm.googleapis.com/v1/projects/tourist-rent-a-friend/messages:send?key=$serverKey")
////                .addHeader("content-type", "application/json; UTF-8")
////                .addHeader("authorization", "Bearer ${getAccessToken()}")
////                .post(requestBody)
////                .build()
//
//            val request = Request.Builder()
//                .url("https://fcm.googleapis.com/fcm/send")
//                .addHeader("content-type", "application/json; UTF-8")
//                .addHeader("authorization", "key=$serverKey")
//                .addHeader("accept", "application/json")
//                .post(requestBody)
//                .build()
//
//
//            GlobalScope.launch {
//                val execute = client.newCall(request).execute()
//                println(execute.networkResponse())
//                println(execute.body()?.string())
//                execute.close()
//            }
        }
    }
//
//    private val SCOPES = "https://www.googleapis.com/auth/firebase.messaging"
//
//    private fun getAccessToken(): String {
//        val googleCredential = GoogleCredentials
//            .fromStream(this.resources.openRawResource(R.raw.serviceacc))
//            .createScoped(listOf(SCOPES))
//        GlobalScope.launch {
//            googleCredential.refresh()
//        }
//        return googleCredential.accessToken.tokenValue
//    }
}
