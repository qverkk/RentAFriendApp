package com.qverkk.touristrentafriend.ui.dashboard.ui.messaging.chat

import android.content.Intent
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageSent(p0: String) {
        println(p0)
    }

    override fun onMessageReceived(p0: RemoteMessage) {
        println("From: ${p0.data}")
        broadcastIntent(p0.data)
    }

    override fun onDeletedMessages() {

    }

    private fun broadcastIntent(data: MutableMap<String, String>) {
        val intent = Intent()
        intent.action = "com.google.firebase.MESSAGING_EVENT"
        intent.putExtra("from", data["fromUser"])
        intent.putExtra("body", data["textBody"])
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
    }

    override fun onNewToken(p0: String) {
        println(p0)
    }
}
