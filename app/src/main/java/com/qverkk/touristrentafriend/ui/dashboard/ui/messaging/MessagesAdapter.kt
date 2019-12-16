package com.qverkk.touristrentafriend.ui.dashboard.ui.messaging

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.qverkk.touristrentafriend.R
import com.qverkk.touristrentafriend.data.UserOrder
import com.qverkk.touristrentafriend.ui.login.helpers.UserAsyncTask


class MessagesAdapter(val mOrders: List<UserOrder>, private val navController: NavController) :
    RecyclerView.Adapter<MessagesAdapter.ViewHolder>() {

    class ViewHolder(itemView: View, val navController: NavController) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {

        val chatNameText: TextView = itemView.findViewById(R.id.text_item_message_name)
        var chatId = ""
        var chatWith = ""

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            navController.navigate(
                R.id.action_navigation_message_to_directChatFragment,
                bundleOf(
                    "chatId" to chatId,
                    "chatWith" to chatWith
                )
            )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)

        val countryView = inflater.inflate(R.layout.item_message, parent, false)

        val viewHolder = ViewHolder(countryView, navController)
        return viewHolder

    }

    override fun getItemCount(): Int = mOrders.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val order = mOrders[position]

        val user = UserAsyncTask().execute().get()
        holder.chatNameText.text = order.chatName
        holder.chatId = order.orderId
        holder.chatWith = if (user.userId == order.userRentingId) {
            order.userRentedName
        } else {
            order.userRentingName
        }
    }
}