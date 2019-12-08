package com.qverkk.touristrentafriend.ui.dashboard.ui.currentUser


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.qverkk.touristrentafriend.R
import com.qverkk.touristrentafriend.database.AppDatabase
import com.qverkk.touristrentafriend.ui.main.MainActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass.
 */
class CurrentUserFragment : Fragment() {

    private lateinit var currentUserViewModel: CurrentUserViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        currentUserViewModel =
            ViewModelProviders.of(this).get(CurrentUserViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_current_user, container, false)
        val fullNameTextView: TextView = root.findViewById(R.id.textView_current_user_full_name)
        val birthDateTextView: TextView = root.findViewById(R.id.textView_current_user_birth_date)
        val countryTextView: TextView = root.findViewById(R.id.textView_current_user_country)
        val cityTextView: TextView = root.findViewById(R.id.textView_current_user_city)
        val priceTextView: TextView = root.findViewById(R.id.textView_current_user_price)
        val descriptionTextView: TextView = root.findViewById(R.id.textView_current_user_description)

        initializeLogoutButton(root)

        initializeObservables(fullNameTextView, birthDateTextView, countryTextView, cityTextView, priceTextView, descriptionTextView)

        return root
    }

    private fun initializeObservables(
        fullNameTextView: TextView,
        birthDateTextView: TextView,
        countryTextView: TextView,
        cityTextView: TextView,
        priceTextView: TextView,
        descriptionTextView: TextView
    ) {
        currentUserViewModel.fullName.observe(this@CurrentUserFragment, Observer {
            fullNameTextView.text = it
        })

        currentUserViewModel.birthDate.observe(this@CurrentUserFragment, Observer {
            birthDateTextView.text = it
        })

        currentUserViewModel.country.observe(this@CurrentUserFragment, Observer {
            countryTextView.text = it
        })

        currentUserViewModel.city.observe(this@CurrentUserFragment, Observer {
            cityTextView.text = it
        })

        currentUserViewModel.price.observe(this@CurrentUserFragment, Observer {
            priceTextView.text = it.toPlainString()
        })

        currentUserViewModel.description.observe(this@CurrentUserFragment, Observer {
            descriptionTextView.text = it
        })
    }

    private fun initializeLogoutButton(root: View) {
        val buttonLogout: Button = root.findViewById(R.id.button_current_user_logout)

        buttonLogout.setOnClickListener {
            val database = AppDatabase.getDatabase(context!!)
            GlobalScope.launch {
                database.userDao().deleteAll()
                database.userInformationDao().deleteAll()

                activity?.runOnUiThread {
                    val intent = Intent(context, MainActivity::class.java)
                    startActivity(intent)
                    activity?.finish()
                }
            }
        }
    }
}
