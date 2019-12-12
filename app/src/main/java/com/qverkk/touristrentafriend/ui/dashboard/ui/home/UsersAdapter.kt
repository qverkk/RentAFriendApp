package com.qverkk.touristrentafriend.ui.dashboard.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.qverkk.touristrentafriend.R
import com.qverkk.touristrentafriend.data.UserDetails
import com.qverkk.touristrentafriend.helpers.PicturesHelper

class UsersAdapter(val mContacts: List<UserDetails>) :
    RecyclerView.Adapter<UsersAdapter.ViewHolder>() {


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        val userPictureImageView: ImageView =
            itemView.findViewById(R.id.image_user_profile_picture)
        val userFullNameTextView: TextView = itemView.findViewById(R.id.text_user_full_name)
        val userCountryTextView: TextView = itemView.findViewById(R.id.text_user_country)
        val userCityTextView: TextView = itemView.findViewById(R.id.text_user_city)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(view: View?) {

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)

        val userView = inflater.inflate(R.layout.item_user, parent, false)

        val viewHolder = ViewHolder(userView)
        return viewHolder
    }

    override fun getItemCount(): Int {
        return mContacts.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val details = mContacts[position]
        val user = details.user
        val information = details.information

        holder.userFullNameTextView.text = "${user.firstName} ${user.lastName}"
        holder.userCityTextView.text = information.cityName
        holder.userCountryTextView.text = information.country
        PicturesHelper().getUserProfilePicture(user, null, holder)
    }
}