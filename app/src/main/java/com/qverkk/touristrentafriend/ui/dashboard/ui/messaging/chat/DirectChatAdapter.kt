package com.qverkk.touristrentafriend.ui.dashboard.ui.messaging.chat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.qverkk.touristrentafriend.R


class DirectChatAdapter(val mOrders: List<UserMessage>) :
    RecyclerView.Adapter<DirectChatAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val messageText: TextView = itemView.findViewById(R.id.text_direct_item_message)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)

        val countryView = inflater.inflate(R.layout.item_direct_message, parent, false)

        val viewHolder = ViewHolder(countryView)
        return viewHolder

    }

    override fun getItemCount(): Int = mOrders.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val order = mOrders[position]

        holder.messageText.text = order.textBody
    }
}