package com.qverkk.touristrentafriend.ui.dashboard.ui.home

import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.qverkk.touristrentafriend.R
import com.qverkk.touristrentafriend.data.UserDetails
import com.qverkk.touristrentafriend.helpers.PicturesHelper
import java.util.*

class UsersAdapter(
    val mContacts: List<UserDetails>,
    private val navController: NavController,
    private val comingFrom: Any
) :
    RecyclerView.Adapter<UsersAdapter.ViewHolder>() {


    class ViewHolder(itemView: View, val navController: NavController, val comingFrom: Any) :
        RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        var user: UserDetails? = null
        var base64Image: String? = null
        val userPictureImageView: ImageView =
            itemView.findViewById(R.id.image_user_profile_picture)
        val userFullNameTextView: TextView = itemView.findViewById(R.id.text_user_full_name)
        val userCountryTextView: TextView = itemView.findViewById(R.id.text_user_country)
        val userCityTextView: TextView = itemView.findViewById(R.id.text_user_city)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(view: View?) {
            println("Clicked on $user")
            navController.navigate(
                if (comingFrom is HomeFragment)
                    R.id.action_navigation_home_to_fullUserFragment
                else
                    R.id.action_countryUsersFragment_to_fullUserFragment,
                generateUserBundle()
            )
        }

        private fun generateUserBundle(): Bundle? {
            return bundleOf(
                "image" to base64Image,
                "fullname" to "${user?.user?.firstName} ${user?.user?.lastName}",
                "country" to user?.information?.country,
                "city" to user?.information?.cityName,
                "age" to getAge(),
                "price" to user?.information?.price,
                "description" to user?.information?.description,
                "comingFrom" to comingFrom.javaClass.simpleName,
                "rentedUserId" to user?.user?.userId
            )
        }

        private fun getAge(): Int {
            val cal = Calendar.getInstance()
            val sdf = SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH)
            cal.time = sdf.parse(user?.user?.birthDate)
            return Calendar.getInstance().get(Calendar.YEAR) - cal.get(Calendar.YEAR)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)

        val userView = inflater.inflate(R.layout.item_user, parent, false)

        val viewHolder = ViewHolder(userView, navController, comingFrom)
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
        holder.user = details
        PicturesHelper().getUserProfilePicture(user, null, holder)
    }
}