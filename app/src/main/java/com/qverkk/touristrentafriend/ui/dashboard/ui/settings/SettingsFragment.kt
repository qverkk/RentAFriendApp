package com.qverkk.touristrentafriend.ui.dashboard.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.ToggleButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.qverkk.touristrentafriend.R

class SettingsFragment : Fragment() {

    private lateinit var notificationsViewModel: SettingsViewModel


    private val PREF_NAME = "theme"
    private var PRIVATE_MODE = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val pref = requireActivity().getSharedPreferences(PREF_NAME, PRIVATE_MODE)

        notificationsViewModel =
            ViewModelProviders.of(this).get(SettingsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_settings, container, false)
        val textView: TextView = root.findViewById(R.id.text_settings)
        notificationsViewModel.text.observe(this, Observer {
            textView.text = it
        })

        val toggleButton: Button = root.findViewById(R.id.button_settings_night_mode)

        toggleButton.text = if (pref.getBoolean(PREF_NAME, true)) {
            "Yes"
        } else {
            "No"
        }

        toggleButton.setOnClickListener {
            toggleButton.text = if (toggleButton.text == "No") {
                "Yes"
            } else {
                "No"
            }

            val editor = pref.edit()
            editor.putBoolean(PREF_NAME, toggleButton.text == "Yes")
            editor.apply()
            requireActivity().setTheme(
                if (pref.getBoolean(PREF_NAME, true)) {
                    R.style.AppThemeDark
                } else R.style.AppThemeLight
            )
            requireActivity().recreate()
        }

        return root
    }
}