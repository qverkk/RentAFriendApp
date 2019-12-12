package com.qverkk.touristrentafriend.ui.dashboard.ui.home


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.qverkk.touristrentafriend.R
import com.qverkk.touristrentafriend.ui.dashboard.ui.currentUser.getBitmapFromBase64
import java.math.BigDecimal

/**
 * A simple [Fragment] subclass.
 */
class FullUserFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            findNavController().navigate(R.id.action_fullUserFragment_to_navigation_home)
        }

        val root = inflater.inflate(R.layout.fragment_full_user, container, false)
        val imageView: ImageView = root.findViewById(R.id.image_user_full_profile_picture)
        val fullName: TextView = root.findViewById(R.id.text_user_full_full_name)
        val country: TextView = root.findViewById(R.id.text_user_full_country)
        val city: TextView = root.findViewById(R.id.text_user_full_city)
        val age: TextView = root.findViewById(R.id.text_user_full_age)
        val price: TextView = root.findViewById(R.id.text_user_full_price)
        val description: TextView = root.findViewById(R.id.text_user_full_description)

        initializeComponents(imageView, fullName, country, city, age, price, description)

        return root
    }

    private fun initializeComponents(
        imageView: ImageView,
        fullName: TextView,
        country: TextView,
        city: TextView,
        age: TextView,
        price: TextView,
        description: TextView
    ) {
        imageView.setImageBitmap(getBitmapFromBase64(arguments!!["image"] as String))
        fullName.text = arguments!!["fullname"] as String
        country.text = arguments!!["country"] as String
        city.text = arguments!!["city"] as String
        age.text = (arguments!!["age"] as Int).toString()
        price.text = (arguments!!["price"] as BigDecimal).toPlainString()
        description.text = arguments!!["description"] as String
    }
}
