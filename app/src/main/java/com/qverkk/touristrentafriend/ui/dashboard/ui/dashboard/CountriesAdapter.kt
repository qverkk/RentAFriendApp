package com.qverkk.touristrentafriend.ui.dashboard.ui.dashboard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.qverkk.touristrentafriend.R
import java.util.*


class CountriesAdapter(val mCountries: List<String>, private val navController: NavController) :
    RecyclerView.Adapter<CountriesAdapter.ViewHolder>() {

    class ViewHolder(itemView: View, val navController: NavController) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {

        val countryImage: TextView = itemView.findViewById(R.id.text_country_country_image)
        val countryText: TextView = itemView.findViewById(R.id.text_country_country_name)

        override fun onClick(p0: View?) {
            println(itemView)
        }

        fun changeImage(image: String) {
            val locale = getLocaleForCountryName(image)
            if (locale == null) {
                countryImage.visibility = View.INVISIBLE
                return
            }
            countryImage.text = getCountryFlag(locale)
        }

        fun changeCountryName(countryName: String) {
            countryText.text = countryName
        }

        private fun getCountryFlag(locale: Locale): String {
            val flagOffset = 0x1F1E6
            val asciiOffset = 0x41

            val country = locale.country

            val firstChar: Int =
                Character.codePointAt(country, 0) - asciiOffset + flagOffset
            val secondChar: Int =
                Character.codePointAt(country, 1) - asciiOffset + flagOffset

            return (String(Character.toChars(firstChar))
                    + String(Character.toChars(secondChar)))
        }

        private fun getLocaleForCountryName(countryName: String): Locale? {
            return Locale.getAvailableLocales()
                .toList()
                .stream()
                .filter { it.displayCountry == countryName }
                .findFirst()
                .orElse(null)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)

        val countryView = inflater.inflate(R.layout.item_country, parent, false)

        val viewHolder = ViewHolder(countryView, navController)
        return viewHolder

    }

    override fun getItemCount(): Int = mCountries.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val country = mCountries[position]

        holder.changeCountryName(country)
        holder.changeImage(country)
    }
}