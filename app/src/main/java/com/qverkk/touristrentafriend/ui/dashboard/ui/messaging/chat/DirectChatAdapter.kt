package com.qverkk.touristrentafriend.ui.dashboard.ui.messaging.chat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.qverkk.touristrentafriend.R
import com.qverkk.touristrentafriend.ui.login.helpers.UserAsyncTask


class DirectChatAdapter(val mOrders: List<UserMessage>) :
    RecyclerView.Adapter<DirectChatAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val messageText: TextView = itemView.findViewById(R.id.text_chat_message)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)

        val countryView = inflater.inflate(R.layout.item_chat_message, parent, false)

        val viewHolder = ViewHolder(countryView)
        return viewHolder

    }

    override fun getItemCount(): Int = mOrders.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val order = mOrders[position]

        val user = UserAsyncTask().execute().get()
        holder.messageText.text = order.textBody
        if (user.userId == order.fromUser) {
            val layoutParams: RelativeLayout.LayoutParams = holder.messageText.layoutParams as RelativeLayout.LayoutParams
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE)
            holder.messageText.setBackgroundResource(R.drawable.rounded_corner_you)
        } else {
            holder.messageText.setBackgroundResource(R.drawable.rounded_corner_them)
        }
    }
}